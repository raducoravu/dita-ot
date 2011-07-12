/*
 * This file is part of the DITA Open Toolkit project hosted on
 * Sourceforge.net. See the accompanying license.txt file for 
 * applicable licenses.
 */

/*
 * (c) Copyright IBM Corp. 2011 All Rights Reserved.
 */
package org.dita.dost.util;

import static org.dita.dost.util.Constants.ATTRIBUTE_NAME_CLASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.helpers.AttributesImpl;


public class DitaClassTest {

    @Test
    public void testHashCode() {
        assertEquals(new DitaClass("- foo/bar baz/qux ").hashCode(), new DitaClass("- foo/bar baz/qux ").hashCode());
        assertEquals(new DitaClass("- foo/bar baz/qux ").hashCode(), new DitaClass("-  foo/bar  baz/qux  ").hashCode());
    }

    @Test
    public void testDitaClass() {
        new DitaClass("- foo/bar baz/qux ");
        try {
            new DitaClass(null);
            fail();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testEqualsObject() {
        assertTrue(new DitaClass("- foo/bar baz/qux ").equals(new DitaClass("- foo/bar baz/qux ")));
        assertTrue(new DitaClass("- foo/bar baz/qux ").equals(new DitaClass("-  foo/bar  baz/qux  ")));
    }

    @Test
    public void testToString() {
        assertEquals("- foo/bar baz/qux ", new DitaClass("- foo/bar baz/qux ").toString());
        assertEquals("- foo/bar baz/qux ", new DitaClass("-  foo/bar  baz/qux  ").toString());
    }
    
    @Test
    public void testLocalName() {
        assertEquals("qux", new DitaClass("- foo/bar baz/qux ").localName);
    }
    
    @Test
    public void testMatcher() {
        assertEquals(" baz/qux ", new DitaClass("- foo/bar baz/qux ").matcher);
    }
    
    @Test
    public void testMatchesDitaClass() {
        assertTrue(new DitaClass("- foo/bar ").matches(new DitaClass("- foo/bar baz/qux ")));
        assertTrue(new DitaClass("- foo/bar baz/qux ").matches(new DitaClass("- foo/bar baz/qux ")));
    }
    
    @Test
    public void testMatchesString() {
        assertTrue(new DitaClass("- foo/bar ").matches("- foo/bar baz/qux "));
        assertTrue(new DitaClass("- foo/bar baz/qux ").matches("- foo/bar baz/qux "));
    }
    
    @Test
    public void testMatchesAttributes() {
        final AttributesImpl atts = new AttributesImpl();
        atts.addAttribute("", ATTRIBUTE_NAME_CLASS, ATTRIBUTE_NAME_CLASS, "CDATA", "- foo/bar baz/qux ");
        assertTrue(new DitaClass("- foo/bar ").matches(atts));
        assertTrue(new DitaClass("- foo/bar baz/qux ").matches(atts));
        assertFalse(new DitaClass("- bar/baz ").matches(atts));
    }

    @Test
    public void testMatchesNode() throws ParserConfigurationException {
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Element elem = doc.createElement("qux");
        elem.setAttribute(ATTRIBUTE_NAME_CLASS, "- foo/bar baz/qux ");
        assertTrue(new DitaClass("- foo/bar ").matches(elem));
        assertTrue(new DitaClass("- foo/bar baz/qux ").matches(elem));
        assertFalse(new DitaClass("- bar/baz ").matches(elem));
    }

}
