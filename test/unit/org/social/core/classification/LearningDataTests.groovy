package org.social.core.classification



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LearningData)
class LearningDataTests {

    void testConstraintsFail() {

       def learningData = new LearningData()
       assertFalse 'Validation should have failed.', learningData.validate()
       assertFalse 'Validation should have failed.', learningData.validate()

       def classification = new Classification(id: 'reliable')
       learningData = new LearningData(classification: classification)
       assertFalse 'Validation should have failed.', learningData.validate()
    }

    void testConstraintsPass() {
        def classification = new Classification(id: 'reliable')

        def learningData = new LearningData(classification: classification, learningData: 'FOO BAA')
        assertTrue 'Validation should have passed.', learningData.validate()
    }
}
