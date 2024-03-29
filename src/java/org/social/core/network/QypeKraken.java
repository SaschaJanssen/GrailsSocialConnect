package org.social.core.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.ClassificationConst;
import org.social.core.constants.NetworkConst;
import org.social.core.data.FilteredMessageList;
import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.QypeQuery;
import org.social.core.util.UtilDateTime;
import org.social.grails.Message;
import org.social.grails.customer.Customer;

public class QypeKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SocialNetworkConnection connection;

    public QypeKraken(Customer customer, SocialNetworkConnection fbConnection) {
        super(customer);
        connection = fbConnection;
        getCustomersKeywords(NetworkConst.QYPE);
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch posts from Qype for customer: " + super.customer.getCustomerId());
        }

        QypeQuery query = buildQueryFromKeywords();

        List<JSONObject> searchResult = connection.getRemoteData(query);
        List<Message> resultMessages = extractMessageData(searchResult);

        FilteredMessageList filteredResultMessages = super.sentimentMessages(resultMessages);

        return filteredResultMessages;
    }

    private QypeQuery buildQueryFromKeywords() {
        QypeQuery query = new QypeQuery(super.customerNetworkKeywords);

        Date lastNetworkAccess = super.customer.getLastNetworkAccess();

        if (lastNetworkAccess != null) {
            query.setSince(lastNetworkAccess.toString());
        }
        query.setLanguage("en");

        return query;
    }

    private List<Message> extractMessageData(List<JSONObject> searchResult) {

        List<Message> results = new ArrayList<Message>();

        for (JSONObject object : searchResult) {
            Message messageData = new Message();

            messageData.setNetwork(NetworkConst.QYPE);

            messageData.setCustomer(super.customer);

            JSONArray links = object.getJSONArray("links");
            for (Object link : links) {
                JSONObject joLink = (JSONObject) link;
                if (joLink.containsKey("rel") && "http://schemas.qype.com/user".equals(joLink.getString("rel"))) {
                    String title = joLink.getString("title");
                    String href = joLink.getString("href");
                    String networkUserId = href.substring(href.lastIndexOf("/") + 1);

                    messageData.setNetworkUserName(title);
                    messageData.setNetworkUserId(networkUserId);
                    break;
                }
            }

            String messageDate = object.getString("created");
            messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(messageDate));

            messageData.setMessage(object.getString("content_text"));
            messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp());

            messageData.setLanguage(object.getString("language"));

            messageData.setReliability(ClassificationConst.Reliability.RELIABLE);
            messageData.setNetworkUserRating(object.getString("rating"));
            results.add(messageData);
        }

        return results;
    }
}
