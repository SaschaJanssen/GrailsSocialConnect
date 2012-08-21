<%@ page import="org.social.core.user.Customer" %>



<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'lastNetworkAccess', 'error')} ">
	<label for="lastNetworkAccess">
		<g:message code="customer.lastNetworkAccess.label" default="Last Network Access" />
		
	</label>
	<g:datePicker name="lastNetworkAccess" precision="day"  value="${customerInstance?.lastNetworkAccess}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'contact', 'error')} ">
	<label for="contact">
		<g:message code="customer.contact.label" default="Contact" />
		
	</label>
	<g:select name="contact" from="${org.social.core.user.Contact.list()}" multiple="multiple" optionKey="id" size="5" value="${customerInstance?.contact*.id}" class="many-to-many"/>
</div>

