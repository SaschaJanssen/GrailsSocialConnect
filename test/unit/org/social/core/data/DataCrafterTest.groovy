package org.social.core.data

import static org.junit.Assert.assertEquals
import grails.test.mixin.*

import org.junit.Test
import org.social.core.constants.KeywordTypeConst
import org.social.grails.network.Message
import org.social.grails.user.Customer
import org.social.grails.user.Keyword
import org.social.grails.user.KeywordType

@TestFor(DataCrafter)
@Mock([Keyword, Customer, Message, KeywordType])
public class DataCrafterTest {

    @Test
    public void testCrafter() {
        DataCrafter crafter = new DataCrafter(setUpDemoData_One())

        Customer customer = new Customer()
        KeywordType type = new KeywordType()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.setCustomer(customer)

        type.id = KeywordTypeConst.HASH
        keywords.setKeywordType(type)
        keywords.setKeyword("#WOLFGANGSSTEAKH")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        keywords.setCustomer(customer)

        type = new KeywordType()
        type.id = KeywordTypeConst.QUERY
        keywords.setKeywordType(type)
        keywords.setKeyword("Wolfgangs")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        keywords.setCustomer(customer)

        type = new KeywordType()
        type.id = KeywordTypeConst.MENTIONED
        keywords.setKeywordType(type)
        keywords.setKeyword("@WOLFGANGSSTEAKH")
        keywordListForNetwork.add(keywords)

        CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(keywordListForNetwork)

        FilteredMessageList result = crafter.reliabilityAndSentimentCrafter(cnk)

        assertEquals(2, result.countPositivMessages())
        assertEquals(2, result.countNegativeMessages())
    }

    @Test
    public void testCrafter_2() {
        DataCrafter crafter = new DataCrafter(setUpDemoData_Two())

        Customer customer = new Customer()
        KeywordType type = new KeywordType()

        List<Keyword> keywordListForNetwork = new ArrayList<Keyword>()
        Keyword keywords = new Keyword()
        keywords.setCustomer(customer)

        type.id = KeywordTypeConst.HASH
        keywords.setKeywordType(type)
        keywords.setKeyword("#Vapiano")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        keywords.setCustomer(customer)

        type = new KeywordType()
        type.id = KeywordTypeConst.QUERY
        keywords.setKeywordType(type)
        keywords.setKeyword("Vapiano")
        keywordListForNetwork.add(keywords)

        keywords = new Keyword()
        keywords.setCustomer(customer)

        type = new KeywordType()
        type.id = KeywordTypeConst.MENTIONED
        keywords.setKeywordType(type)
        keywords.setKeyword("@Vapiano")
        keywordListForNetwork.add(keywords)

        CustomerNetworkKeywords cnk = new CustomerNetworkKeywords(keywordListForNetwork)

        FilteredMessageList result = crafter.reliabilityAndSentimentCrafter(cnk)

        assertEquals(3, result.countPositivMessages())
        assertEquals(3, result.countNegativeMessages())
    }

    private List<Message> setUpDemoData_Two() {
        Message demoData = new Message()

        List<Message> rawData = new ArrayList<Message>()
        demoData.setMessage("RT @girodicoppi: Thank our sponsors, or yours! Tweet thx to a Giro or @VirginiaCycling team sponsor, tag it #girodicoppi. You could win @Vapiano_USA gift!")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("I just ousted Sebi L. as the mayor of Vapiano on @foursquare! http://t.co/H068E93A")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Vapiano Surfers Paradise - great food &amp; wine! http://t.co/PBcmtSo4")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Thank our sponsors, or yours! Tweet thx to a @girodicoppi or @MABRA_org team sponsor, tag it #girodicoppi. You could win @Vapiano_USA gift!")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("@vapiano")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Just saw a girl who looks like Lil Wayne. (@ Vapiano w/ @nitinalabur) http://t.co/nWeXfd45")
        rawData.add(demoData)

        return rawData
    }

    private List<Message> setUpDemoData_One() {
        Message demoData = new Message()

        List<Message> rawData = new ArrayList<Message>()
        demoData.setMessage("Had wolfgangs for brunch. Today is already great.")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Wolfgangs Vault  Weekly Be genuine. Be full of plenty. Feel popular. http://t.co/FGtbpFfj")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Somebody should go to Real Food or Wolfgangs with me to celebrate the new pad not everybody at once lol")
        rawData.add(demoData)

        demoData = new Message()
        demoData.setMessage("Killin some chest and biceps in the dungeon aka Wolfgangs gym w/ @Bobbypuryear")
        rawData.add(demoData)
        return rawData
    }
}
