package org.social.grails.user


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.grails.user.Contact;
import org.social.grails.user.ContactType;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Contact)
class ContactTests {

    void testConstraints() {
        def contactType = new ContactType()
        contactType.id = 'PHONE'

        def contact = new Contact()
        contact.contactType = contactType

        assertFalse ('This test should have failed', contact.validate())


        contact.infoString = '123456'

        assertTrue ('This test should have passed', contact.validate())
    }
}
