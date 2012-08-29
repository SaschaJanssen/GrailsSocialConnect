package org.social.core.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.NetworkConst;
import org.social.core.data.FilteredMessageList;
import org.social.core.exceptions.ItemNotFoundException;
import org.social.core.network.crawler.BaseCrawler;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.network.crawler.TripAdvisorSocialCrawler;
import org.social.core.query.Query;
import org.social.core.query.TripAdvisorQuery;
import org.social.grails.network.Message;
import org.social.grails.user.Customer;

public class TripAdvisorKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BaseCrawler crawler;

    public TripAdvisorKraken(Customer customer, BaseCrawler crawler) {
        super(customer);
        this.customer = customer;
        this.crawler = crawler;

        getCustomersKeywords(NetworkConst.TRIPADVISOR);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch information from OpenTable for customer: " + customer.getCustomerId());
        }

        Query query = buildQueryFromKeywords();

        SocialCrawler otCrawler = new TripAdvisorSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery());

        List<Message> resultMessages = new ArrayList<Message>();
        try {
            resultMessages = otCrawler.crawl(customer);
        } catch (ItemNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return sentimentMessages(resultMessages);
    }

    private Query buildQueryFromKeywords() {
        Query query = new TripAdvisorQuery(super.customerNetworkKeywords);

        Date sinceTs = customer.getLastNetworkAccess();
        if (sinceTs != null) {
            String since = sinceTs.toString();
            query.setSince(since);
        }

        return query;
    }
}
