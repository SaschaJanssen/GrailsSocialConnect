<%@ page import="org.social.core.classification.Classification" %>



<div class="fieldcontain ${hasErrors(bean: classificationInstance, field: 'id', 'error')} required">
	<label for="description">
		<g:message code="classification.id.label" default="ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="id" value="${classificationInstance?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: classificationInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="classification.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${classificationInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: classificationInstance, field: 'classificationType', 'error')} required">
	<label for="classificationType">
		<g:message code="classification.classificationType.label" default="Classification Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="classificationType" name="classificationType.id" from="${org.social.core.classification.ClassificationType.list()}" optionKey="id" optionValue="id" required="" value="${classificationInstance?.classificationType?.id}" class="many-to-one"/>
</div>

