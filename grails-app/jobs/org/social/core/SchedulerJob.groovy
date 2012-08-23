package org.social.core



class SchedulerJob {
    def socialService
    
    static triggers = {
      simple startDelay: 0, repeatInterval: 60000
    }

    def execute() {
        
        socialService.start()
    }
}
