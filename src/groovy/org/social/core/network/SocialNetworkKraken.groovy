package org.social.core.network

import org.social.core.data.CustomerNetworkKeywords
import org.social.core.data.DataCrafter
import org.social.core.data.FilteredMessageList
import org.social.core.user.Customer
import org.social.core.user.Keyword

public abstract class SocialNetworkKraken {

    protected CustomerNetworkKeywords customerNetworkKeywords
    protected Customer customer

    protected SocialNetworkKraken(Customer customer) {
        this.customer = customer
    }

    public abstract FilteredMessageList fetchAndCraftMessages()

    public CustomerNetworkKeywords getCustomerNetworkKeywords() {
        return customerNetworkKeywords
    }

    protected void getCustomersKeywords(String networkName) {
        Long customerId = customer.id

        List<Keyword> keywords = keywordDao.getMappedKeywordByCustomerAndNetwork(customerId, networkName)
        customerNetworkKeywords = new CustomerNetworkKeywords(keywords)
    }

    /**
     * Categories messages after reliability and there sentiment.
     * 
     * @param messagesToCraft
     * @return
     */
    protected FilteredMessageList reliabilityAndSentimentMessages(List<Message> messagesToCraft) {
        DataCrafter crafter = new DataCrafter(messagesToCraft)
        return crafter.reliabilityAndSentimentCrafter(customerNetworkKeywords)
    }

    /**
     * Categories messages only after there sentiment.
     * 
     * @param messagesToCraft
     * @return
     */
    protected FilteredMessageList sentimentMessages(List<Message> messagesToCraft) {
        DataCrafter crafter = new DataCrafter(messagesToCraft)
        return crafter.sentimentCrafter()
    }
}