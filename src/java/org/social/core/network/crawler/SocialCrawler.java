package org.social.core.network.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.ClassificationConst;
import org.social.core.constants.NetworkConst;
import org.social.core.exceptions.ItemNotFoundException;
import org.social.core.util.UtilDateTime;
import org.social.core.util.UtilProperties;
import org.social.core.util.UtilValidate;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

public abstract class SocialCrawler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String baseUrl;
    private final BaseCrawler crawler;

    private String endpoint;
    private Document document = null;

    private boolean anyMoreNewMessages;

    private String ratingClassName;
    private String messageDateClassName;
    private String userNameLinkClassName;
    private String reviewCommentCssClassName;
    private String reviewDataCssClassName;
    private String userDataCssClassName;
    private String reviewContainerCssClassName;

    protected String selectedPaginationCssClassName;
    protected String paginationControlsCssClassName;

    public SocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;

        this.crawler = crawler;

        loadHtmlIdentifiersFromProperty();
    }

    private void loadHtmlIdentifiersFromProperty() {
        String crawlerProperty = getPropertyFileName();

        ratingClassName = UtilProperties.getPropertyValue(crawlerProperty, "ratingClassName");
        messageDateClassName = UtilProperties.getPropertyValue(crawlerProperty, "messageDateClassName");
        userNameLinkClassName = UtilProperties.getPropertyValue(crawlerProperty, "userNameLinkClassName");
        reviewCommentCssClassName = UtilProperties.getPropertyValue(crawlerProperty, "reviewCommentCssClassName");
        reviewDataCssClassName = UtilProperties.getPropertyValue(crawlerProperty, "reviewDataCssClassName");
        userDataCssClassName = UtilProperties.getPropertyValue(crawlerProperty, "userDataCssClassName");
        selectedPaginationCssClassName = UtilProperties.getPropertyValue(crawlerProperty,
                "selectedPaginationCssClassName");
        paginationControlsCssClassName = UtilProperties.getPropertyValue(crawlerProperty,
                "paginationControlsCssClassName");
        reviewContainerCssClassName = UtilProperties.getPropertyValue(crawlerProperty, "reviewContainerCssClassName");
    }

    public List<Message> crawl(Customer customer) throws ItemNotFoundException {
        List<Message> extractedMessagesFilterByDate = new ArrayList<Message>();
        anyMoreNewMessages = true;

        while (anyMoreNewMessages && endpoint != null && !endpoint.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Next request found: " + endpoint);
            }

            try {
                document = getDocument();
            } catch (IOException e) {
                logger.error("", e);
                break;
            }

            Element body = document.body();
            Elements reviewContainer = getReviewDataContainer(body);

            if (logger.isDebugEnabled()) {
                logger.debug("Extracting information from request: " + endpoint);
            }

            List<Message> extractedMessages = extractReviewDataFromHtml(reviewContainer, document.head(), customer);

            extractedMessages = filterMessageByDate(extractedMessages, customer.getLastNetworkAccess());

            extractedMessagesFilterByDate.addAll(extractedMessages);

            endpoint = getNextPageFromPagination(body);
        }

        return extractedMessagesFilterByDate;
    }

    private List<Message> filterMessageByDate(List<Message> extractedMessages, Date customerLastNetworkAccess) {
        Iterator<Message> messageIterator = extractedMessages.iterator();
        if (logger.isDebugEnabled()) {
            logger.debug("Check if there are messages which are older than the last customer network acces date.");
        }
        while (messageIterator.hasNext()) {
            Message message = messageIterator.next();
            Date networkTs = message.getNetworkMessageDate();

            if (UtilDateTime.isMessageDateBeforeLastNetworkAccess(networkTs, customerLastNetworkAccess)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Found message which is already stored. Remove it from current batch: "
                            + message.toString());
                }

                anyMoreNewMessages = false;
                messageIterator.remove();
            }
        }
        messageIterator = null;

        return extractedMessages;
    }

    public Document getDocument() throws IOException {
        return crawler.crwal(baseUrl + endpoint);
    }

    public Elements getReviewDataContainer(Element body) throws ItemNotFoundException {
        Elements reviewData = selectFromElement(body, reviewContainerCssClassName);
        return reviewData;
    }

    public Element getPaginationCurrentSelectedData(Element body) throws ItemNotFoundException {
        Elements paginationElements = selectFromElement(body, paginationControlsCssClassName);
        Element pagination = paginationElements.first();
        Elements currentSelectedPaginationElements = selectFromElement(pagination, selectedPaginationCssClassName);
        return currentSelectedPaginationElements.first();
    }

    public abstract String getNextPageFromPagination(Element currentPaginationElement) throws ItemNotFoundException;

    public List<Message> extractReviewDataFromHtml(Elements reviewContainer, Element headerElements, Customer customer)
            throws ItemNotFoundException {
        List<Message> resultList = new ArrayList<Message>();

        Elements pageUserData = getUserData(reviewContainer);
        Elements pageReviewData = getReviewData(reviewContainer);

        for (int i = 0; i < pageReviewData.size(); i++) {
            Message returnMessage = new Message();

            returnMessage.setNetwork(getNetworkName());

            Element reviewData = pageReviewData.get(i);

            Element userData = pageUserData.get(i);
            Elements selectedUserDate = selectFromElement(userData, userNameLinkClassName);
            Element userInfo = selectedUserDate.first();
            String networkUser = getUserNameFromUserInfo(userInfo);
            returnMessage.setNetworkUserName(networkUser);

            String networkUserId = getUserIdFromUserInfo(userInfo);
            returnMessage.setNetworkUserId(networkUserId);

            returnMessage.setCustomer(customer);

            String message = getReviewTextFromComment(reviewData);
            returnMessage.setMessage(message);

            String language = getLanguageFromHeadMetaData(headerElements);
            returnMessage.setLanguage(language);

            // TODO String geoLocation;

            String networkMessageDate = getNetworkMessageDate(reviewData);
            returnMessage.setNetworkMessageDate(UtilDateTime.toTimestamp(networkMessageDate));

            returnMessage.setMessageReceivedDate(UtilDateTime.nowTimestamp());

            String platformUserRating = getNetworkUserRating(reviewData);
            returnMessage.setNetworkUserRating(platformUserRating);

            returnMessage.setReliability(ClassificationConst.Reliability.RELIABLE);

            resultList.add(returnMessage);
        }
        return resultList;
    }

    protected abstract NetworkConst getNetworkName();

    private String getNetworkUserRating(Element reviewData) throws ItemNotFoundException {
        String userRating = "n/a";

        if (UtilValidate.isNotEmpty(ratingClassName)) {
            Elements selectedElements = selectFromElement(reviewData, ratingClassName);
            Element ratingElement = selectedElements.first();
            userRating = extractNetworkUserRatingData(ratingElement);
        }

        return userRating;
    }

    protected abstract String extractNetworkUserRatingData(Element ratingElement);

    private String getNetworkMessageDate(Element reviewData) throws ItemNotFoundException {
        Elements elements = selectFromElement(reviewData, messageDateClassName);
        String networkMessageDate = elements.first().text();
        return networkMessageDate;
    }

    abstract protected String getUserNameFromUserInfo(Element userInfo);

    protected abstract String getLanguageFromHeadMetaData(Element headerElements);

    abstract protected String getUserIdFromUserInfo(Element userInfo);

    private String getReviewTextFromComment(Element reviewData) throws ItemNotFoundException {
        Elements reviewComments = selectFromElement(reviewData, reviewCommentCssClassName);
        return reviewComments.first().text();
    }

    private Elements getReviewData(Elements reviewContainer) throws ItemNotFoundException {
        return selectFromElement(reviewContainer, reviewDataCssClassName);
    }

    private Elements getUserData(Elements reviewContainer) throws ItemNotFoundException {
        return selectFromElement(reviewContainer, userDataCssClassName);
    }

    protected Elements selectFromElement(Element toSelectFrom, String htmlIdentifierToSelect)
            throws ItemNotFoundException {
        Elements selectedElements = toSelectFrom.select(htmlIdentifierToSelect);

        checkIfAnElementWasFound(htmlIdentifierToSelect, selectedElements);

        return selectedElements;
    }

    private Elements selectFromElement(Elements toSelectFrom, String htmlIdentifierToSelect)
            throws ItemNotFoundException {
        Elements selectedElements = toSelectFrom.select(htmlIdentifierToSelect);

        checkIfAnElementWasFound(htmlIdentifierToSelect, selectedElements);

        return selectedElements;
    }

    private void checkIfAnElementWasFound(String htmlIdentifierToSelect, Elements selectedElements)
            throws ItemNotFoundException {

        if (selectedElements.isEmpty()) {
            throw new ItemNotFoundException("The Element: " + htmlIdentifierToSelect
                    + " could not be found. The Element ID is maybe out of date, check the HTML source from: "
                    + baseUrl);
        }
    }

    protected abstract String getPropertyFileName();
}
