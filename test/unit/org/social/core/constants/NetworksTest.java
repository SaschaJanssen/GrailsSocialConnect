package org.social.core.constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NetworksTest {

    @Test
    public void testFacebook() {
        assertEquals("FACEBOOK", NetworkConst.FACEBOOK.getName());
    }

    @Test
    public void testTwitter() throws Exception {
        assertEquals("TWITTER", NetworkConst.TWITTER.getName());
    }

    @Test
    public void testYELP() throws Exception {
        assertEquals("YELP", NetworkConst.YELP.getName());
    }

    @Test
    public void testQYPE() throws Exception {
        assertEquals("QYPE", NetworkConst.QYPE.getName());
    }

    @Test
    public void testOPENTABLE() throws Exception {
        assertEquals("OPENTABLE", NetworkConst.OPENTABLE.getName());
    }

    @Test
    public void testTRIPADVISOR() throws Exception {
        assertEquals("TRIPADVISOR", NetworkConst.TRIPADVISOR.getName());
    }

    @Test
    public void testZAGAT() throws Exception {
        assertEquals("ZAGAT", NetworkConst.ZAGAT.getName());
    }

    @Test
    public void testFOURSQUARE() throws Exception {
        assertEquals("FOURSQUARE", NetworkConst.FOURSQUARE.getName());
    }

    @Test
    public void test_isNetwork() throws Exception {
        assertFalse(NetworkConst.FACEBOOK.isNetwork("TWITTER"));
        assertTrue(NetworkConst.FACEBOOK.isNetwork("FACEBOOK"));

        assertTrue(NetworkConst.TWITTER.isNetwork("TWITTER"));
        assertFalse(NetworkConst.TWITTER.isNetwork("FACEBOOK"));
    }
}
