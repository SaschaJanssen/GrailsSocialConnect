package org.social.grails.network

import org.social.core.constants.NetworkConst
import org.social.core.network.FacebookKraken
import org.social.core.network.FoursquareKraken
import org.social.core.network.OpenTableKraken
import org.social.core.network.QypeKraken
import org.social.core.network.SocialNetworkKraken
import org.social.core.network.TripAdvisorKraken
import org.social.core.network.TwitterKraken
import org.social.core.network.YelpKraken
import org.social.core.network.ZagatKraken
import org.social.core.network.connection.FacebookConnection
import org.social.core.network.connection.FoursquareConnection
import org.social.core.network.connection.QypeConnection
import org.social.core.network.connection.TwitterConnection
import org.social.core.network.crawler.JsoupBaseCrwaler
import org.social.grails.user.Customer

class KrakenFactoryService {

    public SocialNetworkKraken getInstance(String network, Customer customer)
    throws IllegalArgumentException {

        if (NetworkConst.FACEBOOK.getName().equals(network)) {
            return new FacebookKraken(customer, new FacebookConnection())
        } else if (NetworkConst.TWITTER.getName().equals(network)) {
            return new TwitterKraken(customer, new TwitterConnection())
        } else if (NetworkConst.YELP.getName().equals(network)) {
            return new YelpKraken(customer, new JsoupBaseCrwaler())
        } else if (NetworkConst.OPENTABLE.getName().equals(network)) {
            return new OpenTableKraken(customer, new JsoupBaseCrwaler())
        } else if (NetworkConst.TRIPADVISOR.getName().equals(network)) {
            return new TripAdvisorKraken(customer, new JsoupBaseCrwaler())
        } else if (NetworkConst.ZAGAT.getName().equals(network)) {
            return new ZagatKraken(customer, new JsoupBaseCrwaler())
        } else if (NetworkConst.QYPE.getName().equals(network)) {
            return new QypeKraken(customer, new QypeConnection())
        } else if (NetworkConst.FOURSQUARE.getName().equals(network)) {
            return new FoursquareKraken(customer, new FoursquareConnection())
        }

        throw new IllegalArgumentException("The Network: " + network
        + " is not implemented. Data loading is not possible.")
    }
}
