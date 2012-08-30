package org.social.core.query

import static org.junit.Assert.assertEquals
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.grails.Keyword
import org.social.grails.customer.Customer

@TestFor(FacebookQuery)
@Mock([Customer, Keyword])
public class FacebookQueryTest {

    def CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = KeywordTypeConst.QUERY
        keywords.setKeyword("Test")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() {
        FacebookQuery query = new FacebookQuery(cnk)
        query.setSince("yesterday")
        query.setType("post")
        query.setLanguage("en_Us")

        assertEquals(query.getSearchUrl() + "?q=Test&type=post&since=yesterday&limit=1500&locale=en_Us",
                        query.constructQuery())

        query = new FacebookQuery(cnk)
        query.setType("post")
        query.setLanguage("en_Us")

        assertEquals(query.getSearchUrl() + "?q=Test&type=post&limit=1500&locale=en_Us", query.constructQuery())
    }
}
