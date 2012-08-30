package org.social.grails.customer


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

    def getCustomerId() {
        return this.id
    }

    static constraints = { lastNetworkAccess (nullable: true) }
}
