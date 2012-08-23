package org.social.core.consumer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import org.social.core.data.FilteredMessageList
import org.social.core.network.SocialNetworkKraken
import org.social.core.user.Customer
import org.social.core.user.Keyword

class ConsumerService {

    static transactional = false

    def krakenFactoryService

    private ExecutorService executor
    private FilteredMessageList results

    public SocialDataConsumer() {
        executor = Executors.newCachedThreadPool()
        results = new FilteredMessageList()
    }

    public FilteredMessageList consumeData(Customer customer) {
        def userNetworks = Keyword.executeQuery("select distinct k.network from Keyword k where k.customer= :id", [id: customer.id])

        for (String network : userNetworks) {
            SocialNetworkKraken socialNetworkCon = krakenFactoryService.getInstance(network, customer)

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
