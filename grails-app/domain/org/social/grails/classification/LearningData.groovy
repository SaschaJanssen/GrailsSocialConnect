package org.social.grails.classification

import org.social.core.constants.ClassificationConst

class LearningData {

    String learningData
    ClassificationConst.Sentiment classification

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

    static mapping = { learningData (type: 'text') }
}
