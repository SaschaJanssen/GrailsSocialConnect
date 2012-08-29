<%@ page import="org.social.grails.network.Network" %>



<div class="fieldcontain ${hasErrors(bean: networkInstance, field: 'id', 'error')} required">
	<label for="id">
		<g:message code="network.id.label" default="Network Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="id" value="${networkInstance?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: networkInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="network.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${networkInstance?.description}"/>
</div>

