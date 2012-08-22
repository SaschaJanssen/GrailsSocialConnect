package org.social.core.network

import org.springframework.dao.DataIntegrityViolationException

class NetworkController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [networkInstanceList: Network.list(params), networkInstanceTotal: Network.count()]
    }

    def create() {
        [networkInstance: new Network(params)]
    }

    def save() {
        def networkInstance = new Network(params)
        networkInstance.id = params.id
        if (!networkInstance.save(flush: true)) {
            render(view: "create", model: [networkInstance: networkInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'network.label', default: 'Network'), networkInstance.id])
        redirect(action: "show", id: networkInstance.id)
    }

    def show(String id) {
        def networkInstance = Network.get(id)
        if (!networkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "list")
            return
        }

        [networkInstance: networkInstance]
    }

    def edit(String id) {
        def networkInstance = Network.get(id)
        if (!networkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "list")
            return
        }

        [networkInstance: networkInstance]
    }

    def update(String id, String version) {
        def networkInstance = Network.get(id)
        if (!networkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "list")
            return
        }

        networkInstance.properties = params

        if (!networkInstance.save(flush: true)) {
            render(view: "edit", model: [networkInstance: networkInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'network.label', default: 'Network'), networkInstance.id])
        redirect(action: "show", id: networkInstance.id)
    }

    def delete(String id) {
        def networkInstance = Network.get(id)
        if (!networkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "list")
            return
        }

        try {
            networkInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'network.label', default: 'Network'), id])
            redirect(action: "show", id: id)
        }
    }
}
