package org.social.core.networks.crawler

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import grails.test.mixin.*

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.junit.Before
import org.junit.Test
import org.social.core.classification.Classification
import org.social.core.constants.NetworkConst
import org.social.core.network.Message
import org.social.core.network.Network
import org.social.core.network.crawler.OpenTableSocialCrawler
import org.social.core.network.crawler.SocialCrawler
import org.social.core.network.crawler.TripAdvisorSocialCrawler
import org.social.core.user.Customer

@TestFor(OpenTableSocialCrawler)
@Mock([Customer, Network, Classification])
public class TripAdvisorSocialCrawlerTest {

    def SocialCrawler jsoupCrawler
    def customer

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()

        def network = new Network()
        network.id = NetworkConst.TRIPADVISOR.getName()
        network.save()

        // http://www.tripadvisor.de/Restaurant_Review-g60763-d1846484-Reviews-Vapiano-New_York_City_New_York.html#REVIEWS
        jsoupCrawler = new TripAdvisorSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "TripAdvisorTest_WithPagination.html")
    }

    @Test
    public void testGetDocument() throws Exception {
        assertNotNull(jsoupCrawler.getDocument())
        assertEquals("Vapiano, New York - Bewertungen und Fotos - TripAdvisor", jsoupCrawler.getDocument().title())
    }

    @Test
    public void testGetContainerOfReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        assertNotNull(reviewContainer)
        assertTrue(reviewContainer.size() > 0)
        assertEquals("deckB review_collection", reviewContainer.get(0).className())
    }

    @Test
    public void testGetNextPageLinkFromPagination() throws Exception {
        SocialCrawler secondJsoupCrawler = new TripAdvisorSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "TripAdvisorTest_WithPagination.html")

        Element body = secondJsoupCrawler.getDocument().body()

        String nextLink = secondJsoupCrawler.getNextPageFromPagination(body)

        assertNotNull(nextLink)
        assertEquals("/Restaurant_Review-g60763-d1846484-Reviews-or10-Vapiano-New_York_City_New_York.html", nextLink)
    }

    @Test
    public void testExtractReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        List<Message> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, jsoupCrawler.getDocument()
                        .head(), customer)

        assertTrue(result.size() >= 10)

        assertTrue(result.get(0).getMessage().startsWith("U die Art der Pasta wählen"))
        assertEquals("73kamla", result.get(0).getNetworkUserName())
        assertEquals("n/a", result.get(0).getNetworkUserId())
        assertEquals("en", result.get(0).getLanguage())
        assertEquals("TRIPADVISOR", result.get(0).network.id)
        assertEquals(1, result.get(0).customer.id)
        assertEquals("4", result.get(0).getNetworkUserRating())
    }
}
