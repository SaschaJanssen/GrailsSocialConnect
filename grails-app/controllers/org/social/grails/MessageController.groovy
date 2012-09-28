package org.social.grails

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException
import org.social.grails.customer.Customer


@Secured(['IS_AUTHENTICATED_FULLY'])
class MessageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        flash.message = params.message
        flash.reliability = params.reliability
        flash.sentiment = params.sentiment
        flash.network = params.network
        flash.customerId = params.customerId
        flash.networkMessageDateSince = params.networkMessageDateSince
        
        params.max = Math.min(max ?: 10, 100)
        
        def messageCriteria = Message.createCriteria()
        def results = messageCriteria.list(params) {
            and{
                if (params.message) {
                    like("message", '%' + params.message + '%')
                }
                
                if(params.customerId) {
                    eq("customer", Customer.get(params.customerId))
                }
                
                if(params.network) {
                    eq("network", org.social.core.constants.NetworkConst.valueOf(params.network))
                }
                
                if(params.reliability) {
                    eq("reliability", org.social.core.constants.ClassificationConst.Reliability.valueOf(params.reliability))
                }

                if(params.sentiment) {
                    eq("sentiment", org.social.core.constants.ClassificationConst.Sentiment.valueOf(params.sentiment))
                }
                
                if (params.networkMessageDateSince) {
                    ge("networkMessageDate", params.networkMessageDateSince)
                }
            }
        }
        
        messageCriteria = Message.createCriteria()
        def resultPaginationMaxLength = messageCriteria.list {
            and{
                if (params.message) {
                    like("message", '%' + params.message + '%')
                }
                
                if(params.customerId) {
                    eq("customer", Customer.get(params.customerId))
                }
                
                if(params.network) {
                    eq("network", org.social.core.constants.NetworkConst.valueOf(params.network))
                }
                
                if(params.reliability) {
                    eq("reliability", org.social.core.constants.ClassificationConst.Reliability.valueOf(params.reliability))
                }

                if(params.sentiment) {
                    eq("sentiment", org.social.core.constants.ClassificationConst.Sentiment.valueOf(params.sentiment))
                }
                
                if (params.networkMessageDateSince) {
                    ge("networkMessageDate", params.networkMessageDateSince)
                }
            }
            
            projections {
                rowCount()
            }
        }
        
        [messageInstanceList: results, messageInstanceTotal: resultPaginationMaxLength.first()]
    }

    def create() {
        [messageInstance: new Message(params)]
    }

    def save() {
        def messageInstance = new Message(params)
        if (!messageInstance.save(flush: true)) {
            render(view: "create", model: [messageInstance: messageInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'message.label', default: 'Message'), messageInstance.id])
        redirect(action: "show", id: messageInstance.id)
    }

    def show(Long id) {
        def messageInstance = Message.get(id)
        if (!messageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "list")
            return
        }

        [messageInstance: messageInstance]
    }

    def edit(Long id) {
        def messageInstance = Message.get(id)
        if (!messageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "list")
            return
        }

        [messageInstance: messageInstance]
    }

    def update(Long id, Long version) {
        def messageInstance = Message.get(id)
        if (!messageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (messageInstance.version > version) {
                messageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'message.label', default: 'Message')] as Object[],
                          "Another user has updated this Message while you were editing")
                render(view: "edit", model: [messageInstance: messageInstance])
                return
            }
        }

        messageInstance.properties = params

        if (!messageInstance.save(flush: true)) {
            render(view: "edit", model: [messageInstance: messageInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'message.label', default: 'Message'), messageInstance.id])
        redirect(action: "show", id: messageInstance.id)
    }

    def delete(Long id) {
        def messageInstance = Message.get(id)
        if (!messageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "list")
            return
        }

        try {
            messageInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'message.label', default: 'Message'), id])
            redirect(action: "show", id: id)
        }
    }
}
