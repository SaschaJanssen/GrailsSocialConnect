package org.social.core.user


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Keyword)
class KeywordTests {

    void testConstraintsFail() {
       def networkMock = []
       mockDomain(org.social.core.network.Network, networkMock)

       def customerMock = []
       mockDomain(Customer, customerMock)

       def typeMock = []
       mockDomain(KeywordType, typeMock)

       def keyword = new Keyword()
       assertFalse 'Validation should have failed.', keyword.validate()

       keyword = new Keyword(keywordType: typeMock, customer: customerMock, network: networkMock)
       assertFalse 'Validation should have failed.', keyword.validate()
   }


}
