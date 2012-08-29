
<%@ page import="org.social.grails.user.Keyword" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'keyword.label', default: 'Keyword')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-keyword" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-keyword" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="keyword" title="${message(code: 'keyword.keyword.label', default: 'Keyword')}" />
					
						<th><g:message code="keyword.customer.label" default="Customer" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'keyword.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="keyword.keywordType.label" default="Keyword Type" /></th>
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'keyword.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="keyword.network.label" default="Network" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${keywordInstanceList}" status="i" var="keywordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${keywordInstance.id}">${fieldValue(bean: keywordInstance, field: "keyword")}</g:link></td>
					
						<td>${fieldValue(bean: keywordInstance, field: "customer")}</td>
					
						<td><g:formatDate date="${keywordInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: keywordInstance, field: "keywordType")}</td>
					
						<td><g:formatDate date="${keywordInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: keywordInstance, field: "network")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${keywordInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
