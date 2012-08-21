<%@ page import="org.social.core.user.KeywordType" %>

<div class="fieldcontain ${hasErrors(bean: keywordTypeInstance, field: 'id', 'error')} required">
	<label for="id">
		<g:message code="keywordType.id.label" default="Keyword Type Id" />

	</label>
	<g:textField name="id" value="${keywordTypeInstance?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="keywordType.description.label" default="Description" />

	</label>
	<g:textField name="description" value="${keywordTypeInstance?.description}"/>
</div>

