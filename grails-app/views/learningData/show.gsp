
<%@ page import="org.social.core.classification.LearningData" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'learningData.label', default: 'LearningData')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-learningData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-learningData" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list learningData">
			
				<g:if test="${learningDataInstance?.classification}">
				<li class="fieldcontain">
					<span id="classification-label" class="property-label"><g:message code="learningData.classification.label" default="Classification" /></span>
					
						<span class="property-value" aria-labelledby="classification-label"><g:link controller="classification" action="show" id="${learningDataInstance?.classification?.id}">${learningDataInstance?.classification?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${learningDataInstance?.classificationType}">
				<li class="fieldcontain">
					<span id="classificationType-label" class="property-label"><g:message code="learningData.classificationType.label" default="Classification Type" /></span>
					
						<span class="property-value" aria-labelledby="classificationType-label"><g:link controller="classificationType" action="show" id="${learningDataInstance?.classificationType?.id}">${learningDataInstance?.classificationType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${learningDataInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="learningData.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${learningDataInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${learningDataInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="learningData.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${learningDataInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${learningDataInstance?.learningData}">
				<li class="fieldcontain">
					<span id="learningData-label" class="property-label"><g:message code="learningData.learningData.label" default="Learning Data" /></span>
					
						<span class="property-value" aria-labelledby="learningData-label"><g:fieldValue bean="${learningDataInstance}" field="learningData"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${learningDataInstance?.id}" />
					<g:link class="edit" action="edit" id="${learningDataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
