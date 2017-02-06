package jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Type;

interface JsonToJaxb {
    
    <T> T toClass(String json, Type _type);

    <T> T toClass(String json);

    <T> String toJson(T clazz, Type _type);

    <T> String toJson(T clazz);

    <T> String ToXml(T obj) throws JAXBException;

    <I,O> String ToXml(String json, Type _type) throws JAXBException;   

    <T> String ToJaxbElementString(T obj) throws JAXBException;

    <T> String ToJaxbElementString(String local, T obj) throws JAXBException;

    <T> String ToJaxbElementString(String uri, String local, T obj) throws JAXBException;

    <T> String ToJaxbElementString(String uri, String local, String prefix, T obj) throws JAXBException;

    <T> JAXBElement<T> ToJaxbElement(T obj) throws JAXBException;

    <T> JAXBElement<T> ToJaxbElement(String local, T obj) throws JAXBException;

    <T> JAXBElement<T> ToJaxbElement(String uri, String local, T obj) throws JAXBException;

    <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, T obj) throws JAXBException;

    <T> JAXBElement<T> ToJaxbElement(String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException;

    <T> JAXBElement<T> ToJaxbElement(String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    <T> JAXBElement<T> ToJaxbElement(String local, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException;
    
    <T> JAXBElement<T> ToJaxbElement(String uri, String local, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException;
    
    <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException;
    
    <T> JAXBElement<T> ToJaxbElement(String local, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException;

    <T> JAXBElement<T> ToJaxbElement(String uri, String local, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException;

    <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException;

}


