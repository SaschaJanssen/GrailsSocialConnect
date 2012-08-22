package org.social.core.classification



import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ClassificationType)
class ClassificationTypeTests {

    void testConstructor() {
        mockDomain(ClassificationType)
        def type = new ClassificationType(id: "TYPE")
        assertTrue 'Validation should have passed', type.validate()
        assertEquals 'TYPE', type.id
    }
}
