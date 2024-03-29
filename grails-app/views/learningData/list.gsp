
<%@ page import="org.social.grails.LearningData" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'learningData.label', default: 'LearningData')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-learningData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-learningData" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="classification" title="${message(code: 'learningData.classification.label', default: 'Classification')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'learningData.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'learningData.lastUpdated.label', default: 'Last Updated')}" />
					
						<g:sortableColumn property="learningData" title="${message(code: 'learningData.learningData.label', default: 'Learning Data')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${learningDataInstanceList}" status="i" var="learningDataInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${learningDataInstance.id}">${fieldValue(bean: learningDataInstance, field: "classification")}</g:link></td>
					
						<td><g:formatDate date="${learningDataInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${learningDataInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: learningDataInstance, field: "learningData")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${learningDataInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
