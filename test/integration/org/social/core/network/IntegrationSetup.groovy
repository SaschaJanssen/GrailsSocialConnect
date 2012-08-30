package org.social.core.network

import org.social.core.util.UtilProperties

class IntegrationSetup {

    def IntegrationSetup() {
        loadProperties()
    }

    def loadProperties() {
        System.setProperty("https.proxyHost",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "https.proxyHost"))
        System.setProperty("https.proxyPort",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "https.proxyPort"))

        System.setProperty("http.proxyHost",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "http.proxyHost"))
        System.setProperty("http.proxyPort",
                        UtilProperties.getPropertyValue("grails-app/conf/social.properties", "http.proxyPort"))
    }
}
