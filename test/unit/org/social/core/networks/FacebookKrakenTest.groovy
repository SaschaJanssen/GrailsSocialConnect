package org.social.core.networks

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.FacebookKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.connection.SocialNetworkConnection
import org.social.grails.Keyword
import org.social.grails.Message
import org.social.grails.customer.Customer

@TestFor(FacebookKraken)
@Mock([Message, Keyword, Customer])
public class FacebookKrakenTest {

    def Customer customer
    def SocialNetworkConnection fbConnectionMock

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()

        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName -> 
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = NetworkConst.FACEBOOK
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
        fbConnectionMock = new FacebookConnectionMock()
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new FacebookKraken(customer, fbConnectionMock)
        FilteredMessageList results = kraken.fetchAndCraftMessages()

        assertNotNull(results)
        assertEquals(1, results.countPositivMessages())
        assertEquals(0, results.countNegativeMessages())
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new FacebookKraken(customer, fbConnectionMock)
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork())
    }
}
