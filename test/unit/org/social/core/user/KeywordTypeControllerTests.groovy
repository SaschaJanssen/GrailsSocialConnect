package org.social.core.user



import org.junit.*
import grails.test.mixin.*

@TestFor(KeywordTypeController)
@Mock(KeywordType)
class KeywordTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/keywordType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.keywordTypeInstanceList.size() == 0
        assert model.keywordTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.keywordTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.keywordTypeInstance != null
        assert view == '/keywordType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/keywordType/show/1'
        assert controller.flash.message != null
        assert KeywordType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        populateValidParams(params)
        def keywordType = new KeywordType(params)

        assert keywordType.save() != null

        params.id = keywordType.id

        def model = controller.show()

        assert model.keywordTypeInstance == keywordType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        populateValidParams(params)
        def keywordType = new KeywordType(params)

        assert keywordType.save() != null

        params.id = keywordType.id

        def model = controller.edit()

        assert model.keywordTypeInstance == keywordType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        response.reset()

        populateValidParams(params)
        def keywordType = new KeywordType(params)

        assert keywordType.save() != null

        // test invalid parameters in update
        params.id = keywordType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/keywordType/edit"
        assert model.keywordTypeInstance != null

        keywordType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/keywordType/show/$keywordType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        keywordType.clearErrors()

        populateValidParams(params)
        params.id = keywordType.id
        params.version = -1
        controller.update()

        assert view == "/keywordType/edit"
        assert model.keywordTypeInstance != null
        assert model.keywordTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        response.reset()

        populateValidParams(params)
        def keywordType = new KeywordType(params)

        assert keywordType.save() != null
        assert KeywordType.count() == 1

        params.id = keywordType.id

        controller.delete()

        assert KeywordType.count() == 0
        assert KeywordType.get(keywordType.id) == null
        assert response.redirectedUrl == '/keywordType/list'
    }
}
