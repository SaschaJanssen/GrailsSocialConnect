package org.social.grails.classification


import static junit.framework.Assert.*
import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.ClassificationConst

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LearningData)
class LearningDataTests {

    void testConstraintsFail() {

        def learningData = new LearningData()
        assertFalse 'Validation should have failed.', learningData.validate()

        def classification = ClassificationConst.Sentiment.POSITIVE
        learningData = new LearningData(classification: classification)
        assertFalse 'Validation should have failed.', learningData.validate()
    }

    void testConstraintsPass() {
        def classification = ClassificationConst.Sentiment.POSITIVE

        def learningData = new LearningData(classification: classification, learningData: 'FOO BAA')
        assertTrue 'Validation should have passed.', learningData.validate()
    }
}
