<%@ page import="org.social.grails.Message" %>



<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'networkUserId', 'error')} ">
	<label for="networkUserId">
		<g:message code="message.networkUserId.label" default="Network User Id" />
		
	</label>
	<g:textField name="networkUserId" maxlength="128" value="${messageInstance?.networkUserId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'networkUserName', 'error')} ">
	<label for="networkUserName">
		<g:message code="message.networkUserName.label" default="Network User Name" />
		
	</label>
	<g:textField name="networkUserName" maxlength="80" value="${messageInstance?.networkUserName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'language', 'error')} ">
	<label for="language">
		<g:message code="message.language.label" default="Language" />
		
	</label>
	<g:textField name="language" maxlength="5" value="${messageInstance?.language}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'geoLocation', 'error')} ">
	<label for="geoLocation">
		<g:message code="message.geoLocation.label" default="Geo Location" />
		
	</label>
	<g:textField name="geoLocation" maxlength="20" value="${messageInstance?.geoLocation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'networkUserRating', 'error')} ">
	<label for="networkUserRating">
		<g:message code="message.networkUserRating.label" default="Network User Rating" />
		
	</label>
	<g:textField name="networkUserRating" maxlength="10" value="${messageInstance?.networkUserRating}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'customer', 'error')} required">
	<label for="customer">
		<g:message code="message.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="customer" name="customer.id" from="${org.social.grails.user.Customer.list()}" optionKey="id" required="" value="${messageInstance?.customer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'message', 'error')} ">
	<label for="message">
		<g:message code="message.message.label" default="Message" />
		
	</label>
	<g:textField name="message" value="${messageInstance?.message}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'messageReceivedDate', 'error')} required">
	<label for="messageReceivedDate">
		<g:message code="message.messageReceivedDate.label" default="Message Received Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="messageReceivedDate" precision="day"  value="${messageInstance?.messageReceivedDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'network', 'error')} required">
	<label for="network">
		<g:message code="message.network.label" default="Network" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="network" from="${org.social.core.constants.NetworkConst?.values()}" keys="${org.social.core.constants.NetworkConst.values()*.name()}" required="" value="${messageInstance?.network?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'networkMessageDate', 'error')} required">
	<label for="networkMessageDate">
		<g:message code="message.networkMessageDate.label" default="Network Message Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="networkMessageDate" precision="day"  value="${messageInstance?.networkMessageDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'reliability', 'error')} required">
	<label for="reliability">
		<g:message code="message.reliability.label" default="Reliability" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="reliability" from="${org.social.core.constants.ClassificationConst$Reliability?.values()}" keys="${org.social.core.constants.ClassificationConst$Reliability.values()*.name()}" required="" value="${messageInstance?.reliability?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'sentiment', 'error')} required">
	<label for="sentiment">
		<g:message code="message.sentiment.label" default="Sentiment" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="sentiment" from="${org.social.core.constants.ClassificationConst$Sentiment?.values()}" keys="${org.social.core.constants.ClassificationConst$Sentiment.values()*.name()}" required="" value="${messageInstance?.sentiment?.name()}"/>
</div>

