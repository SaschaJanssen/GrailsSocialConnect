package org.social.grails.network



import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.ClassificationConst
import org.social.core.constants.NetworkConst
import org.social.grails.user.Customer

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Message)
@Mock(Customer)
class MessageTests {

    void testConstructorFail() {

        def networkMock = NetworkConst.TWITTER

        def customerMock = new Customer()

        def reliabilityMock = ClassificationConst.Reliability.RELIABLE

        def sentimentMock = ClassificationConst.Sentiment.NEGATIVE

        def message = new Message()
        assertFalse message.validate()

        message = new Message(network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock)
        assertFalse message.validate()
    }

    void testConstructorPass() {
        def networkMock = NetworkConst.TWITTER

        def customerMock = new Customer()

        def reliabilityMock = ClassificationConst.Reliability.RELIABLE

        def sentimentMock = ClassificationConst.Sentiment.POSITIVE

        def message = new Message(message: 'FOO BAA', network: networkMock, customer: customerMock, reliability: reliabilityMock, sentiment: sentimentMock, messageReceivedDate: new Date(), networkMessageDate: new Date())

        assertTrue 'Validation should have passed', message.validate()
        assertEquals 'FOO BAA', message.message
    }
}
