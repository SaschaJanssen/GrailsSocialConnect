package org.social.grails

import org.springframework.dao.DataIntegrityViolationException

class LearningDataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [learningDataInstanceList: LearningData.list(params), learningDataInstanceTotal: LearningData.count()]
    }

    def create() {
        [learningDataInstance: new LearningData(params)]
    }

    def save() {
        def learningDataInstance = new LearningData(params)
        if (!learningDataInstance.save(flush: true)) {
            render(view: "create", model: [learningDataInstance: learningDataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'learningData.label', default: 'LearningData'), learningDataInstance.id])
        redirect(action: "show", id: learningDataInstance.id)
    }

    def show(Long id) {
        def learningDataInstance = LearningData.get(id)
        if (!learningDataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "list")
            return
        }

        [learningDataInstance: learningDataInstance]
    }

    def edit(Long id) {
        def learningDataInstance = LearningData.get(id)
        if (!learningDataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "list")
            return
        }

        [learningDataInstance: learningDataInstance]
    }

    def update(Long id, Long version) {
        def learningDataInstance = LearningData.get(id)
        if (!learningDataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (learningDataInstance.version > version) {
                learningDataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'learningData.label', default: 'LearningData')] as Object[],
                          "Another user has updated this LearningData while you were editing")
                render(view: "edit", model: [learningDataInstance: learningDataInstance])
                return
            }
        }

        learningDataInstance.properties = params

        if (!learningDataInstance.save(flush: true)) {
            render(view: "edit", model: [learningDataInstance: learningDataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'learningData.label', default: 'LearningData'), learningDataInstance.id])
        redirect(action: "show", id: learningDataInstance.id)
    }

    def delete(Long id) {
        def learningDataInstance = LearningData.get(id)
        if (!learningDataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "list")
            return
        }

        try {
            learningDataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'learningData.label', default: 'LearningData'), id])
            redirect(action: "show", id: id)
        }
    }
}
