package org.social.core.user

import org.springframework.dao.DataIntegrityViolationException

class CustomerContactController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [customerContactInstanceList: CustomerContact.list(params), customerContactInstanceTotal: CustomerContact.count()]
    }

    def create() {
        [customerContactInstance: new CustomerContact(params)]
    }

    def save() {
        def customerContactInstance = new CustomerContact(params)
        if (!customerContactInstance.save(flush: true)) {
            render(view: "create", model: [customerContactInstance: customerContactInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), customerContactInstance.id])
        redirect(action: "show", id: customerContactInstance.id)
    }

    def show(Long id) {
        def customerContactInstance = CustomerContact.get(id)
        if (!customerContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "list")
            return
        }

        [customerContactInstance: customerContactInstance]
    }

    def edit(Long id) {
        def customerContactInstance = CustomerContact.get(id)
        if (!customerContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "list")
            return
        }

        [customerContactInstance: customerContactInstance]
    }

    def update(Long id, Long version) {
        def customerContactInstance = CustomerContact.get(id)
        if (!customerContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (customerContactInstance.version > version) {
                customerContactInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerContact.label', default: 'CustomerContact')] as Object[],
                          "Another user has updated this CustomerContact while you were editing")
                render(view: "edit", model: [customerContactInstance: customerContactInstance])
                return
            }
        }

        customerContactInstance.properties = params

        if (!customerContactInstance.save(flush: true)) {
            render(view: "edit", model: [customerContactInstance: customerContactInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), customerContactInstance.id])
        redirect(action: "show", id: customerContactInstance.id)
    }

    def delete(Long id) {
        def customerContactInstance = CustomerContact.get(id)
        if (!customerContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "list")
            return
        }

        try {
            customerContactInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerContact.label', default: 'CustomerContact'), id])
            redirect(action: "show", id: id)
        }
    }
}
