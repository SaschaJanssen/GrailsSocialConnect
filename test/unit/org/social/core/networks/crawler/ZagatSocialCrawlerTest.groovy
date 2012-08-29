package org.social.core.networks.crawler

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import grails.test.mixin.*

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.junit.Before
import org.junit.Test
import org.social.core.constants.NetworkConst
import org.social.core.network.crawler.SocialCrawler
import org.social.core.network.crawler.ZagatSocialCrawler
import org.social.grails.network.Message
import org.social.grails.network.Network
import org.social.grails.user.Customer

@TestFor(ZagatSocialCrawler)
@Mock([Customer, Network])
public class ZagatSocialCrawlerTest {

    def SocialCrawler jsoupCrawler
    def customer

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save()

        def network = new Network()
        network.id = NetworkConst.ZAGAT.getName()
        network.save()

        // http://www.zagat.com/r/n/five-guys-queens-3/reviews
        jsoupCrawler = new ZagatSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "ZagatFivaGuysTest_WithoutPagination.html")
    }

    @Test
    public void testGetDocument() throws Exception {
        assertNotNull(jsoupCrawler.getDocument())
        assertEquals("Five Guys | Zagat", jsoupCrawler.getDocument().title())
    }

    @Test
    public void testGetContainerOfReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        assertNotNull(reviewContainer)
        assertTrue(reviewContainer.size() > 0)
        assertEquals(
                        "view view-zagat-comments-recent view-id-zagat_comments_recent view-display-id-page view-dom-id-1 view-zagat-comments-recent view-id-zagat_comments_recent view-display-id-page view-dom-id-1",
                        reviewContainer.get(0).className())
    }

    @Test
    public void testGetNextPageLinkFromPagination() throws Exception {
        SocialCrawler secondJsoupCrawler = new ZagatSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "ZagatFivaGuysTest_WithPagination.html")

        Element body = secondJsoupCrawler.getDocument().body()

        String nextLink = secondJsoupCrawler.getNextPageFromPagination(body)

        assertNotNull(nextLink)
        assertEquals("/r/n/five-guys-queens-3/reviews?page=1", nextLink)
    }

    @Test
    public void testExtractReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        List<Message> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, jsoupCrawler.getDocument()
                        .head(), customer)

        assertTrue(result.size() >= 10)

        assertTrue(result.get(0).getMessage().startsWith("Amazingly juicy and nice burgers and"))
        assertEquals("Ray Y", result.get(0).getNetworkUserName())
        assertEquals("4355100", result.get(0).getNetworkUserId())
        assertEquals("en", result.get(0).getLanguage())
        assertEquals("ZAGAT", result.get(0).network.id)
        assertEquals(1, result.get(0).customer.id)
        assertEquals("n/a", result.get(0).getNetworkUserRating())
    }
}
