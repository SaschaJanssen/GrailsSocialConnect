package org.social.core.networks;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.social.core.network.crawler.BaseCrawler;

public class MockYelpBaseCrawler implements BaseCrawler {

    @Override
    public Document crwal(String url) throws IOException {
        File fi = new File("test/resources/YelpVapianoTest_WithoutPagination.html");
        return Jsoup.parse(fi, "UTF-8");
    }

}
