package org.social.grails.network



import grails.test.mixin.*

import org.junit.*
import org.social.grails.network.Network;
import org.social.grails.network.NetworkController;

@TestFor(NetworkController)
@Mock(Network)
class NetworkControllerTests {

    def populateValidParams(params) {
        params.id = 'TWITTER'
        assert params != null
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
        params.id = ''
        controller.save()

        assert model.networkInstance != null
        assert view == '/network/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/network/show/TWITTER'
        assert controller.flash.message != null
        assert Network.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        populateValidParams(params)
        def network = new Network(params)
        network.id = params.id
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
        network.id = params.id
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
        network.id = params.id
        assert network.save() != null


        populateValidParams(params)
        params.description = 'FOO BAA'
        controller.update()

        assert response.redirectedUrl == "/network/show/$network.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/network/list'

        response.reset()

        populateValidParams(params)
        def network = new Network(params)
        network.id = params.id
        assert network.save() != null
        assert Network.count() == 1

        params.id = network.id

        controller.delete()

        assert Network.count() == 0
        assert Network.get(network.id) == null
        assert response.redirectedUrl == '/network/list'
    }
}
