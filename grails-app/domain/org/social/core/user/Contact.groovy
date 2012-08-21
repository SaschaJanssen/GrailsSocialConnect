package org.social.core.user

class Contact {

    String infoString
    Date dateCreated
    Date lastUpdated

    static belongsTo = [contactType:ContactType]

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static constraints = {
    }
}
