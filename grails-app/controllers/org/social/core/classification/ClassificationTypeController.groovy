package org.social.core.classification

import org.springframework.dao.DataIntegrityViolationException

class ClassificationTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [classificationTypeInstanceList: ClassificationType.list(params), classificationTypeInstanceTotal: ClassificationType.count()]
    }

    def create() {
        [classificationTypeInstance: new ClassificationType(params)]
    }

    def save() {
        def classificationTypeInstance = new ClassificationType(params)
        if (!classificationTypeInstance.save(flush: true)) {
            render(view: "create", model: [classificationTypeInstance: classificationTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), classificationTypeInstance.id])
        redirect(action: "show", id: classificationTypeInstance.id)
    }

    def show(Long id) {
        def classificationTypeInstance = ClassificationType.get(id)
        if (!classificationTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "list")
            return
        }

        [classificationTypeInstance: classificationTypeInstance]
    }

    def edit(Long id) {
        def classificationTypeInstance = ClassificationType.get(id)
        if (!classificationTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "list")
            return
        }

        [classificationTypeInstance: classificationTypeInstance]
    }

    def update(Long id, Long version) {
        def classificationTypeInstance = ClassificationType.get(id)
        if (!classificationTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (classificationTypeInstance.version > version) {
                classificationTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'classificationType.label', default: 'ClassificationType')] as Object[],
                          "Another user has updated this ClassificationType while you were editing")
                render(view: "edit", model: [classificationTypeInstance: classificationTypeInstance])
                return
            }
        }

        classificationTypeInstance.properties = params

        if (!classificationTypeInstance.save(flush: true)) {
            render(view: "edit", model: [classificationTypeInstance: classificationTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), classificationTypeInstance.id])
        redirect(action: "show", id: classificationTypeInstance.id)
    }

    def delete(Long id) {
        def classificationTypeInstance = ClassificationType.get(id)
        if (!classificationTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "list")
            return
        }

        try {
            classificationTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'classificationType.label', default: 'ClassificationType'), id])
            redirect(action: "show", id: id)
        }
    }
}
