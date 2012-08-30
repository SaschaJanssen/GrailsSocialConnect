package org.social.core.constants;

public enum NetworkConst {
    FACEBOOK, TWITTER, YELP, QYPE, OPENTABLE, TRIPADVISOR, ZAGAT, FOURSQUARE;

    public String getName() {
        return name();
    }

    public boolean isSameAs(NetworkConst network) {
        return this.equals(network);
    }
}
