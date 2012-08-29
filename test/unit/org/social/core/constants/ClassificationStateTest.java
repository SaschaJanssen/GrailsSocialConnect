package org.social.core.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassificationStateTest {

    @Test
    public void testCraftedGood() {
        assertEquals("RELIABLE", ClassificationConst.RELIABLE.getName());
    }

    @Test
    public void testCraftedBad() {
        assertEquals("NOT_RELIABLE", ClassificationConst.NOT_RELIABLE.getName());
    }

    @Test
    public void testCraftedNot() {
        assertEquals("NOT_CLASSIFIED", ClassificationConst.NOT_CLASSIFIED.getName());
    }

    @Test
    public void testNEGATIVE() {
        assertEquals("NEGATIVE", ClassificationConst.NEGATIVE.getName());
    }

    @Test
    public void testNEUTRAL() {
        assertEquals("NEUTRAL", ClassificationConst.NEUTRAL.getName());
    }

    @Test
    public void testPOSITIVE() {
        assertEquals("POSITIVE", ClassificationConst.POSITIVE.getName());
    }
}
