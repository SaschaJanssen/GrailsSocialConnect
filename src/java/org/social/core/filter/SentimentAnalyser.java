package org.social.core.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.constants.ClassificationConst;
import org.social.core.filter.classifier.bayes.BayesClassifier;
import org.social.core.filter.classifier.bayes.Classifier;
import org.social.core.util.UtilLucene;
import org.social.grails.Message;

public class SentimentAnalyser {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();

    public static SentimentAnalyser getInstance() {
        return sentimentAnalyser;
    }

    public List<Message> sentiment(List<Message> filteredMessageList) {
        return bayesClassifier(filteredMessageList);
    }

    private List<Message> bayesClassifier(List<Message> reliableMessageList) {
        Classifier<String, String> classifier = BayesClassifier.getInstance();

        for (Message msgData : reliableMessageList) {
            List<String> unClassifiedText = UtilLucene.ngramString(msgData.getMessage());

            String classification = null;
            try {
                classification = classifier.classify(unClassifiedText).getCategory();
            } catch (IllegalStateException e) {
                log.warn("Classifier throw the following Exception. Classification will be skiped.");
                break;
            }

            if (log.isDebugEnabled()) {
                log.debug("Message: " + msgData.getMessage() + " classified as: " + classification);
            }

            if (ClassificationConst.Sentiment.POSITIVE.isSameAs(classification)) {
                msgData.setSentiment(ClassificationConst.Sentiment.POSITIVE);

            } else if (ClassificationConst.Sentiment.NEGATIVE.isSameAs(classification)) {
                msgData.setSentiment(ClassificationConst.Sentiment.NEGATIVE);

            } else {
                msgData.setSentiment(ClassificationConst.Sentiment.NEUTRAL);
            }
        }
        return reliableMessageList;
    }
}
