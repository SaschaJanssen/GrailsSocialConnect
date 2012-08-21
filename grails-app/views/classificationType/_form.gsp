<%@ page import="org.social.core.classification.ClassificationType" %>

<div class="fieldcontain ${hasErrors(bean: classificationTypeInstance, field: 'id', 'error')} required">
	<label for="id">
		<g:message code="classificationType.id.label" default="ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="id" value="${classificationTypeInstance?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: classificationTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="classificationType.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${classificationTypeInstance?.description}"/>
</div>

