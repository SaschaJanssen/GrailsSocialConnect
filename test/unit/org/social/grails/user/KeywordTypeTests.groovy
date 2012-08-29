package org.social.grails.user

import org.social.grails.user.KeywordType;

import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(KeywordType)
class KeywordTypeTests {

    void testConstraint() {
        def type = new KeywordType(id: 'HASH')
        assert type.validate()
        assert 'HASH' == type.id
    }
}
