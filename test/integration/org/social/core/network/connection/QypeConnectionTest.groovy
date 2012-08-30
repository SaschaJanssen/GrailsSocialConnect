package org.social.core.network.connection

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import net.sf.json.JSONObject

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.core.network.IntegrationSetup
import org.social.core.query.QypeQuery
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

public class QypeConnectionTest extends IntegrationSetup {

    def QypeConnectionTest() {
        super()
    }

    private CustomerNetworkKeywords cnk

    @Before
    public void setUp() throws Exception {
        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = new Customer()
        keywords.setKeywordType(KeywordTypeConst.PAGE)
        keywords.setKeyword("139975")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testQypeSearch() {
        QypeQuery query = new QypeQuery(cnk)
        query.setLanguage("en")
        query.setSince("2012-01-01 18:48:05.123456")

        SocialNetworkConnection con = new QypeConnection()
        List<JSONObject> result = con.getRemoteData(query)

        assertNotNull(result)
        assertTrue(result.size() >= 1)
    }
}
