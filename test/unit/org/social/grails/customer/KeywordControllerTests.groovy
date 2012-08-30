package org.social.grails.customer



import grails.test.mixin.*

import org.junit.*
import org.social.core.constants.KeywordTypeConst
import org.social.core.constants.NetworkConst
import org.social.grails.Keyword

@TestFor(KeywordController)
@Mock([Keyword, Customer])
class KeywordControllerTests {

    def populateValidParams(params) {
        def customer = new Customer()
        customer.save()
        params.customer = customer
        params.keywordType = KeywordTypeConst.HASH
        params.network = NetworkConst.TWITTER
        params.keyword = 'KEYWORD'

        assert params != null
    }

    void testIndex() {
        controller.index()
        assert "/keyword/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.keywordInstanceList.size() == 0
        assert model.keywordInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.keywordInstance != null
    }

    void testSave() {
        controller.save()

        assert model.keywordInstance != null
        assert view == '/keyword/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/keyword/show/1'
        assert controller.flash.message != null
        assert Keyword.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/keyword/list'

        populateValidParams(params)
        def keyword = new Keyword(params)

        assert keyword.save() != null

        params.id = keyword.id

        def model = controller.show()

        assert model.keywordInstance == keyword
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/keyword/list'

        populateValidParams(params)
        def keyword = new Keyword(params)

        assert keyword.save() != null

        params.id = keyword.id

        def model = controller.edit()

        assert model.keywordInstance == keyword
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/keyword/list'

        response.reset()

        populateValidParams(params)
        def keyword = new Keyword(params)

        assert keyword.save() != null

        // test invalid parameters in update
        params.id = keyword.id
        params.keyword = null

        controller.update()

        assert view == "/keyword/edit"
        assert model.keywordInstance != null

        keyword.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/keyword/show/$keyword.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/keyword/list'

        response.reset()

        populateValidParams(params)
        def keyword = new Keyword(params)

        assert keyword.save() != null
        assert Keyword.count() == 1

        params.id = keyword.id

        controller.delete()

        assert Keyword.count() == 0
        assert Keyword.get(keyword.id) == null
        assert response.redirectedUrl == '/keyword/list'
    }
}
