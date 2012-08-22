package org.social.core.user

import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(KeywordType)
class KeywordTypeTests {

    void testConstraint() {
        def type = new KeywordType(id: 'HASH')
        assertTrue 'Validation should have passed', type.validate()
        assertEquals 'HASH', type.id
    }
}
