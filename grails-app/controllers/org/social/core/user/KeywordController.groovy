package org.social.core.user

import org.springframework.dao.DataIntegrityViolationException

class KeywordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [keywordInstanceList: Keyword.list(params), keywordInstanceTotal: Keyword.count()]
    }

    def create() {
        [keywordInstance: new Keyword(params)]
    }

    def save() {
        def keywordInstance = new Keyword(params)
        if (!keywordInstance.save(flush: true)) {
            render(view: "create", model: [keywordInstance: keywordInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'keyword.label', default: 'Keyword'), keywordInstance.id])
        redirect(action: "show", id: keywordInstance.id)
    }

    def show(Long id) {
        def keywordInstance = Keyword.get(id)
        if (!keywordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "list")
            return
        }

        [keywordInstance: keywordInstance]
    }

    def edit(Long id) {
        def keywordInstance = Keyword.get(id)
        if (!keywordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "list")
            return
        }

        [keywordInstance: keywordInstance]
    }

    def update(Long id, Long version) {
        def keywordInstance = Keyword.get(id)
        if (!keywordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "list")
            return
        }

        keywordInstance.properties = params

        if (!keywordInstance.save(flush: true)) {
            render(view: "edit", model: [keywordInstance: keywordInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'keyword.label', default: 'Keyword'), keywordInstance.id])
        redirect(action: "show", id: keywordInstance.id)
    }

    def delete(Long id) {
        def keywordInstance = Keyword.get(id)
        if (!keywordInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "list")
            return
        }

        try {
            keywordInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'keyword.label', default: 'Keyword'), id])
            redirect(action: "show", id: id)
        }
    }
}
