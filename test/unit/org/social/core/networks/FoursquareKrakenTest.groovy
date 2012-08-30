package org.social.core.networks;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.FoursquareKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.connection.SocialNetworkConnection
import org.social.grails.Keyword
import org.social.grails.Message
import org.social.grails.customer.Customer

@TestFor(FoursquareKraken)
@Mock([Message, Keyword, Customer])
public class FoursquareKrakenTest {

    def Customer customer;
    def SocialNetworkConnection connectionMock;

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()
        
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName -> 
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = NetworkConst.FOURSQUARE
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
        connectionMock = new FoursquareConnectionMock();
    }

    @Test
    public void testFetchResults() {
        SocialNetworkKraken kraken = new FoursquareKraken(customer, connectionMock);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(1, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());

        Message msg = results.getPositiveList().get(0);

        assertEquals("Mikey", msg.getNetworkUserName());
        assertEquals("33226339", msg.getNetworkUserId());
        assertEquals("Burgers are great, try the Cajun style fries, the diet coke vanella and pib sodas are good too",
                msg.getMessage());
        assertEquals("RELIABLE", msg.reliability.name());
    }

    @Test
    public void testName() throws Exception {
        SocialNetworkKraken kraken = new FoursquareKraken(customer, connectionMock);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }

}
