package org.social.grails.network

import org.social.core.constants.ClassificationConst
import org.social.grails.user.Customer

class Message {

    String message
    String networkUserId
    String networkUserName
    String language
    String geoLocation
    String networkUserRating

    Date networkMessageDate
    Date messageReceivedDate

    Date dateCreated
    Date lastUpdated

    org.social.core.constants.ClassificationConst.Reliability reliability
    org.social.core.constants.ClassificationConst.Sentiment sentiment

    static belongsTo = [network:Network, customer:org.social.grails.user.Customer]

    def beforeValidate() {
        if (this.sentiment == null) {
            sentiment = ClassificationConst.Sentiment.NOT_CLASSIFIED
        }
    }

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    def setCustomer(Customer customer) {
        this.customer = customer
    }

    def setNetwork(Network network) {
        this.network = network
    }

    static constraints = {
        networkUserId (nullable: true, size: 0..128)
        networkUserName (nullable: true, size: 0..80)
        language (nullable: true, size: 0..5)
        geoLocation (nullable: true, size: 0..20)
        networkUserRating (nullable: true, size: 0..10)
    }
}