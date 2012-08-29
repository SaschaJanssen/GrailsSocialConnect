package org.social.core.network



import static org.junit.Assert.assertTrue
import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.user.Customer
import org.social.core.user.Keyword
import org.social.core.user.KeywordType

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(KrakenFactoryService)
@Mock([Customer, Network, Keyword, KeywordType])
class KrakenFactoryServiceTests {
    def krakenFactoryService = new KrakenFactoryService()

    @Before
    public void setUp() {
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName ->
            
            def network = new Network()
            network.id = NetworkConst.FOURSQUARE.getName()
    
            def type = new KeywordType()
            type.id = KeywordTypeConst.QUERY.getName()
    
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = network
            keyword.keywordType = type
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
    }
    
    @Test
    public void testFactoryForAllNetworks() {

        SocialNetworkKraken snk = krakenFactoryService.getInstance("FACEBOOK", new Customer())
        assertTrue(snk instanceof FacebookKraken)

        snk = krakenFactoryService.getInstance("TWITTER", new Customer())
        assertTrue(snk instanceof TwitterKraken)

        snk = krakenFactoryService.getInstance("YELP", new Customer())
        assertTrue(snk instanceof YelpKraken)

        snk = krakenFactoryService.getInstance("OPENTABLE", new Customer())
        assertTrue(snk instanceof OpenTableKraken)

        snk = krakenFactoryService.getInstance("TRIPADVISOR", new Customer())
        assertTrue(snk instanceof TripAdvisorKraken)

        snk = krakenFactoryService.getInstance("ZAGAT", new Customer())
        assertTrue(snk instanceof ZagatKraken)

        snk = krakenFactoryService.getInstance("QYPE", new Customer())
        assertTrue(snk instanceof QypeKraken)

        snk = krakenFactoryService.getInstance("FOURSQUARE", new Customer())
        assertTrue(snk instanceof FoursquareKraken)
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() throws Exception {
        krakenFactoryService.getInstance("FOOBAA", new Customer())
    }
}
