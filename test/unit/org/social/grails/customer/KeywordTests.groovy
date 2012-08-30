package org.social.grails.customer


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.grails.Keyword

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Keyword)
@Mock(Customer)
class KeywordTests {

    void testConstraints() {
        def networkMock = NetworkConst.TWITTER

        def customerMock = new Customer()

        def type = KeywordTypeConst.HASH

        def keyword = new Keyword()
        assertFalse 'Validation should have failed.', keyword.validate()

        keyword = new Keyword(keywordType: type, customer: customerMock, network: networkMock)
        assertFalse 'Validation should have failed.', keyword.validate()

        keyword = new Keyword(keyword: 'FOO', keywordType: type, customer: customerMock, network: networkMock)
        def validate = keyword.validate()
        assertTrue 'Validation should have passed.', validate
    }
}
