package org.social.core.network

import org.social.core.classification.Classification

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

    static belongsTo = [network:Network, customer:org.social.core.user.Customer, reliability:org.social.core.classification.Classification, sentiment:org.social.core.classification.Classification]

    def beforeValidate() {
        if (this.sentiment == null) {
            def classificationId = org.social.core.constants.Classification.NOT_CLASSIFIED.getName()
            setSentiment(Classification.get(classificationId))
        }
    }

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    def setReliability(Classification reliability) {
        this.reliability = reliability
    }

    def setReliability(String reliability) {
        this.reliability = Classification.get(reliability)
    }

    def setSentiment(Classification sentiment) {
        this.sentiment = sentiment
    }

    def setSentiment(String sentiment) {
        this.sentiment = Classification.get(sentiment)
    }

    static constraints = {
        networkUserId (nullable: true, size: 0..128)
        networkUserName (nullable: true, size: 0..80)
        language (nullable: true, size: 0..5)
        geoLocation (nullable: true, size: 0..20)
        networkUserRating (nullable: true, size: 0..10)
    }

    static mapping = { message (type: 'text') }
}
