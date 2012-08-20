package org.social.core.user

class Contact {

    Date dateCreated
    Date lastUpdated

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static constraints = {
    }
}
