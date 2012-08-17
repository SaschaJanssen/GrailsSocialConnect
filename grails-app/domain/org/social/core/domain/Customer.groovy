package org.social.core.domain

class Customer {

    Date lastNetworkAccess
    Date dateCreated
    Date lastUpdated

    static constraints = {
        lastNetworkAccess (nullable: true)
        dateCreated ()
        lastUpdated ()
    }

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }
}
