package org.social.core.classification

import org.springframework.dao.DataIntegrityViolationException

class ClassificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [classificationInstanceList: Classification.list(params), classificationInstanceTotal: Classification.count()]
    }

    def create() {
        [classificationInstance: new Classification(params)]
    }

    def save() {
        def classificationInstance = new Classification(params)
        classificationInstance.id = params.id
        if (!classificationInstance.save(insert: true, flush: true)) {
            render(view: "create", model: [classificationInstance: classificationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'classification.label', default: 'Classification'), classificationInstance.id])
        redirect(action: "show", id: classificationInstance.id)
    }

    def show(String id) {
        def classificationInstance = Classification.get(id)
        if (!classificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "list")
            return
        }

        [classificationInstance: classificationInstance]
    }

    def edit(String id) {
        def classificationInstance = Classification.get(id)
        if (!classificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "list")
            return
        }

        [classificationInstance: classificationInstance]
    }

    def update(String id, String version) {
        def classificationInstance = Classification.get(id)
        if (!classificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "list")
            return
        }

        classificationInstance.properties = params

        if (!classificationInstance.save(insert: true, flush: true)) {
            render(view: "edit", model: [classificationInstance: classificationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'classification.label', default: 'Classification'), classificationInstance.id])
        redirect(action: "show", id: classificationInstance.id)
    }

    def delete(String id) {
        def classificationInstance = Classification.get(id)
        if (!classificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "list")
            return
        }

        try {
            classificationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'classification.label', default: 'Classification'), id])
            redirect(action: "show", id: id)
        }
    }
}
