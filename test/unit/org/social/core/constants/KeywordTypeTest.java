package org.social.core.constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KeywordTypeTest {

    @Test
    public void testQuery() {
        assertEquals("QUERY", KeywordTypeConst.QUERY.getName());
    }

    @Test
    public void testHash() {
        assertEquals("HASH", KeywordTypeConst.HASH.getName());
    }

    @Test
    public void testMentioned() {
        assertEquals("MENTIONED", KeywordTypeConst.MENTIONED.getName());
    }

    @Test
    public void testPage() {
        assertEquals("PAGE", KeywordTypeConst.PAGE.getName());
    }

    @Test
    public void testIsKeywordType() throws Exception {
        assertTrue(KeywordTypeConst.QUERY.isKeywordType(KeywordTypeConst.QUERY));
        assertTrue(KeywordTypeConst.HASH.isKeywordType(KeywordTypeConst.HASH));
        assertTrue(KeywordTypeConst.MENTIONED.isKeywordType(KeywordTypeConst.MENTIONED));
        assertTrue(KeywordTypeConst.PAGE.isKeywordType(KeywordTypeConst.PAGE));

        assertFalse(KeywordTypeConst.MENTIONED.isKeywordType(KeywordTypeConst.HASH));
    }

}
