package org.social.core.user

import org.social.core.network.Network

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
    
    public static List<Keyword> findAllByCustomerAndNetwork(Customer customer, String networkName) {
        Network network = new Network();
        network.id = networkName
        
        return Keyword.findAllByCustomerAndNetwork(customer, network)
    }

    static constraints = {
        keyword (unique: ['keywordType', 'customer', 'network'])
    }

    static mapping = {
        version (false)
    }
}
