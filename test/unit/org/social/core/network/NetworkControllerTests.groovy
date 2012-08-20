package org.social.core.network



import org.junit.*
import grails.test.mixin.*

@TestFor(NetworkController)
@Mock(Network)
class NetworkControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/network/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.networkInstanceList.size() == 0
        assert model.networkInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.networkInstance != null
    }

    void testSave() {
        controller.save()

        assert model.networkInstance != null
        assert view == '/network/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/network/show/1'
        assert controller.flash.message != null
        assert Network.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        populateValidParams(params)
        def network = new Network(params)

        assert network.save() != null

        params.id = network.id

        def model = controller.show()

        assert model.networkInstance == network
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        populateValidParams(params)
        def network = new Network(params)

        assert network.save() != null

        params.id = network.id

        def model = controller.edit()

        assert model.networkInstance == network
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        response.reset()

        populateValidParams(params)
        def network = new Network(params)

        assert network.save() != null

        // test invalid parameters in update
        params.id = network.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/network/edit"
        assert model.networkInstance != null

        network.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/network/show/$network.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        network.clearErrors()

        populateValidParams(params)
        params.id = network.id
        params.version = -1
        controller.update()

        assert view == "/network/edit"
        assert model.networkInstance != null
        assert model.networkInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        response.reset()

        populateValidParams(params)
        def network = new Network(params)

        assert network.save() != null
        assert Network.count() == 1

        params.id = network.id

        controller.delete()

        assert Network.count() == 0
        assert Network.get(network.id) == null
        assert response.redirectedUrl == '/network/list'
    }
}
