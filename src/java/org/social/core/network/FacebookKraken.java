package org.social.core.network;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.NetworkConst;
import org.social.core.data.FilteredMessageList;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.FacebookQuery;
import org.social.core.util.UtilDateTime;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

public class FacebookKraken extends SocialNetworkKraken {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final SocialNetworkConnection connection;

    public FacebookKraken(Customer customer, SocialNetworkConnection fbConnection) {
        super(customer);
        connection = fbConnection;
        getCustomersKeywords(NetworkConst.FACEBOOK);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (log.isDebugEnabled()) {
            log.debug("Fetch posts from Facebook for customer: " + super.customer.getCustomerId());
        }

        FacebookQuery query = buildQueryFromKeywords();

        List<JSONObject> searchResult = connection.getRemoteData(query);
        List<Message> resultMessages = extractMessageData(searchResult);

        FilteredMessageList filteredResultMessages = reliabilityAndSentimentMessages(resultMessages);

        return filteredResultMessages;
    }

    private FacebookQuery buildQueryFromKeywords() {
        FacebookQuery fbQuery = new FacebookQuery(super.customerNetworkKeywords);

        String since = UtilDateTime.connvertDateToFacebookTime(super.customer.getLastNetworkAccess());
        fbQuery.setSince(since);
        fbQuery.setType("post");
        fbQuery.setLanguage("en_US");

        return fbQuery;
    }

    private List<Message> extractMessageData(List<JSONObject> searchResult) {

        List<Message> results = new ArrayList<Message>();

        for (JSONObject object : searchResult) {
            if (!object.has("message")) {
                // object could be ignored if no message attribute is set.
                continue;
            }
            Message messageData = new Message();

            messageData.setNetwork(NetworkConst.FACEBOOK);

            messageData.setCustomer(customer);

            JSONObject userData = object.getJSONObject("from");
            messageData.setNetworkUserName(userData.getString("name"));
            messageData.setNetworkUserId(userData.getString("id"));

            String fbMessageDate = object.getString("created_time");
            messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(fbMessageDate));

            messageData.setMessage(object.getString("message"));
            messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());

            results.add(messageData);
        }

        return results;
    }
}
