package org.social.grails.classification



import grails.test.mixin.*

import org.junit.*
import org.social.grails.classification.ClassificationType;
import org.social.grails.classification.ClassificationTypeController;

@TestFor(ClassificationTypeController)
@Mock(ClassificationType)
class ClassificationTypeControllerTests {

    def populateValidParams(params) {
        params.id = 'TYPE'

        assert params != null
    }

    void testIndex() {
        controller.index()
        assert "/classificationType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.classificationTypeInstanceList.size() == 0
        assert model.classificationTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.classificationTypeInstance != null
    }

    void testSave() {
        params.id = ''
        controller.save()

        assert model.classificationTypeInstance != null
        assert view == '/classificationType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/classificationType/show/TYPE'
        assert controller.flash.message != null
        assert ClassificationType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        populateValidParams(params)
        def classificationType = new ClassificationType(params)
        classificationType.id = params.id
        assert classificationType.save() != null

        params.id = classificationType.id

        def model = controller.show()

        assert model.classificationTypeInstance == classificationType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        populateValidParams(params)
        def classificationType = new ClassificationType(params)
        classificationType.id = params.id

        assert classificationType.save() != null

        params.id = classificationType.id

        def model = controller.edit()

        assert model.classificationTypeInstance == classificationType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        response.reset()

        populateValidParams(params)
        def classificationType = new ClassificationType(params)
        classificationType.id = params.id

        assert classificationType.save() != null

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/classificationType/show/$classificationType.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        response.reset()

        populateValidParams(params)
        def classificationType = new ClassificationType(params)
        classificationType.id = params.id

        assert classificationType.save() != null
        assert ClassificationType.count() == 1

        params.id = classificationType.id

        controller.delete()

        assert ClassificationType.count() == 0
        assert ClassificationType.get(classificationType.id) == null
        assert response.redirectedUrl == '/classificationType/list'
    }
}
