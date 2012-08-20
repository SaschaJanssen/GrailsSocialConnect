package org.social.core.user

import org.springframework.dao.DataIntegrityViolationException

class KeywordTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [keywordTypeInstanceList: KeywordType.list(params), keywordTypeInstanceTotal: KeywordType.count()]
    }

    def create() {
        [keywordTypeInstance: new KeywordType(params)]
    }

    def save() {
        def keywordTypeInstance = new KeywordType(params)
        if (!keywordTypeInstance.save(flush: true)) {
            render(view: "create", model: [keywordTypeInstance: keywordTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), keywordTypeInstance.id])
        redirect(action: "show", id: keywordTypeInstance.id)
    }

    def show(Long id) {
        def keywordTypeInstance = KeywordType.get(id)
        if (!keywordTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "list")
            return
        }

        [keywordTypeInstance: keywordTypeInstance]
    }

    def edit(Long id) {
        def keywordTypeInstance = KeywordType.get(id)
        if (!keywordTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "list")
            return
        }

        [keywordTypeInstance: keywordTypeInstance]
    }

    def update(Long id, Long version) {
        def keywordTypeInstance = KeywordType.get(id)
        if (!keywordTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (keywordTypeInstance.version > version) {
                keywordTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'keywordType.label', default: 'KeywordType')] as Object[],
                          "Another user has updated this KeywordType while you were editing")
                render(view: "edit", model: [keywordTypeInstance: keywordTypeInstance])
                return
            }
        }

        keywordTypeInstance.properties = params

        if (!keywordTypeInstance.save(flush: true)) {
            render(view: "edit", model: [keywordTypeInstance: keywordTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), keywordTypeInstance.id])
        redirect(action: "show", id: keywordTypeInstance.id)
    }

    def delete(Long id) {
        def keywordTypeInstance = KeywordType.get(id)
        if (!keywordTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "list")
            return
        }

        try {
            keywordTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'keywordType.label', default: 'KeywordType'), id])
            redirect(action: "show", id: id)
        }
    }
}
