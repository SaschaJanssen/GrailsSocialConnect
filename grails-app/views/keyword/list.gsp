
<%@ page import="org.social.grails.Keyword"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'keyword.label', default: 'Keyword')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#list-keyword" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" /></a></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>

	<div id="list-filter" class="content">
		<h1>
			<g:message code="default.list-filter.label" default="Filter" />
		</h1>

		<g:form action="list" method="post">
			<fieldset class="form">
				<div class="fieldcontain">
					<label for="keyword"> <g:message code="keyword.keyword.label" default="Keyword" />
					</label>
					<g:textField name="keyword" value="${flash.keyword}" />
				</div>

				<div class="fieldcontain">
					<label for="customerId"> <g:message code="keyword.customer.label" default="Customer" />
					</label>
					<g:select name="customerId" value="${flash.customerId}" from="${org.social.grails.customer.Customer.list()}" optionKey="id"
						optionValue="id" noSelection="['':' ']" />
				</div>

				<div class="fieldcontain">
					<label for="keywordType"> <g:message code="keyword.keywordType.label" default="Keyword Type" />
					</label>
					<g:select name="keywordType" value="${flash.keywordType}" from="${org.social.core.constants.KeywordTypeConst?.values()}"
						keys="${org.social.core.constants.KeywordTypeConst.values()*.name()}" noSelection="['':' ']" />
				</div>
				<div class="fieldcontain">
					<label for="network"> <g:message code="keyword.network.label" default="Network" />
					</label>
					<g:select name="network" value="${flash.network}" from="${org.social.core.constants.NetworkConst?.values()}"
						keys="${org.social.core.constants.NetworkConst.values()*.name()}" noSelection="['':' ']" />
				</div>
			</fieldset>

			<div class="buttons">
				<span class="button"><input class="save" type="submit" value="Filter" /></span>
			</div>
		</g:form>
	</div>

	<div id="list-keyword" class="content scaffold-list" role="main">
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<g:sortableColumn property="keyword" title="${message(code: 'keyword.keyword.label', default: 'Keyword')}" />

					<th><g:message code="keyword.customer.label" default="Customer" /></th>

					<g:sortableColumn property="dateCreated" title="${message(code: 'keyword.dateCreated.label', default: 'Date Created')}" />

					<g:sortableColumn property="keywordType" title="${message(code: 'keyword.keywordType.label', default: 'Keyword Type')}" />

					<g:sortableColumn property="lastUpdated" title="${message(code: 'keyword.lastUpdated.label', default: 'Last Updated')}" />

					<g:sortableColumn property="network" title="${message(code: 'keyword.network.label', default: 'Network')}" />

				</tr>
			</thead>
			<tbody>
				<g:each in="${keywordInstanceList}" status="i" var="keywordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${keywordInstance.id}">
								${fieldValue(bean: keywordInstance, field: "keyword")}
							</g:link></td>

						<td>
							${fieldValue(bean: keywordInstance, field: "customer.id")}
						</td>

						<td><g:formatDate date="${keywordInstance.dateCreated}" /></td>

						<td>
							${fieldValue(bean: keywordInstance, field: "keywordType")}
						</td>

						<td><g:formatDate date="${keywordInstance.lastUpdated}" /></td>

						<td>
							${fieldValue(bean: keywordInstance, field: "network")}
						</td>

					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${keywordInstanceTotal}" params="${flash}" />
		</div>
	</div>
</body>
</html>
