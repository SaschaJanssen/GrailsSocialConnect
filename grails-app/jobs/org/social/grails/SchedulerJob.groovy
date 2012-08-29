package org.social.grails

import org.social.core.util.UtilProperties;

class SchedulerJob {
    def socialService
    
    static triggers = {
       def interval = UtilProperties.getPropertyValueAsLong("grails-app/conf/social.properties", "schedule.period", 3600000)
       simple name: 'Social Trigger', startDelay: 1000, repeatInterval: interval, repeatCount: -1 
    }

    def execute() {
        socialService.start()
    }
}
