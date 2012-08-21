
<%@ page import="org.social.core.user.Contact" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-contact" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-contact" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="contact.contactType.label" default="Contact Type" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'contact.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="infoString" title="${message(code: 'contact.infoString.label', default: 'Info String')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'contact.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${contactInstanceList}" status="i" var="contactInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${contactInstance.id}">${fieldValue(bean: contactInstance, field: "contactType")}</g:link></td>
					
						<td><g:formatDate date="${contactInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: contactInstance, field: "infoString")}</td>
					
						<td><g:formatDate date="${contactInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${contactInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
