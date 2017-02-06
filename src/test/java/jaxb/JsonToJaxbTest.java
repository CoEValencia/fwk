package jaxb;

import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonToJaxbTest {

    private JsonToJaxb jsonToJaxb;

    @org.junit.Before
    public void setUp() throws Exception {
        jsonToJaxb = JsonToJaxbImpl.create();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        jsonToJaxb = null;
    }

    @Test
    public void testToJson() {
        String expected = "[1,2,3,4,5,6]";
        final List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        String result = jsonToJaxb.toJson(list);
        Assert.assertTrue("List<Integer> is not same the expected "+result, result.equals(expected));
    }

    @SuppressWarnings({"serial" })
    @Test
    public void testToClass() {
        final List<Integer> expected = new ArrayList<Integer>() { { add(1); add(2); add(3); add(4); add(5); add(6); }};
        String str = "[1, 2, 3, 4, 5, 6]";
        final List<Integer> result = jsonToJaxb.toClass(str, new TypeToken<List<Integer>>(){}.getType());
        String resultToString =  result.toString();
        String expectedToString =  expected.toString();
        Assert.assertTrue("[1, 2, 3, 4, 5, 6] is not same the expected ", resultToString.equals(expectedToString));

    }

    @Test
    public void testToJsonClass() {
        String expected = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo pojo = new Pojo()
                .setBoo(100)
                .setFoo("vicboma1")
                .setToo(222.369);

        String result = jsonToJaxb.toJson(pojo);
        Assert.assertTrue("Is not the same toString "+result+" with the expected String "+expected, result.equals(expected));
    }

    @Test
    public void testToJsonSpecificClass() {
        String expected = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo pojo = new Pojo()
                .setBoo(100)
                .setFoo("vicboma1")
                .setToo(222.369);

        String result = jsonToJaxb.toJson(pojo, new TypeToken<Pojo>(){}.getType());
        Assert.assertTrue("Is not the same toString "+result+" with the expected String "+expected, result.equals(expected));
    }

    @Test
    public void testToClassSpecificJson() {
        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());
        Assert.assertNotNull(result);
        Assert.assertTrue("Is not the same class "+result+" with the expected String "+json, jsonToJaxb.toJson(result).toString().equals(json));
    }

    @Test
    public void testToClassJsonFail() {
        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        Pojo result = null;
        try {
            result = jsonToJaxb.toClass(json);
        }
        catch(Exception e){
            Assert.assertNull(result);
        }
    }

    @Test
    public void testToJsonSpecificClassJaxBString() throws JAXBException {

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<pojo>\n"
                + "    <boo>100</boo>\n"
                + "    <foo>vicboma1</foo>\n"
                + "    <too>222.369</too>\n"
                + "</pojo>\n";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final String resultXml = jsonToJaxb.ToXml(result);

        Assert.assertEquals(expected,resultXml.toString());
    }

    @Test
    public void testToJsonJaxBString() throws JAXBException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<pojo>\n"
                + "    <boo>100</boo>\n"
                + "    <foo>vicboma1</foo>\n"
                + "    <too>222.369</too>\n"
                + "</pojo>\n";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final String resultXml = jsonToJaxb.ToXml(json, new TypeToken<Pojo>(){}.getType());

        Assert.assertEquals(expected,resultXml.toString());
    }

    @Test
    public void testToJsonJaxbElement() throws JAXBException {
        String expctedDeclareType = "class jaxb.Pojo";
        String expctedQnameUri = "uri";
        String expctedQnameLocal = "local";
        String expctedQnamePrefix = "prefix";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final JAXBElement<Pojo> element = jsonToJaxb.ToJaxbElement("uri", "local", "prefix", result);

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(expctedQnameUri,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(expctedQnamePrefix,element.getName().getPrefix().toString());


    }

    @Test
    public void testToJsonJaxbElementOnly() throws JAXBException {
        String expctedDeclareType = "class jaxb.Pojo";
        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final JAXBElement<Pojo> element = jsonToJaxb.ToJaxbElement(result);

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(XMLConstants.NULL_NS_URI,element.getName().getNamespaceURI().toString());
        Assert.assertEquals("",element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());
    }

    @Test
    public void testToJsonJaxbElementLocal() throws JAXBException {
        String expctedDeclareType = "class jaxb.Pojo";
        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        String expctedQnameLocal = "local";

        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final JAXBElement<Pojo> element = jsonToJaxb.ToJaxbElement("local",result);

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(XMLConstants.NULL_NS_URI,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());
    }


    @Test
    public void testToJsonJaxbElementUriLocal() throws JAXBException {
        String expctedDeclareType = "class jaxb.Pojo";
        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        String expctedQnameUri = "uri";
        String expctedQnameLocal = "local";

        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final JAXBElement<Pojo> element = jsonToJaxb.ToJaxbElement("uri", "local", result);

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(expctedQnameUri,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());


    }

    @Test
    public void testToJsonJaxBElementStringLocal() throws JAXBException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<LocalNamespace>\n"
                + "    <boo>100</boo>\n"
                + "    <foo>vicboma1</foo>\n"
                + "    <too>222.369</too>\n"
                + "</LocalNamespace>\n";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final String element = jsonToJaxb.ToJaxbElementString("LocalNamespace", result);


        Assert.assertEquals(expected,element.toString());
    }

    @Test
    public void testToJsonJaxBElementStringLocalUri() throws JAXBException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<ns2:local xmlns:ns2=\"uri\">\n"
                + "    <boo>100</boo>\n"
                + "    <foo>vicboma1</foo>\n"
                + "    <too>222.369</too>\n"
                + "</ns2:local>\n";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final String element = jsonToJaxb.ToJaxbElementString("uri","local", result);


        Assert.assertEquals(expected,element.toString());
    }

    @Test
    public void testToJsonJaxBElementStringLocalUriPrefix() throws JAXBException {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<prefix:local xmlns:prefix=\"uri\">\n"
                + "    <boo>100</boo>\n"
                + "    <foo>vicboma1</foo>\n"
                + "    <too>222.369</too>\n"
                + "</prefix:local>\n";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
        final Pojo result = jsonToJaxb.toClass(json, new TypeToken<Pojo>(){}.getType());

        final String element = jsonToJaxb.ToJaxbElementString("uri","local","prefix", result);

        Assert.assertEquals(expected,element.toString());
    }


    @Test
    public void testToJsonJaxbElemenLocalWithFullClassNamespaceClass() throws JAXBException, ClassNotFoundException {
        String expctedDeclareType = "class jaxb.Pojo";
        String expctedQnameLocal = "local";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final JAXBElement<Pojo> element = jsonToJaxb.<Pojo>ToJaxbElement("local", "jaxb.Pojo", json,	new TypeToken<Pojo>(){}.getType());

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(XMLConstants.NULL_NS_URI,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());

    }


    @Test
    public void testToJsonJaxbElemenLocalWithFullClassNamespaceClassLocal() throws JAXBException, ClassNotFoundException {
        String expctedDeclareType = "class jaxb.Pojo";
        String expctedQnameLocal = "local";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final JAXBElement<Pojo> element = jsonToJaxb.<Pojo>ToJaxbElement("local", "jaxb.Pojo", json,	new TypeToken<Pojo>(){}.getType());

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(XMLConstants.NULL_NS_URI,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());

    }


    @Test
    public void testToJsonJaxbElemenLocalWithFullClassNamespaceClassUriLocal() throws JAXBException, ClassNotFoundException {
        String expctedDeclareType = "class jaxb.Pojo";
        String expctedQnameLocal = "local";
        String expctedQnameUri = "uri";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final JAXBElement<Pojo> element = jsonToJaxb.<Pojo>ToJaxbElement("uri","local", "jaxb.Pojo", json,	new TypeToken<Pojo>(){}.getType());

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(expctedQnameUri,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(XMLConstants.DEFAULT_NS_PREFIX,element.getName().getPrefix().toString());

    }

    @Test
    public void testToJsonJaxbElemenLocalWithFullClassNamespaceClassUriLocalPrefix() throws JAXBException, ClassNotFoundException {
        String expctedDeclareType = "class jaxb.Pojo";
        String expctedQnameLocal = "local";
        String expctedQnameUri = "uri";
        String expctedQnamePrefix = "prefix";

        String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";

        final JAXBElement<Pojo> element = jsonToJaxb.<Pojo>ToJaxbElement("uri","local","prefix", "jaxb.Pojo", json,	new TypeToken<Pojo>(){}.getType());

        Assert.assertEquals(expctedDeclareType,element.getDeclaredType().toString());

        Assert.assertEquals(expctedQnameUri,element.getName().getNamespaceURI().toString());
        Assert.assertEquals(expctedQnameLocal,element.getName().getLocalPart().toString());
        Assert.assertEquals(expctedQnamePrefix,element.getName().getPrefix().toString());

    }
}