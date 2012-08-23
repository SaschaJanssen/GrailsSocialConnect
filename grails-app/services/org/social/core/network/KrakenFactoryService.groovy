package org.social.core.network

import org.social.core.constants.Networks
import org.social.core.network.connection.FacebookConnection
import org.social.core.network.connection.FoursquareConnection
import org.social.core.network.connection.QypeConnection
import org.social.core.network.connection.TwitterConnection
import org.social.core.network.crawler.JsoupBaseCrwaler
import org.social.core.user.Customer

class KrakenFactoryService {

    def serviceMethod() {
    }

    public static SocialNetworkKraken getInstance(String network, Customer customer)
    throws IllegalArgumentException {

        if (Networks.FACEBOOK.getName().equals(network)) {
            return new FacebookKraken(customer, new FacebookConnection())
        } else if (Networks.TWITTER.getName().equals(network)) {
            return new TwitterKraken(customer, new TwitterConnection())
        } else if (Networks.YELP.getName().equals(network)) {
            return new YelpKraken(customer, new JsoupBaseCrwaler())
        } else if (Networks.OPENTABLE.getName().equals(network)) {
            return new OpenTableKraken(customer, new JsoupBaseCrwaler())
        } else if (Networks.TRIPADVISOR.getName().equals(network)) {
            return new TripAdvisorKraken(customer, new JsoupBaseCrwaler())
        } else if (Networks.ZAGAT.getName().equals(network)) {
            return new ZagatKraken(customer, new JsoupBaseCrwaler())
        } else if (Networks.QYPE.getName().equals(network)) {
            return new QypeKraken(customer, new QypeConnection())
        } else if (Networks.FOURSQUARE.getName().equals(network)) {
            return new FoursquareKraken(customer, new FoursquareConnection())
        }

        throw new IllegalArgumentException("The Network: " + network
        + " is not implemented. Data loading is not possible.")
    }
}
