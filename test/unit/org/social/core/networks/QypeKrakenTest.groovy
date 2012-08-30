package org.social.core.networks;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.QypeKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.connection.SocialNetworkConnection
import org.social.grails.network.Message
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

@TestFor(QypeKraken)
@Mock([Message, Keyword, Customer])
public class QypeKrakenTest {

    private Customer customer;
    private SocialNetworkConnection connectionMock;

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()
        
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName -> 
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = NetworkConst.QYPE
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
        connectionMock = new QypeConnectionMock();
    }

    @Test
    public void test() {
        SocialNetworkKraken kraken = new QypeKraken(customer, connectionMock);
        FilteredMessageList results = kraken.fetchAndCraftMessages();

        assertNotNull(results);
        assertEquals(1, results.countPositivMessages());
        assertEquals(0, results.countNegativeMessages());
    }

    @Test
    public void testGetNetworkKeyword() throws Exception {
        SocialNetworkKraken kraken = new QypeKraken(customer, connectionMock);
        assertEquals("Vapiano", kraken.getCustomerNetworkKeywords().getQueryForNetwork());
    }
}
