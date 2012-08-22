package org.social.core.user


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Keyword)
class KeywordTests {

    void testConstraints() {
        def networkMock = new org.social.core.network.Network()

        def customerMock = new Customer()

        def typeMock = new KeywordType()

        def keyword = new Keyword()
        assertFalse 'Validation should have failed.', keyword.validate()

        keyword = new Keyword(keywordType: typeMock, customer: customerMock, network: networkMock)
        assertFalse 'Validation should have failed.', keyword.validate()

        keyword = new Keyword(keyword: 'FOO', keywordType: typeMock, customer: customerMock, network: networkMock)
        assertTrue 'Validation should have passed.', keyword.validate()
    }
}
