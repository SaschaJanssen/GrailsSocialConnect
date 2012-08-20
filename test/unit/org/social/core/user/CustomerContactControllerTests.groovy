package org.social.core.user



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerContactController)
@Mock(CustomerContact)
class CustomerContactControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerContact/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerContactInstanceList.size() == 0
        assert model.customerContactInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerContactInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerContactInstance != null
        assert view == '/customerContact/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerContact/show/1'
        assert controller.flash.message != null
        assert CustomerContact.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerContact/list'

        populateValidParams(params)
        def customerContact = new CustomerContact(params)

        assert customerContact.save() != null

        params.id = customerContact.id

        def model = controller.show()

        assert model.customerContactInstance == customerContact
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerContact/list'

        populateValidParams(params)
        def customerContact = new CustomerContact(params)

        assert customerContact.save() != null

        params.id = customerContact.id

        def model = controller.edit()

        assert model.customerContactInstance == customerContact
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerContact/list'

        response.reset()

        populateValidParams(params)
        def customerContact = new CustomerContact(params)

        assert customerContact.save() != null

        // test invalid parameters in update
        params.id = customerContact.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerContact/edit"
        assert model.customerContactInstance != null

        customerContact.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerContact/show/$customerContact.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerContact.clearErrors()

        populateValidParams(params)
        params.id = customerContact.id
        params.version = -1
        controller.update()

        assert view == "/customerContact/edit"
        assert model.customerContactInstance != null
        assert model.customerContactInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerContact/list'

        response.reset()

        populateValidParams(params)
        def customerContact = new CustomerContact(params)

        assert customerContact.save() != null
        assert CustomerContact.count() == 1

        params.id = customerContact.id

        controller.delete()

        assert CustomerContact.count() == 0
        assert CustomerContact.get(customerContact.id) == null
        assert response.redirectedUrl == '/customerContact/list'
    }
}
