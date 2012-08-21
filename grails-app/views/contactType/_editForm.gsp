<%@ page import="org.social.core.user.ContactType" %>



<div class="fieldcontain ${hasErrors(bean: contactTypeInstance, field: 'id', 'error')} ">
	<label for="id">
		<g:message code="contactType.id.label" default="Contact Type Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="id" value="${contactTypeInstance?.id}" readonly="readonly"/>
</div>
<div class="fieldcontain ${hasErrors(bean: contactTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="contactType.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${contactTypeInstance?.description}"/>
</div>

