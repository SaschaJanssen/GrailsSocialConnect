<%@ page import="org.social.core.classification.ClassificationType" %>

<div class="fieldcontain">
	<label for="id">
		<g:message code="classificationType.id.label" default="ID" />

	</label>
	<g:textField name="id" value="${classificationTypeInstance?.id}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: classificationTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="classificationType.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${classificationTypeInstance?.description}"/>
</div>

