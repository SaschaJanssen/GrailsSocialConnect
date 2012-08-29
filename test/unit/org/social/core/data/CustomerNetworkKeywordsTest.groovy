package org.social.core.data

import static org.junit.Assert.assertEquals
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.social.grails.user.Customer
import org.social.grails.user.Keyword
import org.social.grails.user.KeywordType

@TestFor(CustomerNetworkKeywords)
@Mock([Keyword, Customer, KeywordType])
public class CustomerNetworkKeywordsTest {

    org.social.core.data.CustomerNetworkKeywords unk = null

    public CustomerNetworkKeywordsTest() {
    }

    @Before
    public void setUp() {

        Customer customer = new Customer()
        KeywordType type = new KeywordType()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.setCustomer(customer)

        type.id = org.social.core.constants.KeywordTypeConst.HASH
        keywords.setKeywordType(type)
        keywords.setKeyword("#Vapiano")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        keywords.setCustomer(customer)

        type = new KeywordType()
        type.id = org.social.core.constants.KeywordTypeConst.QUERY
        keywords.setKeywordType(type)
        keywords.setKeyword("Vapiano")
        keywordListForNetwork.add(keywords)

        unk = new CustomerNetworkKeywords(keywordListForNetwork)
    }

    @Test
    public void testGetTwitterHash() {
        String hash = unk.getHashForNetwork()
        assertEquals("#Vapiano", hash)
    }

    @Test
    public void testGetTwitterQuery() {
        String query = unk.getQueryForNetwork()
        assertEquals("Vapiano", query)
    }

    @Test
    public void testGetTwitterMentioned() {
        String mentioned = unk.getMentionedForNetwork()
        assertEquals("", mentioned)
    }
}
