<%@ page import="org.social.grails.user.Contact" %>



<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'contactType', 'error')} required">
	<label for="contactType">
		<g:message code="contact.contactType.label" default="Contact Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="contactType" name="contactType.id" from="${org.social.grails.user.ContactType.list()}" optionKey="id" optionValue="id" required="" value="${contactInstance?.contactType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'infoString', 'error')} ">
	<label for="infoString">
		<g:message code="contact.infoString.label" default="Info String" />
		
	</label>
	<g:textField name="infoString" value="${contactInstance?.infoString}"/>
</div>

