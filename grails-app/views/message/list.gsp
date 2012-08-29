
<%@ page import="org.social.grails.network.Message" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-message" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-message" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="networkUserId" title="${message(code: 'message.networkUserId.label', default: 'Network User Id')}" />
					
						<g:sortableColumn property="networkUserName" title="${message(code: 'message.networkUserName.label', default: 'Network User Name')}" />
					
						<g:sortableColumn property="language" title="${message(code: 'message.language.label', default: 'Language')}" />
					
						<g:sortableColumn property="geoLocation" title="${message(code: 'message.geoLocation.label', default: 'Geo Location')}" />
					
						<g:sortableColumn property="networkUserRating" title="${message(code: 'message.networkUserRating.label', default: 'Network User Rating')}" />
					
						<th><g:message code="message.customer.label" default="Customer" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${messageInstanceList}" status="i" var="messageInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${messageInstance.id}">${fieldValue(bean: messageInstance, field: "networkUserId")}</g:link></td>
					
						<td>${fieldValue(bean: messageInstance, field: "networkUserName")}</td>
					
						<td>${fieldValue(bean: messageInstance, field: "language")}</td>
					
						<td>${fieldValue(bean: messageInstance, field: "geoLocation")}</td>
					
						<td>${fieldValue(bean: messageInstance, field: "networkUserRating")}</td>
					
						<td>${fieldValue(bean: messageInstance, field: "customer")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${messageInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
