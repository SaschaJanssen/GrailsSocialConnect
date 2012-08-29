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

@TestFor(QypeQuery)
@Mock([Customer, Keyword, KeywordType])
public class QypeQueryTest {

    CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        def type = new KeywordType()
        type.id = KeywordTypeConst.PAGE.getName()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        def keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = type
        keywords.setKeyword("139975")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() {
        QypeQuery query = new QypeQuery(cnk)
        query.setSince("2011-03-01T12:20:06+01:00")
        query.setLanguage("en")

        assertEquals(query.getSearchUrl()
                        + "139975/reviews/en.json?order=date_created&consumer_key=1f9XapFtM974aajgXnTQ", query.constructQuery())
    }
}
