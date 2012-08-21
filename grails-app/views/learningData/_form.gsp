<%@ page import="org.social.core.classification.LearningData" %>



<div class="fieldcontain ${hasErrors(bean: learningDataInstance, field: 'classification', 'error')} required">
	<label for="classification">
		<g:message code="learningData.classification.label" default="Classification" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="classification" name="classification.id" from="${org.social.core.classification.Classification.list()}" optionKey="id" required="" value="${learningDataInstance?.classification?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: learningDataInstance, field: 'classificationType', 'error')} required">
	<label for="classificationType">
		<g:message code="learningData.classificationType.label" default="Classification Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="classificationType" name="classificationType.id" from="${org.social.core.classification.ClassificationType.list()}" optionKey="id" required="" value="${learningDataInstance?.classificationType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: learningDataInstance, field: 'learningData', 'error')} ">
	<label for="learningData">
		<g:message code="learningData.learningData.label" default="Learning Data" />
		
	</label>
	<g:textField name="learningData" value="${learningDataInstance?.learningData}"/>
</div>

