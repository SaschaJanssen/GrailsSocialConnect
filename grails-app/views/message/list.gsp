
<%@ page import="org.social.grails.Message"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#list-message" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;" /></a>
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
					<label for="message"> <g:message code="message.message.label" default="Message" />
					</label>
					<g:textField name="message" value="${flash.message}" />
				</div>

				<div class="fieldcontain">
					<label for="customerId"> <g:message code="message.customer.label" default="Customer" />
					</label>
					<g:select name="customerId" value="${flash.customerId}" from="${org.social.grails.customer.Customer.list()}" optionKey="id"
						optionValue="id" noSelection="['':' ']" />
				</div>

				<div class="fieldcontain">
					<label for="network"> <g:message code="message.network.label" default="Network" />
					</label>
					<g:select name="network" value="${flash.network}" from="${org.social.core.constants.NetworkConst?.values()}"
						keys="${org.social.core.constants.NetworkConst.values()*.name()}" noSelection="['':' ']" />
				</div>

				<div class="fieldcontain">
					<label for="reliability"> <g:message code="message.reliability.label" default="Reliability" />
					</label>
					<g:select name="reliability" value="${flash.reliability}" from="${org.social.core.constants.ClassificationConst.Reliability?.values()}"
						keys="${org.social.core.constants.ClassificationConst.Reliability.values()*.name()}" noSelection="['':' ']" />
				</div>

				<div class="fieldcontain">
					<label for="sentiment"> <g:message code="message.sentiment.label" default="Sentiment" />
					</label>
					<g:select name="sentiment" value="${flash.sentiment}" from="${org.social.core.constants.ClassificationConst.Sentiment?.values()}"
						keys="${org.social.core.constants.ClassificationConst.Sentiment.values()*.name()}" noSelection="['':' ']" />
				</div>

				<div class="fieldcontain">
					<label for="networkMessageDateSince"> <g:message code="message.networkMessageDateSince.label" default="Network Message Is Younger then" />
					</label>
					<g:datePicker name="networkMessageDateSince" value="${flash.networkMessageDateSince}" noSelection="['':' ']" />
				</div>
			</fieldset>

			<div class="buttons">
				<span class="button"><input class="save" type="submit" value="Filter" /></span>
			</div>
		</g:form>
	</div>

	<div id="list-message" class="content scaffold-list" role="main">
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

					<g:sortableColumn property="id" title="${message(code: 'message.id.label', default: 'Message Id')}" />

					<g:sortableColumn property="network" title="${message(code: 'message.network.label', default: 'Network')}" />

					<g:sortableColumn property="customer" title="${message(code: 'message.customer.label', default: 'Customer')}" />

					<g:sortableColumn property="reliability" title="${message(code: 'message.reliability.label', default: 'Reliability')}" />

					<g:sortableColumn property="sentiment" title="${message(code: 'message.sentiment.label', default: 'Sentiment')}" />

					<g:sortableColumn property="networkMessageDate"
						title="${message(code: 'message.networkMessageDate.label', default: 'Network Message Date')}" />
				</tr>
			</thead>
			<tbody>
				<g:each in="${messageInstanceList}" status="i" var="messageInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${messageInstance.id}">
								${fieldValue(bean: messageInstance, field: "id")}
							</g:link></td>

						<td>
							${fieldValue(bean: messageInstance, field: "network")}
						</td>

						<td>
							${fieldValue(bean: messageInstance, field: "customer.id")}
						</td>

						<td>
							${fieldValue(bean: messageInstance, field: "reliability")}
						</td>

						<td>
							${fieldValue(bean: messageInstance, field: "sentiment")}
						</td>

						<td>
							${fieldValue(bean: messageInstance, field: "networkMessageDate")}
						</td>

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
