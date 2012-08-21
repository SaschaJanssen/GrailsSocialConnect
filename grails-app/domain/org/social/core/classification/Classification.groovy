package org.social.core.classification

class Classification {

    String id
    String description
    Date dateCreated
    Date lastUpdated

    def beforeInsert() {
        dateCreated = new Date()
    }
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static belongsTo = [classificationType:ClassificationType]

    static constraints = {
        id (size:0..20, unique: true, blank: false)
        description (nullable: true)
    }

    static mapping = {
        id (generator : 'assigned', column: 'id', type: 'string')
        version (false)
    }
}
