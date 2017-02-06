package jaxb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.lang.reflect.Type;


public class JsonToJaxbImpl implements JsonToJaxb {

    private static final String UTF_8 = "UTF-8";
    private Gson gson;

    public static JsonToJaxbImpl create(GsonBuilder builder) {
        return new JsonToJaxbImpl(builder.create());
    }

    public static JsonToJaxbImpl create() {
        return new JsonToJaxbImpl(
                new GsonBuilder().create()
        );
    }

    JsonToJaxbImpl(Gson gson){
        this.gson = gson;
    }

    public <T> T toClass(String json, Type _type){
        final T newclass = gson.fromJson(json, _type);
        return newclass;
    }

    @SuppressWarnings("static-access")
    public <T> T toClass(String json){
        final Type type =  this.<T>factoryTypeToken();
        final T newclass = gson.fromJson(json, type);
        return newclass;
    }

    public <T> String toJson(T clazz, Type _type){
        final String toJson = gson.toJson(clazz,_type);
        return toJson;
    }

    public <T> String toJson(T clazz){
        final Type type =  factoryTypeToken();
        final String toJson = toJson(clazz,type);
        return toJson;
    }

    public <T> String ToXml(T obj) throws JAXBException{
        final JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        final StringWriter sw = new StringWriter();

        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(obj, sw);

        return sw.toString();
    }

    public <I,O> String ToXml(String json, Type _type) throws JAXBException{
        final I obj = toClass(json,_type);
        String res = ToXml(obj);

        return res;
    }

    public <T> String ToJaxbElementString(T obj) throws JAXBException{
        return ToJaxbElementString(XMLConstants.NULL_NS_URI,"",XMLConstants.DEFAULT_NS_PREFIX,obj);
    }

    public <T> String ToJaxbElementString(String local, T obj) throws JAXBException{
        return ToJaxbElementString(XMLConstants.NULL_NS_URI,local,XMLConstants.DEFAULT_NS_PREFIX,obj);
    }

    public <T> String ToJaxbElementString(String uri, String local, T obj) throws JAXBException{
        return ToJaxbElementString(uri,local,XMLConstants.DEFAULT_NS_PREFIX,obj);
    }

    public <T> String ToJaxbElementString(String uri, String local, String prefix, T obj) throws JAXBException{
        final JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        final JAXBElement<T> element = ToJaxbElement(uri,local,prefix, obj);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(element, sw);

        return sw.toString();

    }

    public <T> JAXBElement<T> ToJaxbElement(T obj) throws JAXBException{
        final JAXBElement<T> element = new JAXBElement<T>(new QName(XMLConstants.NULL_NS_URI,"",XMLConstants.DEFAULT_NS_PREFIX),  getClass(obj), obj);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String local, T obj) throws JAXBException{
        final JAXBElement<T> element = new JAXBElement<T>(new QName(XMLConstants.NULL_NS_URI,local,XMLConstants.DEFAULT_NS_PREFIX),  getClass(obj), obj);
        return element;
    }


    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, T obj) throws JAXBException{
        final JAXBElement<T> element = new JAXBElement<T>(new QName(uri,local,XMLConstants.DEFAULT_NS_PREFIX),  getClass(obj), obj);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, T obj) throws JAXBException{
        final JAXBElement<T> element = new JAXBElement<T>(new QName(uri,local,prefix),   getClass(obj), obj);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json,type);
        final JAXBElement<T> element = ToJaxbElement(objJson);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json);
        final JAXBElement<T> element = ToJaxbElement(objJson);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String local, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json,type);
        final JAXBElement<T> element = ToJaxbElement(local, objJson);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json,type);
        final JAXBElement<T> element = ToJaxbElement(uri,local, objJson);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, String fullPathNamespaceClass, String json, Type type) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json, type);
        final JAXBElement<T> element = ToJaxbElement(uri,local,prefix, objJson);
        return element;
    }

    @SuppressWarnings("static-access")
    public <T> JAXBElement<T> ToJaxbElement(String local, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json);
        final JAXBElement<T> element = ToJaxbElement(local, objJson);
        return element;
    }

    @SuppressWarnings("static-access")
    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json );
        final JAXBElement<T> element = ToJaxbElement(uri,local, objJson);
        return element;
    }

    public <T> JAXBElement<T> ToJaxbElement(String uri, String local, String prefix, String fullPathNamespaceClass, String json) throws JAXBException, ClassNotFoundException{
        T objJson = this.<T>getClassWithFullNameObject(fullPathNamespaceClass, json);
        final JAXBElement<T> element = ToJaxbElement(uri,local,prefix, objJson);
        return element;
    }

    private static <T> Type factoryTypeToken() {
        return new TypeToken<T>(){}.getType();
    }

    @SuppressWarnings({ "rawtypes"})
    private <T> T getClassWithFullNameObject(String fullPathNamespaceClass, String json) throws ClassNotFoundException {
        Class obj =  Class.forName(fullPathNamespaceClass);
        T objJson = (T) gson.fromJson(json, obj);
        return objJson;
    }

    @SuppressWarnings({ "rawtypes", "unused" })
    private <T> T getClassWithFullNameObject(String fullPathNamespaceClass, String json, Type type) throws ClassNotFoundException {
        Class obj =  Class.forName(fullPathNamespaceClass);
        T objJson = gson.fromJson(json, type);
        return objJson;
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> getClass(T obj) {
        return (Class<T>) obj.getClass();
    }

}


