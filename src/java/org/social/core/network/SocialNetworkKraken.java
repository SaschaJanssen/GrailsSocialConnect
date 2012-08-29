package org.social.core.network;

import java.util.List;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.grails.network.Message;
import org.social.grails.user.Customer;
import org.social.grails.user.Keyword;

public abstract class SocialNetworkKraken {

    protected CustomerNetworkKeywords customerNetworkKeywords;
    protected Customer customer;

    protected SocialNetworkKraken(Customer customer) {
        this.customer = customer;
    }

    public abstract FilteredMessageList fetchAndCraftMessages();

    public CustomerNetworkKeywords getCustomerNetworkKeywords() {
        return customerNetworkKeywords;
    }

    protected void getCustomersKeywords(String networkName) {
        List<Keyword> keywords = Keyword.findAllByCustomerAndNetwork(customer, networkName);
        customerNetworkKeywords = new CustomerNetworkKeywords(keywords);
    }

    /**
     * Categories messages after reliability and there sentiment.
     * 
     * @param messagesToCraft
     * @return
     */
    protected FilteredMessageList reliabilityAndSentimentMessages(List<Message> messagesToCraft) {
        DataCrafter crafter = new DataCrafter(messagesToCraft);
        return crafter.reliabilityAndSentimentCrafter(customerNetworkKeywords);
    }

    /**
     * Categories messages only after there sentiment.
     * 
     * @param messagesToCraft
     * @return
     */
    protected FilteredMessageList sentimentMessages(List<Message> messagesToCraft) {
        DataCrafter crafter = new DataCrafter(messagesToCraft);
        return crafter.sentimentCrafter();
    }
}