package org.social.core.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.ClassificationConst;
import org.social.core.filter.SentimentAnalyser;
import org.social.core.filter.TwitterMentionedFilter;
import org.social.core.filter.WordlistFilter;
import org.social.grails.Message;

public class DataCrafter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private List<Message> rawData = null;

    private List<Message> positiveList = null;
    private List<Message> negativeList = null;

    public DataCrafter(List<Message> rawList) {
        rawData = rawList;

        positiveList = new ArrayList<Message>();
        negativeList = new ArrayList<Message>();
    }

    public FilteredMessageList reliabilityAndSentimentCrafter(CustomerNetworkKeywords customerKeywords) {
        wordlistFilter();
        mentionedFilter(customerKeywords);
        sentimentAnalyser();

        FilteredMessageList filteredMessages = new FilteredMessageList();
        filteredMessages.setPositiveList(positiveList);
        filteredMessages.setNegativeList(negativeList);
        return filteredMessages;
    }

    public FilteredMessageList sentimentCrafter() {
        positiveList = rawData;

        sentimentAnalyser();

        FilteredMessageList filteredMessages = new FilteredMessageList();
        filteredMessages.setPositiveList(positiveList);
        return filteredMessages;
    }

    private void sentimentAnalyser() {
        SentimentAnalyser sentimentAnalyser = SentimentAnalyser.getInstance();
        positiveList = sentimentAnalyser.sentiment(positiveList);
    }

    private void wordlistFilter() {
        WordlistFilter wordlistFilter = WordlistFilter.getInstance();
        for (Message msgData : rawData) {
            if (wordlistFilter.matchesWordList(msgData.getMessage())) {
                addToPositiveList(msgData);
            } else {
                addToNegativeList(msgData);
            }
        }
    }

    private void addToNegativeList(Message negativeMessage) {
        negativeMessage.setReliability(ClassificationConst.Reliability.NOT_RELIABLE);
        negativeList.add(negativeMessage);
    }

    private void addToPositiveList(Message positiveMessage) {
        positiveMessage.setReliability(ClassificationConst.Reliability.RELIABLE);
        positiveList.add(positiveMessage);
    }

    private void mentionedFilter(CustomerNetworkKeywords customerKeywords) {

        if (log.isDebugEnabled()) {
            log.debug("Create mentioned filter.");
        }

        TwitterMentionedFilter mentionFilter = new TwitterMentionedFilter(customerKeywords);

        List<Message> messagesToMove = new ArrayList<Message>();
        for (Message message : negativeList) {
            if (mentionFilter.mentioned(message.getMessage())) {
                messagesToMove.add(message);
            }
        }

        moveItemFromNegativeToPositiveList(messagesToMove);
    }

    public void moveItemFromNegativeToPositiveList(List<Message> messagesToMove) {
        if (log.isDebugEnabled()) {
            log.debug("Move Item to positive list.");
        }

        for (Message message : messagesToMove) {
            if (negativeList.contains(message)) {
                negativeList.remove(message);
            }
            addToPositiveList(message);
        }
    }
}
