package org.social.core.network.connection

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import net.sf.json.JSONObject

import org.junit.Before
import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.core.data.CustomerNetworkKeywords
import org.social.core.network.IntegrationSetup
import org.social.core.query.TwitterQuery
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

public class TwitterConnectionTest extends IntegrationSetup {

    def TwitterConnectionTest() {
        super()
    }

    private CustomerNetworkKeywords cnk

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()

        keywords.customer = customer
        keywords.setKeywordType(KeywordTypeConst.QUERY)
        keywords.setKeyword("Vapiano")
        keywordListForNetwork.add(keywords)

        cnk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testTwitterSearch() throws Exception {
        TwitterQuery query = new TwitterQuery(cnk)
        query.setLanguage("en")

        SocialNetworkConnection con = new TwitterConnection()
        List<JSONObject> result = con.getRemoteData(query)

        assertNotNull(result)
        assertTrue(result.size() > 0)
    }
}
