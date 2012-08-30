package org.social.core.query

import static org.junit.Assert.assertEquals
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

@TestFor(FoursquareQuery)
@Mock([Customer, Keyword])
public class FoursquareQueryTest {

    CustomerNetworkKeywords cnk

    @Before
    public void setUp() {
        def customer = new Customer()
        customer.save()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        def keywords = new Keyword()
        keywords.customer = customer
        keywords.keywordType = KeywordTypeConst.PAGE
        keywords.setKeyword("4b68590cf964a52012732be3")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testConstructQuery() {
        Query query = new FoursquareQuery(cnk)
        assertEquals(
                        query.getSearchUrl()
                        + "4b68590cf964a52012732be3/tips?sort=recent&v=20120808&client_secret=THA353SUWBOA15AVQ5DBDAJ3J3RMYTN0X1YSBYHKUO1MRXCB&client_id=E3ZI3TJQMTKROQ5EUOBMCXFTGNGK5E3NWDCNO3Q5KQIDEOUW",
                        query.constructQuery())
    }
}
