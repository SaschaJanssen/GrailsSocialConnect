package org.social.core.network;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.NetworkConst;
import org.social.core.data.FilteredMessageList;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.TwitterQuery;
import org.social.core.util.UtilDateTime;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

public class TwitterKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SocialNetworkConnection connection;

    public TwitterKraken(Customer customer, SocialNetworkConnection twitterConnection) {
        super(customer);
        connection = twitterConnection;
        this.customer = customer;

        getCustomersKeywords(NetworkConst.TWITTER);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch tweets from Twitter for customer: " + customer.getCustomerId());
        }

        TwitterQuery query = buildQueryFromKeywords();

        List<JSONObject> resultObjects = connection.getRemoteData(query);
        List<Message> resultList = new ArrayList<Message>();
        for (JSONObject jo : resultObjects) {
            resultList.addAll(extractMessageData(jo));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Got " + resultObjects.size() + " tweets from Twitter.");
        }

        FilteredMessageList filteredResultMessages = reliabilityAndSentimentMessages(resultList);

        return filteredResultMessages;
    }

    private TwitterQuery buildQueryFromKeywords() {
        TwitterQuery twitterQuery = new TwitterQuery(super.customerNetworkKeywords);

        String since = UtilDateTime.connvertDateToTwitterTime(customer.getLastNetworkAccess());
        twitterQuery.setSince(since);
        twitterQuery.setLanguage("en");

        return twitterQuery;
    }

    private List<Message> extractMessageData(JSONObject json) {
        List<Message> resultList = new ArrayList<Message>();
        JSONArray resultArray = json.getJSONArray("results");

        for (Object object : resultArray) {
            Message messageData = new Message();

            messageData.setNetwork(NetworkConst.TWITTER);

            messageData.setCustomer(super.customer);

            JSONObject jsonObj = (JSONObject) object;

            messageData.setNetworkUserName(jsonObj.getString("from_user"));
            messageData.setNetworkUserId(jsonObj.getString("from_user_id"));
            messageData.setLanguage(jsonObj.getString("iso_language_code"));

            JSONObject geo = jsonObj.getJSONObject("geo");
            if (!geo.isNullObject() && geo.has("coordinates")) {
                messageData.setGeoLocation(geo.getString("coordinates"));
            }
            messageData.setMessage(jsonObj.getString("text"));

            String createdAt = jsonObj.getString("created_at");
            messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(createdAt));
            messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());
            resultList.add(messageData);
        }

        return resultList;
    }
}
