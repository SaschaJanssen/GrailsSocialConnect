package org.social.grails.user

import org.social.core.constants.NetworkConst


class Keyword {

    String keyword
    org.social.core.constants.NetworkConst network
    
    Date dateCreated
    Date lastUpdated

    static belongsTo = [keywordType:KeywordType, customer:Customer]
    
    def String getKeywordTypeId() {
        return this.keywordType.id
    }

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }
    
    public static List<Keyword> findAllByCustomerAndNetwork(Customer customer, NetworkConst network) {
        return Keyword.findAllByCustomerAndNetwork(customer, network.name())
    }

    static constraints = {
        keyword (unique: ['keywordType', 'customer', 'network'])
    }

    static mapping = {
        version (false)
    }
}
