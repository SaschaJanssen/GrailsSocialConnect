<%@ page import="org.social.core.classification.ClassificationType" %>



<div class="fieldcontain ${hasErrors(bean: classificationTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="classificationType.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${classificationTypeInstance?.description}"/>
</div>

