package org.social.core.classification



import org.junit.*
import grails.test.mixin.*

@TestFor(LearningDataController)
@Mock(LearningData)
class LearningDataControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/learningData/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.learningDataInstanceList.size() == 0
        assert model.learningDataInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.learningDataInstance != null
    }

    void testSave() {
        controller.save()

        assert model.learningDataInstance != null
        assert view == '/learningData/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/learningData/show/1'
        assert controller.flash.message != null
        assert LearningData.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/learningData/list'

        populateValidParams(params)
        def learningData = new LearningData(params)

        assert learningData.save() != null

        params.id = learningData.id

        def model = controller.show()

        assert model.learningDataInstance == learningData
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/learningData/list'

        populateValidParams(params)
        def learningData = new LearningData(params)

        assert learningData.save() != null

        params.id = learningData.id

        def model = controller.edit()

        assert model.learningDataInstance == learningData
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/learningData/list'

        response.reset()

        populateValidParams(params)
        def learningData = new LearningData(params)

        assert learningData.save() != null

        // test invalid parameters in update
        params.id = learningData.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/learningData/edit"
        assert model.learningDataInstance != null

        learningData.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/learningData/show/$learningData.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        learningData.clearErrors()

        populateValidParams(params)
        params.id = learningData.id
        params.version = -1
        controller.update()

        assert view == "/learningData/edit"
        assert model.learningDataInstance != null
        assert model.learningDataInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/learningData/list'

        response.reset()

        populateValidParams(params)
        def learningData = new LearningData(params)

        assert learningData.save() != null
        assert LearningData.count() == 1

        params.id = learningData.id

        controller.delete()

        assert LearningData.count() == 0
        assert LearningData.get(learningData.id) == null
        assert response.redirectedUrl == '/learningData/list'
    }
}
