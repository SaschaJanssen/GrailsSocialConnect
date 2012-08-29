package org.social.core.query

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.grails.user.Customer
import org.social.grails.user.Keyword
import org.social.grails.user.KeywordType

@TestFor(YelpQuery)
@Mock([Customer, Keyword, KeywordType])
public class YelpQueryTest {

    CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        def type = new KeywordType()
        type.id = KeywordTypeConst.PAGE.getName()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = type
        keywords.setKeyword("/biz/restaurant")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() throws Exception {
        YelpQuery yq = new YelpQuery(cnk)

        assertEquals("/biz/restaurant?sort_by=date_desc", yq.constructQuery())
        assertNotNull(yq.getSearchUrl())
    }
}
