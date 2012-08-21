package org.social.core.network

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

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
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
