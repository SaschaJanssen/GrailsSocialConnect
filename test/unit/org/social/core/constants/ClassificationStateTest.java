package org.social.core.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassificationStateTest {

    @Test
    public void testCraftedGood() {
        assertEquals("RELIABLE", ClassificationConst.Reliability.RELIABLE.name());
    }

    @Test
    public void testCraftedBad() {
        assertEquals("NOT_RELIABLE", ClassificationConst.Reliability.NOT_RELIABLE.name());
    }

    @Test
    public void testCraftedNot() {
        assertEquals("NOT_CLASSIFIED", ClassificationConst.Reliability.NOT_CLASSIFIED.name());
    }

    @Test
    public void testCraftedNot2() {
        assertEquals("NOT_CLASSIFIED", ClassificationConst.Sentiment.NOT_CLASSIFIED.name());
    }

    @Test
    public void testNEGATIVE() {
        assertEquals("NEGATIVE", ClassificationConst.Sentiment.NEGATIVE.name());
    }

    @Test
    public void testNEUTRAL() {
        assertEquals("NEUTRAL", ClassificationConst.Sentiment.NEUTRAL.name());
    }

    @Test
    public void testPOSITIVE() {
        assertEquals("POSITIVE", ClassificationConst.Sentiment.POSITIVE.name());
    }
}
