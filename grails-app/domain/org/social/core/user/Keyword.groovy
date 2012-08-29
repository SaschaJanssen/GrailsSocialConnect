package org.social.core.user

class Keyword {

    String keyword
    Date dateCreated
    Date lastUpdated

    static belongsTo = [keywordType:KeywordType, customer:Customer, network:org.social.core.network.Network]
    
    def String getKeywordTypeId() {
        return this.keywordType.id
    }

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static constraints = {
        keyword (unique: ['keywordType', 'customer', 'network'])
    }

    static mapping = {
        version (false)
    }
}
