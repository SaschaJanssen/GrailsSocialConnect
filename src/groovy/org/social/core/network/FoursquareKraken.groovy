package org.social.core.network

import net.sf.json.JSONObject

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.social.core.constants.ClassificationConst
import org.social.core.constants.NetworkConst
import org.social.core.data.FilteredMessageList
import org.social.core.network.connection.SocialNetworkConnection
import org.social.core.query.FoursquareQuery
import org.social.core.query.Query
import org.social.core.user.Customer
import org.social.core.util.UtilDateTime

public class FoursquareKraken extends SocialNetworkKraken {

    private Logger logger = LoggerFactory.getLogger(this.getClass())
    private final SocialNetworkConnection connection

    public FoursquareKraken(Customer customer, SocialNetworkConnection fbConnection) {
        super(customer)
        connection = fbConnection
        getCustomersKeywords(NetworkConst.FOURSQUARE.getName())
    }

    @Override
    public FilteredMessageList fetchAndCraftMessages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Fetch posts from Foursquare for customer: " + super.customer.getCustomerId())
        }

        Query query = buildQueryFromKeywords()

        List<JSONObject> searchResult = connection.getRemoteData(query)
        List<Message> resultMessages = extractMessageData(searchResult)

        FilteredMessageList filteredResultMessages = sentimentMessages(resultMessages)

        return filteredResultMessages
    }

    private Query buildQueryFromKeywords() {
        Query query = new FoursquareQuery(super.customerNetworkKeywords)
        if (customer.getLastNetworkAccess() != null) {
            query.setSince(customer.getLastNetworkAccess().toString())
        }
        return query
    }

    private List<Message> extractMessageData(List<JSONObject> searchResult) {

        List<Message> results = new ArrayList<Message>()

        for (JSONObject object : searchResult) {
            Message messageData = new Message()
            messageData.network = Network.get(NetworkConst.FOURSQUARE.getName())

            messageData.customer = this.customer

            JSONObject user = object.getJSONObject("user")
            messageData.setNetworkUserName(user.getString("firstName"))
            messageData.setNetworkUserId(user.getString("id"))

            String messageDate = object.getString("createdAt")
            messageData.setNetworkMessageDate(UtilDateTime.toTimestamp(messageDate))

            messageData.setMessage(object.getString("text"))
            messageData.setMessageReceivedDate(UtilDateTime.nowTimestamp())

            messageData.setReliability(ClassificationConst.RELIABLE.getName())
            messageData.setNetworkUserRating("n/a")
            results.add(messageData)
        }

        return results
    }
}
