package org.social.core.query

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.grails.Keyword
import org.social.grails.customer.Customer

@TestFor(OpenTableQuery)
@Mock([Customer, Keyword])
public class OpenTableQueryTest {

    CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        def keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = KeywordTypeConst.PAGE
        keywords.setKeyword("/biz/restaurant")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() throws Exception {
        OpenTableQuery yq = new OpenTableQuery(cnk)

        assertEquals("/biz/restaurant/reviews.htm", yq.constructQuery())
        assertNotNull(yq.getSearchUrl())
    }
}
