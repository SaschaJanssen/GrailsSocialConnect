package org.social.grails.classification



import grails.test.mixin.*

import org.junit.*
import org.social.grails.classification.Classification
import org.social.grails.classification.ClassificationController
import org.social.grails.classification.ClassificationType

@TestFor(ClassificationController)
@Mock([Classification, ClassificationType])
class ClassificationControllerTests {

    def populateValidParams(params) {
        def type = new org.social.grails.classification.ClassificationType()
        type.id = 'SENTIMENT'
        type.save()

        params.classificationType = type
        params.id = 'POSITIVE'

        assert params != null
    }

    void testIndex() {
        controller.index()
        assert "/classification/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.classificationInstanceList.size() == 0
        assert model.classificationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.classificationInstance != null
    }

    void testSave() {
        params.id = ''
        controller.save()

        assert model.classificationInstance != null
        assert view == '/classification/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/classification/show/POSITIVE'
        assert controller.flash.message != null
        assert Classification.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/classification/list'

        populateValidParams(params)
        def classification = new Classification(params)
        classification.id = params.id

        assert classification.save() != null

        params.id = classification.id

        def model = controller.show()

        assert model.classificationInstance == classification
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/classification/list'

        populateValidParams(params)
        def classification = new Classification(params)
        classification.id  = params.id

        assert classification.save() != null

        params.id = classification.id

        def model = controller.edit()

        assert model.classificationInstance == classification
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/classification/list'

        response.reset()

        populateValidParams(params)
        def classification = new Classification(params)
        classification.id  = params.id

        assert classification.save() != null

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/classification/show/$classification.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/classification/list'

        response.reset()

        populateValidParams(params)
        def classification = new Classification(params)
        classification.id  = params.id

        assert classification.save() != null
        assert Classification.count() == 1

        params.id = classification.id

        controller.delete()

        assert Classification.count() == 0
        assert Classification.get(classification.id) == null
        assert response.redirectedUrl == '/classification/list'
    }
}
