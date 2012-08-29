package org.social.core.network



import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.core.classification.Classification
import org.social.core.user.Customer

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Message)
@Mock([Network, Classification, Customer])
class MessageTests {

    void testConstructorFail() {

        def networkMock = new Network()

        def customerMock = new Customer()

        def reliabilityMock = new Classification()

        def sentimentMock = new Classification()

        def message = new Message()
        assertFalse message.validate()

        message = new Message(network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock)
        assertFalse message.validate()
    }

    void testConstructorPass() {
        def networkMock = new Network()

        def customerMock = new Customer()

        def reliabilityMock = new Classification()

        def sentimentMock = new Classification()

        def message = new Message(message: 'FOO BAA', network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock, messageReceivedDate: new Date(), networkMessageDate: new Date())

        assertTrue 'Validation should have passed', message.validate()
        assertEquals 'FOO BAA', message.message
    }
}
