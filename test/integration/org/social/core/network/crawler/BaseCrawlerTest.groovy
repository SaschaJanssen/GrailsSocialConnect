package org.social.core.network.crawler

import static org.junit.Assert.assertNotNull

import org.jsoup.nodes.Document
import org.junit.Test
import org.social.core.network.IntegrationSetup

public class BaseCrawlerTest extends IntegrationSetup {

    def BaseCrawlerTest() {
        super()
    }

    @Test
    public void testJsoupCrawler() throws Exception {
        BaseCrawler crawler = new JsoupBaseCrwaler()
        Document doc = crawler.crwal("https://www.yelp.com/biz/vapiano-new-york-2?sort_by=date_desc")

        assertNotNull(doc)
    }
}
