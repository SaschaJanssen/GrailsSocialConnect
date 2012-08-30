package org.social.core.networks;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.YelpKraken
import org.social.core.network.crawler.BaseCrawler
import org.social.grails.network.Message
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

@TestFor(YelpKraken)
@Mock([Message, Keyword, Customer])
public class YelpKrakenTest {

    private Customer customer;
    private BaseCrawler baseCrawler;

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()
        
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName -> 
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = NetworkConst.YELP
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
        baseCrawler = new MockYelpBaseCrawler();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new YelpKraken(customer, baseCrawler);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(40, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new YelpKraken(customer, baseCrawler);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
