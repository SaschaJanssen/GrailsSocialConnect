package org.social.core.query
import static org.junit.Assert.assertEquals
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.grails.user.Customer
import org.social.grails.user.Keyword
import org.social.grails.user.KeywordType

@TestFor(TwitterQuery)
@Mock([Customer, Keyword, KeywordType])
public class TwitterQueryTest {

    CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        def type = new KeywordType()
        type.id = KeywordTypeConst.QUERY.getName()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = type
        keywords.setKeyword("Test")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        type = new KeywordType()
        type.id = KeywordTypeConst.HASH.getName()
        keywords.customer = customer
        keywords.keywordType = type

        keywords.setKeyword("#Test")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        type = new KeywordType()
        type.id = KeywordTypeConst.MENTIONED.getName()
        keywords.customer = customer
        keywords.keywordType = type
        keywords.setKeyword("@Test")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() {
        TwitterQuery query = new TwitterQuery(cnk)
        query.setLanguage("de")
        query.setMinus("-Tree")
        query.setSince("2012-06-01")

        assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test+--Tree&lang=de&since=2012-06-01&rpp=100",
                        query.constructQuery())

        query = new TwitterQuery(cnk)
        query.setLanguage("de")

        assertEquals(query.getSearchUrl() + "?q=Test+OR+%23Test+OR+%40Test&lang=de&rpp=100", query.constructQuery())
    }
}
