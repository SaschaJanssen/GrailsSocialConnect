package org.social.core.network;

import java.util.List;

import org.social.core.constants.NetworkConst;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.data.DataCrafter;
import org.social.core.data.FilteredMessageList;
import org.social.grails.Keyword;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

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

    protected void getCustomersKeywords(NetworkConst network) {
        List<Keyword> keywords = Keyword.findAllByCustomerAndNetwork(customer, network);
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