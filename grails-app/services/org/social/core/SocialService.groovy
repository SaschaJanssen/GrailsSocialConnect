package org.social.core

import org.social.core.classification.LearningData
import org.social.core.data.FilteredMessageList
import org.social.core.filter.classifier.bayes.BayesClassifier
import org.social.core.filter.classifier.bayes.Classifier
import org.social.core.user.Customer
import org.social.core.util.UtilLucene
import org.social.core.util.UtilProperties

class SocialService {

    def consumerService

    private boolean storeLastNetworkAccessToDb

    public SocialConnect() {
        loadProperties()
    }

    public void start() {

        // learn()

        def customers = Customer.list()
        for (Customer customer : customers) {
            FilteredMessageList filteredMessageDataList = consumerService.consumeData(customer)

            // TODO save messages in DB --> messageDao.storeMessages(filteredMessageDataList);

            if (storeLastNetworkAccessToDb) {
                // TODO update user lastNetworkAccess in DB --> customerDao.updateCustomerNetworkAccess(customer, UtilDateTime.nowTimestamp());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Finished social connect run successfully.")
        }

    }

    private void learn() {
        List<LearningData> learningData = LearningData.list()
        Classifier<String, String> classifier = BayesClassifier.getInstance()
        classifier.reset()
        for (LearningData data : learningData) {
            List<String> t = UtilLucene.ngramString(data.getLearningData())
            classifier.learn(data.classification, t)
        }
    }

    private void loadProperties() {
        System.setProperty("https.proxyHost",
                        UtilProperties.getPropertyValue("conf/social.properties", "https.proxyHost"))
        System.setProperty("https.proxyPort",
                        UtilProperties.getPropertyValue("conf/social.properties", "https.proxyPort"))

        System.setProperty("http.proxyHost",
                        UtilProperties.getPropertyValue("conf/social.properties", "http.proxyHost"))
        System.setProperty("http.proxyPort",
                        UtilProperties.getPropertyValue("conf/social.properties", "http.proxyPort"))

        System.setProperty("derby.system.home",
                        UtilProperties.getPropertyValue("conf/social.properties", "derby.system.home"))

        storeLastNetworkAccessToDb = UtilProperties.getPropertyValueAsBoolean("conf/social.properties",
                        "write.user.last.network.access.to.db", true)
    }

}
