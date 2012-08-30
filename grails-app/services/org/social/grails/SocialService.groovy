package org.social.grails

import org.social.core.data.FilteredMessageList
import org.social.core.filter.classifier.bayes.BayesClassifier
import org.social.core.filter.classifier.bayes.Classifier
import org.social.core.util.UtilDateTime
import org.social.core.util.UtilLucene
import org.social.core.util.UtilProperties
import org.social.grails.classification.LearningData
import org.social.grails.consumer.ConsumerService
import org.social.grails.network.Message
import org.social.grails.user.Customer

class SocialService {

    static transactional = true

    private boolean storeLastNetworkAccessToDb

    public void start() {
        loadProperties()

        learn()

        def customers = Customer.list()
        customers.each() {  customer  ->
            def consumerService = new ConsumerService()
            FilteredMessageList filteredMessageDataList = consumerService.consumeData(customer)

            storeFilteredListsInDb(filteredMessageDataList)


            if (storeLastNetworkAccessToDb) {
                customer.lastNetworkAccess = UtilDateTime.nowTimestamp()
                customer.save(flush: true)
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Finished social connect run successfully.")
        }
    }

    private storeFilteredListsInDb(FilteredMessageList filteredMessageDataList) {
        filteredMessageDataList.getNegativeList().each() { message -> writeToDb(message) }
        filteredMessageDataList.getPositiveList().each() { message -> writeToDb(message) }
    }

    private writeToDb(Message message) {
        if(!message.save(flush: true)) {
            message.errors.each { log.error(it) }
        }
    }

    private void learn() {
        List<LearningData> learningData = LearningData.list(fetch: [classification: "eager"])
        Classifier<String, String> classifier = BayesClassifier.getInstance()
        classifier.reset()

        learningData.each() { data ->
            List<String> t = UtilLucene.ngramString(data.getLearningData())
            def sentiment = data.classification.name()
            classifier.learn(sentiment, t)
        }
    }

    private void loadProperties() {
        System.setProperty("https.proxyHost",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "https.proxyHost"))
        System.setProperty("https.proxyPort",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "https.proxyPort"))

        System.setProperty("http.proxyHost",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "http.proxyHost"))
        System.setProperty("http.proxyPort",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "http.proxyPort"))

        System.setProperty("derby.system.home",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "derby.system.home"))

        storeLastNetworkAccessToDb = UtilProperties.getPropertyValueAsBoolean("grails-app/conf/social.properties",
                        "write.user.last.network.access.to.db", true)
    }
}
