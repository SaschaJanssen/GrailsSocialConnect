package org.social.core.classification



import org.junit.*
import grails.test.mixin.*

@TestFor(ClassificationTypeController)
@Mock(ClassificationType)
class ClassificationTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
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
        controller.save()

        assert model.classificationTypeInstance != null
        assert view == '/classificationType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/classificationType/show/1'
        assert controller.flash.message != null
        assert ClassificationType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        populateValidParams(params)
        def classificationType = new ClassificationType(params)

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

        assert classificationType.save() != null

        // test invalid parameters in update
        params.id = classificationType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/classificationType/edit"
        assert model.classificationTypeInstance != null

        classificationType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/classificationType/show/$classificationType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        classificationType.clearErrors()

        populateValidParams(params)
        params.id = classificationType.id
        params.version = -1
        controller.update()

        assert view == "/classificationType/edit"
        assert model.classificationTypeInstance != null
        assert model.classificationTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/classificationType/list'

        response.reset()

        populateValidParams(params)
        def classificationType = new ClassificationType(params)

        assert classificationType.save() != null
        assert ClassificationType.count() == 1

        params.id = classificationType.id

        controller.delete()

        assert ClassificationType.count() == 0
        assert ClassificationType.get(classificationType.id) == null
        assert response.redirectedUrl == '/classificationType/list'
    }
}
