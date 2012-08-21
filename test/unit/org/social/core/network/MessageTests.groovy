package org.social.core.network



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Message)
class MessageTests {

    void testConstructorFail() {

       def networkMock = []
       mockDomain(Network, networkMock)

       def customerMock = []
       mockDomain(org.social.core.user.Customer, customerMock)

       def reliabilityMock = []
       mockDomain(org.social.core.classification.Classification, reliabilityMock)

       def sentimentMock = []
       mockDomain(org.social.core.classification.Classification, sentimentMock)


       def message = new Message()
       assertFalse 'Validation should have failed', message.validate()

       message = new Message(network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock)
       assertFalse 'Validation should have failed', message.validate()
    }

    void testConstructorPass() {

        def networkMock = []
        mockDomain(Network, networkMock)

        def customerMock = []
        mockDomain(org.social.core.user.Customer, customerMock)

        def reliabilityMock = []
        mockDomain(org.social.core.classification.Classification, reliabilityMock)

        def sentimentMock = []
        mockDomain(org.social.core.classification.Classification, sentimentMock)


        def message = new Message(message: 'FOO BAA', network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock)
        assertFalse 'Validation should have failed', message.validate()
        assertEquals 'FOO BAA', message.message
    }
}
