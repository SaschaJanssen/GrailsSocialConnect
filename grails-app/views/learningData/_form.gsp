<%@ page import="org.social.grails.LearningData" %>



<div class="fieldcontain ${hasErrors(bean: learningDataInstance, field: 'classification', 'error')} required">
	<label for="classification">
		<g:message code="learningData.classification.label" default="Classification" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="classification" from="${org.social.core.constants.ClassificationConst$Sentiment?.values()}" keys="${org.social.core.constants.ClassificationConst$Sentiment.values()*.name()}" required="" value="${learningDataInstance?.classification?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: learningDataInstance, field: 'learningData', 'error')} ">
	<label for="learningData">
		<g:message code="learningData.learningData.label" default="Learning Data" />
		
	</label>
	<g:textField name="learningData" value="${learningDataInstance?.learningData}"/>
</div>

