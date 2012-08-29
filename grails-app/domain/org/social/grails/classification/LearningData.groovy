package org.social.grails.classification

class LearningData {

    String learningData
    Date dateCreated
    Date lastUpdated

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static belongsTo = [classification:Classification]

    static constraints = {
    }

    static mapping = {
        learningData (type: 'text')
    }
}
