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
import org.social.core.user.Customer

@TestFor(OpenTableSocialCrawler)
@Mock([Customer, Network, Classification])
public class OpenTableSocialCrawlerTest {

    def SocialCrawler jsoupCrawler
    def customer

    @Before
    public void setUp() throws Exception {
        customer = new Customer()
        customer.save(validate: false)

        def network = new Network()
        network.id = NetworkConst.OPENTABLE.getName()
        network.save(validate: false)

        jsoupCrawler = new OpenTableSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "OpenTableWolfgangsTest_WithoutPagination.html")
    }

    @Test
    public void testGetDocument() throws Exception {
        assertNotNull(jsoupCrawler.getDocument())
        assertEquals("Wolfgang's Steak House - 54th Street Reviews - Rated by OpenTable Diners", jsoupCrawler
                        .getDocument().title())
    }

    @Test
    public void testGetContainerOfReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        assertNotNull(reviewContainer)
        assertTrue(reviewContainer.size() > 0)
        assertEquals("BVRRSDisplayContentBody", reviewContainer.get(0).className())
    }

    @Test
    public void testGetNextPageLinkFromPagination() throws Exception {
        SocialCrawler secondJsoupCrawler = new OpenTableSocialCrawler(new MockBaseCrawler(), "test/resources/",
                        "OpenTableWolfgangsTest_WithPagination.html")

        Element body = secondJsoupCrawler.getDocument().body()

        String nextLink = secondJsoupCrawler.getNextPageFromPagination(body)

        assertNotNull(nextLink)
        assertEquals("/0938/41533/reviews.htm?page=2", nextLink)
    }

    @Test
    public void testExtractReviewData() throws Exception {
        Element body = jsoupCrawler.getDocument().body()
        Elements reviewContainer = jsoupCrawler.getReviewDataContainer(body)

        List<Message> result = jsoupCrawler.extractReviewDataFromHtml(reviewContainer, jsoupCrawler.getDocument()
                        .head(), customer)

        assertTrue(result.size() >= 10)

        // assertTrue(result.get(0).getMessage().startsWith("Even though Vapiano is one of my favorite places"));
        assertEquals("n/a", result.get(0).getNetworkUserName())
        assertEquals("n/a", result.get(0).getNetworkUserId())
        assertEquals("en-US", result.get(0).getLanguage())
        assertEquals("OPENTABLE", result.get(0).network.id)
        assertEquals(1, result.get(0).customer.id)
        // assertEquals("4.0", result.get(0).getNetworkUserRating());
    }
}
