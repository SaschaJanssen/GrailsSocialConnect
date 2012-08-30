package org.social.core.network.connection

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import net.sf.json.JSONObject

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.core.network.IntegrationSetup
import org.social.core.query.FoursquareQuery
import org.social.core.query.Query
import org.social.grails.Keyword
import org.social.grails.customer.Customer

public class FoursquareConnectionTest extends IntegrationSetup {

    def FoursquareConnectionTest() {
        super()
    }

    private CustomerNetworkKeywords cnk

    @Before
    public void setUp() throws Exception {
        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = new Customer()
        keywords.setKeywordType(KeywordTypeConst.PAGE)
        keywords.setKeyword("4b68590cf964a52012732be3")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testFoursquareSearch() {
        Query query = new FoursquareQuery(cnk)

        SocialNetworkConnection con = new FoursquareConnection()
        List<JSONObject> result = con.getRemoteData(query)

        assertNotNull(result)
        assertTrue(result.size() >= 5)
    }
}
