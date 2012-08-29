package org.social.grails.network


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.grails.network.Network;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Network)
class NetworkTests {

    void testConstructor() {

        def type = new Network(id: "TWITTER")
        assertTrue 'Validation should have passed', type.validate()
        assertEquals 'TWITTER', type.id
    }
}