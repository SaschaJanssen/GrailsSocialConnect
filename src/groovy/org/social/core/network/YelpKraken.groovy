package org.social.core.network

import java.sql.Timestamp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.exceptions.ItemNotFoundException
import org.social.core.network.crawler.BaseCrawler
import org.social.core.network.crawler.YelpSocialCrawler
import org.social.core.query.Query
import org.social.core.query.YelpQuery
import org.social.core.user.Customer

public class YelpKraken extends SocialNetworkKraken {

    private Logger log = LoggerFactory.getLogger(this.getClass())

    private final BaseCrawler crawler

    public YelpKraken(Customer customer, BaseCrawler crawler) {
        super(customer)
        this.customer = customer
        this.crawler = crawler

        getCustomersKeywords(NetworkConst.YELP.getName())
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (log.isDebugEnabled()) {
            log.debug("Fetch information from YELP for customer: " + customer.id)
        }

        Query query = buildQueryFromKeywords()

        YelpSocialCrawler yelpCrawler = new YelpSocialCrawler(crawler, query.getSearchUrl(), query.constructQuery())

        List<Message> resultMessages = new ArrayList<Message>()
        try {
            resultMessages = yelpCrawler.crawl(customer)
        } catch (ItemNotFoundException e) {
            log.error(e.getMessage(), e)
        }

        return sentimentMessages(resultMessages)
    }

    private Query buildQueryFromKeywords() {
        Query yelpQuery = new YelpQuery(super.customerNetworkKeywords)

        Timestamp sinceTs = customer.lastNetworkAccess
        if (sinceTs != null) {
            String since = sinceTs.toString()
            yelpQuery.setSince(since)
        }

        return yelpQuery
    }
}
