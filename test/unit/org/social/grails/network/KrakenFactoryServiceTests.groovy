package org.social.grails.network



import static org.junit.Assert.assertTrue
import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.core.network.FacebookKraken
import org.social.core.network.FoursquareKraken
import org.social.core.network.OpenTableKraken
import org.social.core.network.QypeKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.TripAdvisorKraken
import org.social.core.network.TwitterKraken
import org.social.core.network.YelpKraken
import org.social.core.network.ZagatKraken
import org.social.grails.Keyword
import org.social.grails.customer.Customer

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(KrakenFactoryService)
@Mock([Customer, Keyword])
class KrakenFactoryServiceTests {
    def krakenFactoryService = new KrakenFactoryService()

    @Before
    public void setUp() {
        Keyword.metaClass.static.findAllByCustomerAndNetwork = {customer, networkName ->
            
            def keyword = new Keyword()
            keyword.customer = customer
            keyword.network = NetworkConst.FOURSQUARE
            keyword.keywordType = KeywordTypeConst.QUERY
            keyword.keyword = "Vapiano"
            
            [keyword]
        }
    }
    
    @Test
    public void testFactoryForAllNetworks() {

        SocialNetworkKraken snk = krakenFactoryService.getInstance(NetworkConst.FACEBOOK, new Customer())
        assertTrue(snk instanceof FacebookKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.TWITTER, new Customer())
        assertTrue(snk instanceof TwitterKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.YELP, new Customer())
        assertTrue(snk instanceof YelpKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.OPENTABLE, new Customer())
        assertTrue(snk instanceof OpenTableKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.TRIPADVISOR, new Customer())
        assertTrue(snk instanceof TripAdvisorKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.ZAGAT, new Customer())
        assertTrue(snk instanceof ZagatKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.QYPE, new Customer())
        assertTrue(snk instanceof QypeKraken)

        snk = krakenFactoryService.getInstance(NetworkConst.FOURSQUARE, new Customer())
        assertTrue(snk instanceof FoursquareKraken)
    }
}
