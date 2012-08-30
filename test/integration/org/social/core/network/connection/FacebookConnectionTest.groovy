package org.social.core.network.connection

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import net.sf.json.JSONObject

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.core.network.IntegrationSetup
import org.social.core.query.FacebookQuery
import org.social.grails.Keyword
import org.social.grails.customer.Customer

public class FacebookConnectionTest extends IntegrationSetup {

    def FacebookConnectionTest() {
        super()
    }

    private CustomerNetworkKeywords cnk

    @Before
    public void setUp() throws Exception {
        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.customer = new Customer()
        keywords.setKeywordType(KeywordTypeConst.QUERY)
        keywords.setKeyword("Vapiano")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testFacebookSearch() {
        FacebookQuery query = new FacebookQuery(cnk)

        query.setLanguage("en_us")

        SocialNetworkConnection con = new FacebookConnection()
        List<JSONObject> result = con.getRemoteData(query)

        assertNotNull(result)
        assertTrue(result.size() >= 1)
    }
}
