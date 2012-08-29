package org.social.grails.user


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.grails.user.ContactType;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ContactType)
class ContactTypeTests {

    void testConstraint() {
        def contactType = new ContactType()
        contactType.id = 'PHONE'
        assertTrue ('This test should have passed', contactType.validate())
    }
}
