<%@ page import="org.social.grails.user.Keyword" %>



<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'keyword', 'error')} ">
	<label for="keyword">
		<g:message code="keyword.keyword.label" default="Keyword" />
		
	</label>
	<g:textField name="keyword" value="${keywordInstance?.keyword}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'customer', 'error')} required">
	<label for="customer">
		<g:message code="keyword.customer.label" default="Customer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="customer" name="customer.id" from="${org.social.grails.user.Customer.list()}" optionKey="id" required="" value="${keywordInstance?.customer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'keywordType', 'error')} required">
	<label for="keywordType">
		<g:message code="keyword.keywordType.label" default="Keyword Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="keywordType" name="keywordType.id" from="${org.social.grails.user.KeywordType.list()}" optionKey="id" required="" value="${keywordInstance?.keywordType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'network', 'error')} required">
	<label for="network">
		<g:message code="keyword.network.label" default="Network" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="network" name="network.id" from="${org.social.grails.network.Network.list()}" optionKey="id" required="" value="${keywordInstance?.network?.id}" class="many-to-one"/>
</div>

