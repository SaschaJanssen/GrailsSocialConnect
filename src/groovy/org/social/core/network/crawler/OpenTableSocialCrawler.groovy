package org.social.core.network.crawler

import org.jsoup.nodes.Element
import org.social.core.constants.Networks

class OpenTableSocialCrawler extends SocialCrawler {

    public OpenTableSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        super(crawler, baseUrl, endpoint)
    }

    @Override
    def String extractNetworkUserRatingData(Element ratingElement) {
        return ratingElement.text()
    }

    @Override
    def String getLanguageFromHeadMetaData(Element headerElements) {
        return headerElements.parent().attr("lang")
    }

    @Override
    public String getNextPageFromPagination(Element body) {
        String nextPage = null

        Element nextPageLink = body.select("span.BVRRNextPage a").first()
        if (nextPageLink != null) {
            nextPage = nextPageLink.attr("href")
            nextPage = nextPage.substring(nextPage.indexOf(".com") + 4)
        }
        return nextPage
    }

    @Override
    def String getNetworkName() {
        return Networks.OPENTABLE.name()
    }

    @Override
    def String getUserIdFromUserInfo(Element userInfo) {
        return "n/a"
    }

    @Override
    def String getUserNameFromUserInfo(Element userInfo) {
        return "n/a"
    }

    @Override
    def String getPropertyFileName() {
        return "grails-app/conf/openTable.properties"
    }
}
