package org.social.grails.consumer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import org.social.core.data.FilteredMessageList
import org.social.core.network.SocialNetworkKraken
import org.social.grails.network.KrakenFactoryService
import org.social.grails.user.Customer
import org.social.grails.user.Keyword

class ConsumerService {

    static transactional = false

    private ExecutorService executor
    private FilteredMessageList results

    public ConsumerService() {
        executor = Executors.newCachedThreadPool()
        results = new FilteredMessageList()
    }

    public FilteredMessageList consumeData(Customer customer) {
        if (log.isDebugEnabled()) {
            log.debug "Consume Data for customer with id: " + customer.id
        }

        def userNetworks = Keyword.executeQuery("select distinct k.network from Keyword k where k.customer= ?", [customer])

        def krakenFactory = new KrakenFactoryService()
        userNetworks.each() { network -> 
            SocialNetworkKraken socialNetworkCon = krakenFactory.getInstance(network, customer)

            Thread networkThread = new Thread(new NetworkConsumeThread(socialNetworkCon))
            executor.execute(networkThread)
        }

        waitForThreadsToFinish()

        if (log.isDebugEnabled()) {
            log.debug("Finished all threads and found " + results.size() + " messages.")
        }
        return results
    }

    private void waitForThreadsToFinish() {
        executor.shutdown()
        try {
            if (log.isDebugEnabled()) {
                log.debug("Wait for threads to finish.")
            }
            executor.awaitTermination(1000, TimeUnit.SECONDS)
        } catch (InterruptedException e) {
            log.error("", e)
        }
    }

    private class NetworkConsumeThread implements Runnable {
        private final SocialNetworkKraken networkConnection

        public NetworkConsumeThread(SocialNetworkKraken networkConnection) {
            this.networkConnection = networkConnection
        }

        @Override
        public void run() {
            if (log.isDebugEnabled()) {
                log.debug("Start new " + networkConnection.getClass().getName()
                                + " data consumer and filtering thread.")
            }

            FilteredMessageList filteredMessages = networkConnection.fetchAndCraftMessages()

            if (log.isDebugEnabled()) {
                log.debug("Found " + filteredMessages.size() + " Messages from Network: "
                                + networkConnection.getClass().getName())
            }

            results.addAll(filteredMessages)
        }
    }
}
