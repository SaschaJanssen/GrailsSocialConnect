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
	<g:select id="customer" name="customer.id" from="${org.social.grails.user.Customer.list()}" optionKey="id" optionValue="id" required="" value="${keywordInstance?.customer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'keywordType', 'error')} required">
	<label for="keywordType">
		<g:message code="keyword.keywordType.label" default="Keyword Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="keywordType" from="${org.social.core.constants.KeywordTypeConst?.values()}" keys="${org.social.core.constants.KeywordTypeConst.values()*.name()}" required="" value="${keywordInstance?.keywordType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: keywordInstance, field: 'network', 'error')} required">
	<label for="network">
		<g:message code="keyword.network.label" default="Network" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="network" from="${org.social.core.constants.NetworkConst?.values()}" keys="${org.social.core.constants.NetworkConst.values()*.name()}" required="" value="${keywordInstance?.network?.name()}"/>
</div>

