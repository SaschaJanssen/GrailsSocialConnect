package org.social.grails.user

import org.social.grails.user.ContactType;
import org.springframework.dao.DataIntegrityViolationException

class ContactTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [contactTypeInstanceList: ContactType.list(params), contactTypeInstanceTotal: ContactType.count()]
    }

    def create() {
        [contactTypeInstance: new ContactType(params)]
    }

    def save() {
        def contactTypeInstance = new ContactType(params)
        contactTypeInstance.id = params.id
        if (!contactTypeInstance.save(flush: true)) {
            render(view: "create", model: [contactTypeInstance: contactTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'contactType.label', default: 'ContactType'), contactTypeInstance.id])
        redirect(action: "show", id: contactTypeInstance.id)
    }

    def show(String id) {
        def contactTypeInstance = ContactType.get(id)
        if (!contactTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "list")
            return
        }

        [contactTypeInstance: contactTypeInstance]
    }

    def edit(String id) {
        def contactTypeInstance = ContactType.get(id)
        if (!contactTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "list")
            return
        }

        [contactTypeInstance: contactTypeInstance]
    }

    def update(String id, String version) {
        def contactTypeInstance = ContactType.get(id)
        if (!contactTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "list")
            return
        }

        contactTypeInstance.properties = params

        if (!contactTypeInstance.save(flush: true)) {
            render(view: "edit", model: [contactTypeInstance: contactTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'contactType.label', default: 'ContactType'), contactTypeInstance.id])
        redirect(action: "show", id: contactTypeInstance.id)
    }

    def delete(String id) {
        def contactTypeInstance = ContactType.get(id)
        if (!contactTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "list")
            return
        }

        try {
            contactTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'contactType.label', default: 'ContactType'), id])
            redirect(action: "show", id: id)
        }
    }
}
