
<%@ page import="org.social.core.user.Keyword" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'keyword.label', default: 'Keyword')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-keyword" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-keyword" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list keyword">
			
				<g:if test="${keywordInstance?.customer}">
				<li class="fieldcontain">
					<span id="customer-label" class="property-label"><g:message code="keyword.customer.label" default="Customer" /></span>
					
						<span class="property-value" aria-labelledby="customer-label"><g:link controller="customer" action="show" id="${keywordInstance?.customer?.id}">${keywordInstance?.customer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${keywordInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="keyword.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${keywordInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${keywordInstance?.keyword}">
				<li class="fieldcontain">
					<span id="keyword-label" class="property-label"><g:message code="keyword.keyword.label" default="Keyword" /></span>
					
						<span class="property-value" aria-labelledby="keyword-label"><g:fieldValue bean="${keywordInstance}" field="keyword"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${keywordInstance?.keywordType}">
				<li class="fieldcontain">
					<span id="keywordType-label" class="property-label"><g:message code="keyword.keywordType.label" default="Keyword Type" /></span>
					
						<span class="property-value" aria-labelledby="keywordType-label"><g:link controller="keywordType" action="show" id="${keywordInstance?.keywordType?.id}">${keywordInstance?.keywordType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${keywordInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="keyword.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${keywordInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${keywordInstance?.network}">
				<li class="fieldcontain">
					<span id="network-label" class="property-label"><g:message code="keyword.network.label" default="Network" /></span>
					
						<span class="property-value" aria-labelledby="network-label"><g:link controller="network" action="show" id="${keywordInstance?.network?.id}">${keywordInstance?.network?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${keywordInstance?.id}" />
					<g:link class="edit" action="edit" id="${keywordInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
