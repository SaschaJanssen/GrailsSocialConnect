package org.social.core.networks;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.OpenTableKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.crawler.BaseCrawler
import org.social.grails.network.Message
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

@TestFor(OpenTableKraken)
@Mock([Message, Keyword, Customer])
public class OpenTableKrakenTest {

    def Customer customer;
    def BaseCrawler baseCrawler;

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()
        
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, network -> 
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = network
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
        baseCrawler = new MockOpenTableBaseCrawler();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new OpenTableKraken(customer, baseCrawler);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(50, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new OpenTableKraken(customer, baseCrawler);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
