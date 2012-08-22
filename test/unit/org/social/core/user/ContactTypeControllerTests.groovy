package org.social.core.user



import grails.test.mixin.*

import org.junit.*

@TestFor(ContactTypeController)
@Mock(ContactType)
class ContactTypeControllerTests {

    def populateValidParams(params) {
        params.id = 'PHONE'
        assert params != null
    }

    void testIndex() {
        controller.index()
        assert "/contactType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.contactTypeInstanceList.size() == 0
        assert model.contactTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.contactTypeInstance != null
    }

    void testSave() {
        params.id = ''
        controller.save()

        assert model.contactTypeInstance != null
        assert view == '/contactType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/contactType/show/PHONE'
        assert controller.flash.message != null
        assert ContactType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/contactType/list'

        populateValidParams(params)
        def contactType = new ContactType(params)
        contactType.id = params.id
        assert contactType.save() != null

        params.id = contactType.id

        def model = controller.show()

        assert model.contactTypeInstance == contactType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/contactType/list'

        populateValidParams(params)
        def contactType = new ContactType(params)
        contactType.id = params.id
        assert contactType.save() != null

        params.id = contactType.id

        def model = controller.edit()

        assert model.contactTypeInstance == contactType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/contactType/list'

        response.reset()

        populateValidParams(params)
        def contactType = new ContactType(params)
        contactType.id = params.id
        assert contactType.save() != null

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/contactType/show/$contactType.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/contactType/list'

        response.reset()

        populateValidParams(params)
        def contactType = new ContactType(params)
        contactType.id = params.id

        assert contactType.save() != null
        assert ContactType.count() == 1

        params.id = contactType.id

        controller.delete()

        assert ContactType.count() == 0
        assert ContactType.get(contactType.id) == null
        assert response.redirectedUrl == '/contactType/list'
    }
}
