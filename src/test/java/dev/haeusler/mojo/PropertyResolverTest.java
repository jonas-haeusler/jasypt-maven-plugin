package dev.haeusler.mojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Properties;

public class PropertyResolverTest {
    private final PropertyResolver resolver = new PropertyResolver();

    @Test
    public void validPlaceholderIsResolved() {
        Properties properties = new Properties();
        properties.setProperty("p1", "${p2}");
        properties.setProperty("p2", "value");

        String value1 = resolver.getPropertyValue("p1", properties, new Properties());
        String value2 = resolver.getPropertyValue("p2", properties, new Properties());

        assertEquals("value", value1);
        assertEquals("value", value2);
    }

    @Test
    public void unknownPlaceholderIsLeftAsIs() {
        Properties properties = new Properties();
        properties.setProperty("p1", "${p2}");
        properties.setProperty("p2", "value");
        properties.setProperty("p3", "${unknown}");

        String value1 = resolver.getPropertyValue("p1", properties, new Properties());
        String value2 = resolver.getPropertyValue("p2", properties, new Properties());
        String value3 = resolver.getPropertyValue("p3", properties, new Properties());

        assertEquals("value", value1);
        assertEquals("value", value2);
        assertEquals("${unknown}", value3);
    }

    @Test
    public void multipleValuesAreResolved() {
        Properties properties = new Properties();
        properties.setProperty("hostname", "localhost");
        properties.setProperty("port", "8080");
        properties.setProperty("base.url", "http://${hostname}:${port}/");

        String value = resolver.getPropertyValue("base.url", properties, new Properties());

        assertEquals("http://localhost:8080/", value);
    }

    @Test
    public void malformedPlaceholderIsLeftAsIs() {
        Properties properties = new Properties();
        properties.setProperty("p1", "${p2}");
        properties.setProperty("p2", "value");
        properties.setProperty("p4", "${malformed");

        String value1 = resolver.getPropertyValue("p1", properties, new Properties());
        String value2 = resolver.getPropertyValue("p2", properties, new Properties());
        String value4 = resolver.getPropertyValue("p4", properties, new Properties());

        assertEquals("value", value1);
        assertEquals("value", value2);
        assertEquals("${malformed", value4);
    }

    @Test
    public void propertyDefinedAsItselfIsIllegal() {
        Properties properties = new Properties();
        properties.setProperty("p1", "${p2}");
        properties.setProperty("p2", "value");
        properties.setProperty("p5", "${p5}");
        properties.setProperty("p6", "${p7}");
        properties.setProperty("p7", "${p6}");

        String value1 = resolver.getPropertyValue("p1", properties, new Properties());
        String value2 = resolver.getPropertyValue("p2", properties, new Properties());
        String value5 = null;
        try {
            value5 = resolver.getPropertyValue("p5", properties, new Properties());
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("p5"));
        }
        String value6 = null;
        try {
            value6 = resolver.getPropertyValue("p6", properties, new Properties());
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("p7"));
        }

        assertEquals("value", value1);
        assertEquals("value", value2);
        assertNull(value5);
        assertNull(value6);
    }

    @Test
    public void valueIsObtainedFromSystemProperty() {
        Properties saved = System.getProperties();
        System.setProperty("system.property", "system.value");

        Properties properties = new Properties();
        properties.setProperty("p1", "${system.property}");

        String value = resolver.getPropertyValue("p1", properties, new Properties());

        try {
            assertEquals("system.value", value);
        } finally {
            System.setProperties(saved);
        }
    }

    @Test
    public void valueIsObtainedFromEnvironmentProperty() {
        Properties environment = new Properties();
        environment.setProperty("PROPERTY", "env.value");

        Properties properties = new Properties();
        properties.setProperty("p1", "${env.PROPERTY}");

        String value = resolver.getPropertyValue("p1", properties, environment);

        assertEquals("env.value", value);
    }

    @Test
    public void missingPropertyIsTolerated() {
        assertEquals("", resolver.getPropertyValue("non-existent", new Properties(), null));
    }
}
