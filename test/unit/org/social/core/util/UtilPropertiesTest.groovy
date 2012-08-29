package org.social.core.util

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import grails.test.mixin.*

import org.junit.Test

@TestFor(UtilProperties)
public class UtilPropertiesTest {

    @Test
    public void testGetProperties() {
        Properties prop = UtilProperties.getProperties("grails-app/conf/social.properties")
        assertNotNull(prop)
        assertFalse(prop.isEmpty())
    }

    @Test
    public void testGetPropValue() throws Exception {
        String value = UtilProperties.getPropertyValue("grails-app/conf/social.properties", "application.name")
        assertEquals("SocialConnect", value)
    }

    @Test
    public void testGetLongValue() throws Exception {
        long value = UtilProperties.getPropertyValueAsLong("grails-app/conf/social.properties", "schedule.period", 6)
        assertEquals(3600000, value)

        value = UtilProperties.getPropertyValueAsLong("grails-app/conf/social.properties", "schedule.period2", 6)
        assertEquals(6, value)
    }

    @Test
    public void testValueAsBoolean() throws Exception {
        assertFalse(UtilProperties.getPropertyValueAsBoolean("conf/social.properties",
                        "write.user.last.network.access.to.db", false))
        assertTrue(UtilProperties.getPropertyValueAsBoolean("conf/social.properties", "test.default", true))
    }
}
