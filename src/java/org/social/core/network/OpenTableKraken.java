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
import org.social.core.network.crawler.OpenTableSocialCrawler;
import org.social.core.network.crawler.SocialCrawler;
import org.social.core.query.OpenTableQuery;
import org.social.core.query.Query;
import org.social.grails.network.Message;
import org.social.grails.user.Customer;

public class OpenTableKraken extends SocialNetworkKraken {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final BaseCrawler crawler;

    public OpenTableKraken(Customer customer, BaseCrawler crawler) {
        super(customer);
        this.customer = customer;
        this.crawler = crawler;

        getCustomersKeywords(NetworkConst.OPENTABLE);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (log.isDebugEnabled()) {
            log.debug("Fetch information from OpenTable for customer: " + customer.getCustomerId());
        }

        Query query = buildQueryFromKeywords();

        SocialCrawler otCrawler = new OpenTableSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery());

        List<Message> resultMessages = new ArrayList<Message>();
        try {
            resultMessages = otCrawler.crawl(customer);
        } catch (ItemNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return sentimentMessages(resultMessages);
    }

    private Query buildQueryFromKeywords() {
        Query openTableQuery = new OpenTableQuery(super.customerNetworkKeywords);

        Date sinceTs = customer.getLastNetworkAccess();
        if (sinceTs != null) {
            String since = sinceTs.toString();
            openTableQuery.setSince(since);
        }

        return openTableQuery;
    }
}
