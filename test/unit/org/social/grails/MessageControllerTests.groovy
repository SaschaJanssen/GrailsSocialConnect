package org.social.grails



import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.ClassificationConst
import org.social.core.constants.NetworkConst
import org.social.grails.customer.Customer

@TestFor(MessageController)
@Mock([Message, Customer])
class MessageControllerTests {

    def populateValidParams(params) {
        def customer = new Customer()
        customer.save()

        def reliability = ClassificationConst.Reliability.RELIABLE

        def sentiment = ClassificationConst.Sentiment.POSITIVE

        params.network = NetworkConst.TWITTER
        params.customer = customer
        params.reliability = reliability
        params.sentiment = sentiment
        params.message = 'MESSAGE'
        params.messageReceivedDate = new Date()
        params.networkMessageDate = new Date()

        assert params != null
    }

    void testIndex() {
        controller.index()
        assert "/message/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.messageInstanceList.size() == 0
        assert model.messageInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.messageInstance != null
    }

    void testSave() {
        controller.save()

        assert model.messageInstance != null
        assert view == '/message/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/message/show/1'
        assert controller.flash.message != null
        assert Message.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/message/list'

        populateValidParams(params)
        def message = new Message(params)

        assert message.save() != null

        params.id = message.id

        def model = controller.show()

        assert model.messageInstance == message
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/message/list'

        populateValidParams(params)
        def message = new Message(params)

        assert message.save() != null

        params.id = message.id

        def model = controller.edit()

        assert model.messageInstance == message
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/message/list'

        response.reset()

        populateValidParams(params)
        def message = new Message(params)

        assert message.save() != null

        // test invalid parameters in update
        params.id = message.id
        params.message = null

        controller.update()

        assert view == "/message/edit"
        assert model.messageInstance != null

        message.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/message/show/$message.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        message.clearErrors()

        populateValidParams(params)
        params.id = message.id
        params.version = -1
        controller.update()

        assert view == "/message/edit"
        assert model.messageInstance != null
        assert model.messageInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/message/list'

        response.reset()

        populateValidParams(params)
        def message = new Message(params)

        assert message.save() != null
        assert Message.count() == 1

        params.id = message.id

        controller.delete()

        assert Message.count() == 0
        assert Message.get(message.id) == null
        assert response.redirectedUrl == '/message/list'
    }
}