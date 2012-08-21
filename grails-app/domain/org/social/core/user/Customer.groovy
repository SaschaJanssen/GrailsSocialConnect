package org.social.core.user

class Customer {

    Date lastNetworkAccess
    Date dateCreated
    Date lastUpdated

    static hasMany = [contact:Contact]

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static constraints = {
        lastNetworkAccess (nullable: true)
    }
}
