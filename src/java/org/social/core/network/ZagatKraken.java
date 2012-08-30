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
import org.social.core.network.crawler.ZagatSocialCrawler;
import org.social.core.query.Query;
import org.social.core.query.ZagatQuery;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

public class ZagatKraken extends SocialNetworkKraken {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final BaseCrawler crawler;

    public ZagatKraken(Customer customer, BaseCrawler crawler) {
        super(customer);
        this.customer = customer;
        this.crawler = crawler;

        getCustomersKeywords(NetworkConst.ZAGAT);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (log.isDebugEnabled()) {
            log.debug("Fetch information from Zagat for customer: " + customer.getCustomerId());
        }

        Query query = buildQueryFromKeywords();

        SocialCrawler otCrawler = new ZagatSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery());

        List<Message> resultMessages = new ArrayList<Message>();
        try {
            resultMessages = otCrawler.crawl(customer);
        } catch (ItemNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return sentimentMessages(resultMessages);
    }

    private Query buildQueryFromKeywords() {
        Query query = new ZagatQuery(super.customerNetworkKeywords);

        Date sinceTs = customer.getLastNetworkAccess();
        if (sinceTs != null) {
            String since = sinceTs.toString();
            query.setSince(since);
        }

        return query;
    }
}
