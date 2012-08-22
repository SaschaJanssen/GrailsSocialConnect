package org.social.core.user


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Customer)
class CustomerTests {
    void testConstraint() {
        def customer = new Customer()
        assertTrue ('This test should have passed', customer.validate())
    }
}
