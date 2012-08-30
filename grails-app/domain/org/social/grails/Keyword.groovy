package org.social.grails

import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.grails.customer.Customer;


class Keyword {

    String keyword
    KeywordTypeConst keywordType
    NetworkConst network
    
    Date dateCreated
    Date lastUpdated
    
    static belongsTo = [customer:Customer]

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
