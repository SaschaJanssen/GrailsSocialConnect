
<%@ page import="org.social.grails.network.Message" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-message" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-message" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list message">
			
				<g:if test="${messageInstance?.networkUserId}">
				<li class="fieldcontain">
					<span id="networkUserId-label" class="property-label"><g:message code="message.networkUserId.label" default="Network User Id" /></span>
					
						<span class="property-value" aria-labelledby="networkUserId-label"><g:fieldValue bean="${messageInstance}" field="networkUserId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.networkUserName}">
				<li class="fieldcontain">
					<span id="networkUserName-label" class="property-label"><g:message code="message.networkUserName.label" default="Network User Name" /></span>
					
						<span class="property-value" aria-labelledby="networkUserName-label"><g:fieldValue bean="${messageInstance}" field="networkUserName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.language}">
				<li class="fieldcontain">
					<span id="language-label" class="property-label"><g:message code="message.language.label" default="Language" /></span>
					
						<span class="property-value" aria-labelledby="language-label"><g:fieldValue bean="${messageInstance}" field="language"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.geoLocation}">
				<li class="fieldcontain">
					<span id="geoLocation-label" class="property-label"><g:message code="message.geoLocation.label" default="Geo Location" /></span>
					
						<span class="property-value" aria-labelledby="geoLocation-label"><g:fieldValue bean="${messageInstance}" field="geoLocation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.networkUserRating}">
				<li class="fieldcontain">
					<span id="networkUserRating-label" class="property-label"><g:message code="message.networkUserRating.label" default="Network User Rating" /></span>
					
						<span class="property-value" aria-labelledby="networkUserRating-label"><g:fieldValue bean="${messageInstance}" field="networkUserRating"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.customer}">
				<li class="fieldcontain">
					<span id="customer-label" class="property-label"><g:message code="message.customer.label" default="Customer" /></span>
					
						<span class="property-value" aria-labelledby="customer-label"><g:link controller="customer" action="show" id="${messageInstance?.customer?.id}">${messageInstance?.customer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="message.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${messageInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="message.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${messageInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.message}">
				<li class="fieldcontain">
					<span id="message-label" class="property-label"><g:message code="message.message.label" default="Message" /></span>
					
						<span class="property-value" aria-labelledby="message-label"><g:fieldValue bean="${messageInstance}" field="message"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.messageReceivedDate}">
				<li class="fieldcontain">
					<span id="messageReceivedDate-label" class="property-label"><g:message code="message.messageReceivedDate.label" default="Message Received Date" /></span>
					
						<span class="property-value" aria-labelledby="messageReceivedDate-label"><g:formatDate date="${messageInstance?.messageReceivedDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.network}">
				<li class="fieldcontain">
					<span id="network-label" class="property-label"><g:message code="message.network.label" default="Network" /></span>
					
						<span class="property-value" aria-labelledby="network-label"><g:link controller="network" action="show" id="${messageInstance?.network?.id}">${messageInstance?.network?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.networkMessageDate}">
				<li class="fieldcontain">
					<span id="networkMessageDate-label" class="property-label"><g:message code="message.networkMessageDate.label" default="Network Message Date" /></span>
					
						<span class="property-value" aria-labelledby="networkMessageDate-label"><g:formatDate date="${messageInstance?.networkMessageDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.reliability}">
				<li class="fieldcontain">
					<span id="reliability-label" class="property-label"><g:message code="message.reliability.label" default="Reliability" /></span>
					
						<span class="property-value" aria-labelledby="reliability-label"><g:link controller="classification" action="show" id="${messageInstance?.reliability?.id}">${messageInstance?.reliability?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${messageInstance?.sentiment}">
				<li class="fieldcontain">
					<span id="sentiment-label" class="property-label"><g:message code="message.sentiment.label" default="Sentiment" /></span>
					
						<span class="property-value" aria-labelledby="sentiment-label"><g:link controller="classification" action="show" id="${messageInstance?.sentiment?.id}">${messageInstance?.sentiment?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${messageInstance?.id}" />
					<g:link class="edit" action="edit" id="${messageInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
