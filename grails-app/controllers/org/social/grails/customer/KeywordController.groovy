package org.social.grails.customer

import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException
import org.social.grails.Keyword

@Secured(['IS_AUTHENTICATED_FULLY'])
class KeywordController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        flash.keyword = params.keyword
        flash.customerId = params.customerId
        flash.keywordType = params.keywordType
        flash.network = params.network
        
        params.max = Math.min(max ?: 10, 100)
        
        def keywordCriteria = Keyword.createCriteria()
        def results = keywordCriteria.list(params) { 
            and{
                if (params.keyword) {
                    like("keyword", '%' + params.keyword + '%')
                }
                
                if(params.customerId) {
                    eq("customer", Customer.get(params.customerId))
                }
                
                if(params.network) {
                    eq("network", org.social.core.constants.NetworkConst.valueOf(params.network))
                }
                
                if(params.keywordType) {
                    eq("keywordType", org.social.core.constants.KeywordTypeConst.valueOf(params.keywordType))
                }
            }
        }
        
        keywordCriteria = Keyword.createCriteria()
        def resultPaginationMaxLength = keywordCriteria.list { 
            and{
                if (params.keyword) {
                    like("keyword", '%' + params.keyword + '%')
                }
                
                if(params.customerId) {
                    eq("customer", Customer.get(params.customerId))
                }
                
                if(params.network) {
                    eq("network", org.social.core.constants.NetworkConst.valueOf(params.network))
                }
                
                if(params.keywordType) {
                    eq("keywordType", org.social.core.constants.KeywordTypeConst.valueOf(params.keywordType))
                }
            }
            
            projections {
                rowCount()
            }
        }
        
        [keywordInstanceList: results, keywordInstanceTotal: resultPaginationMaxLength.first()]
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
