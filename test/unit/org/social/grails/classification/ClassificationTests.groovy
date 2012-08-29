package org.social.grails.classification


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.grails.classification.Classification;
import org.social.grails.classification.ClassificationType;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Classification)
class ClassificationTests {

    void testConstructor() {
        mockForConstraintsTests(Classification)

        def type = new Classification(id: "CLASSIFI")
        assertFalse 'Validation should have failed.', type.validate()

        def classType = new ClassificationType(id: "TYPE")
        type = new Classification(id: "CLASSIFI", classificationType: classType)
        assertTrue 'Validation should have passed.', type.validate()

        assertEquals 'CLASSIFI', type.id
    }
}
