
<%@ page import="org.social.core.classification.Classification" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'classification.label', default: 'Classification')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-classification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-classification" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'classification.description.label', default: 'Description')}" />
					
						<th><g:message code="classification.classificationType.label" default="Classification Type" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'classification.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'classification.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${classificationInstanceList}" status="i" var="classificationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${classificationInstance.id}">${fieldValue(bean: classificationInstance, field: "description")}</g:link></td>
					
						<td>${fieldValue(bean: classificationInstance, field: "classificationType")}</td>
					
						<td><g:formatDate date="${classificationInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${classificationInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${classificationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
