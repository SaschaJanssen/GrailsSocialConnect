package org.social.core.user



import grails.test.mixin.*

import org.junit.*

@TestFor(KeywordTypeController)
@Mock(KeywordType)
class KeywordTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
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

        // save
        params.id = ''
        controller.save()
        controller.create()

        assert view == '/keywordType/create'
        assert model.keywordTypeInstance != null

        response.reset()

        params.id = '1'
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

        def type = new KeywordType()
        type.id = 'HASH'
        type.save()

        def model = controller.show('HASH')
        assertEquals type, model.keywordTypeInstance
    }

    void testEdit() {
        controller.edit()
        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        def keywordType = new KeywordType()
        keywordType.id = 'HASH'
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

        def keywordType = new KeywordType(params)
        keywordType.id = 'HASH'
        assert keywordType.save() != null

        params.id = 'HASH'
        params.description = 'FOOBAA'

        controller.update()
        assert response.redirectedUrl == "/keywordType/show/$keywordType.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/keywordType/list'

        response.reset()
        def keywordType = new KeywordType(params)
        keywordType.id = 'HASH'
        assert keywordType.save() != null
        assert KeywordType.count() == 1

        params.id = keywordType.id
        controller.delete()
        assert KeywordType.count() == 0
        assert KeywordType.get(keywordType.id) == null
        assert response.redirectedUrl == '/keywordType/list'
    }
}
