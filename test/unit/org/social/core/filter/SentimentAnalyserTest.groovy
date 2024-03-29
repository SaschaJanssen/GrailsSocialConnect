package org.social.core.filter

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import grails.test.mixin.*

import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.social.core.constants.ClassificationConst
import org.social.core.filter.classifier.bayes.BayesClassifier
import org.social.core.filter.classifier.bayes.Classifier
import org.social.core.util.UtilLucene
import org.social.core.util.UtilValidate
import org.social.grails.Message;

@TestFor(SentimentAnalyser)
@Mock([Message])
public class SentimentAnalyserTest {

    private Logger logger = LoggerFactory.getLogger(getClass())

    private SentimentAnalyser analyser

    @Before
    public void setUp() {
        Classifier<String, String> classifier = BayesClassifier.getInstance()
        File fi = new File("test/resources/sentimentLearningTestData")
        BufferedReader bufferedFileReader = null
        try {
            bufferedFileReader = new BufferedReader(new FileReader(fi))
            String lineInFile
            while ((lineInFile = bufferedFileReader.readLine()) != null) {
                if (UtilValidate.isNotEmpty(lineInFile)) {
                    String[] split = lineInFile.split("�")

                    List<String> t = UtilLucene.ngramString(split[0])
                    classifier.learn(split[1], t)
                }
            }
        } catch (IOException e) {
            logger.error("", e)
        } finally {
            try {
                if (bufferedFileReader != null) {
                    bufferedFileReader.close()
                }
            } catch (IOException e) {
                logger.error("", e)
            }
        }

        analyser = SentimentAnalyser.getInstance()
    }

    @Test
    public void testSentiment() {
        def m = new Message()
        m.setMessage("I want a five guys burger and done cajun fries so damn bad")

        List<Message> fm = new ArrayList<Message>()
        fm.add(m)

        List<Message> fml = analyser.sentiment(fm)
        assertEquals(ClassificationConst.Sentiment.POSITIVE, fml.get(0).sentiment)
    }

    @Test
    public void testSentimentMultibleMessages() throws Exception {
        File fi = new File("test/resources/sentimentTestData")
        List<String> lr = new ArrayList<String>()
        BufferedReader bufferedFileReader = null

        try {
            bufferedFileReader = new BufferedReader(new FileReader(fi))
            String lineInFile
            while ((lineInFile = bufferedFileReader.readLine()) != null) {
                if (UtilValidate.isNotEmpty(lineInFile)) {
                    lr.add(lineInFile)
                }
            }
        } finally {
            if (bufferedFileReader != null) {
                bufferedFileReader.close()
            }
        }

        List<Message> fm = new ArrayList<Message>()

        for (String string : lr) {
            Message m = new Message()
            m.setMessage(string)

            fm.add(m)
        }

        analyser.sentiment(fm)

        for (Message msg : fm) {
            assertFalse(ClassificationConst.Sentiment.NOT_CLASSIFIED.isSameAs(msg.sentiment.name()))
        }
    }
}
