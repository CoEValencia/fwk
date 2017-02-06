# Json To JXB

Clase que permite :
* Crear un elemento Jaxb genérico a partir de un Json.
    * Sin Qname definidio
    * Uri
    * Uri - Local
    * Uri - Local - Prefix

* Xml ToString del elemento Jaxb genérico.
    * Plain
    * Writer

## API

```java
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

```

## Test

JSON
```java
 String json = "{\"foo\":\"vicboma1\",\"boo\":100,\"too\":222.369}";
```

Full class path
```java
String expctedDeclareType = "class jaxb.Pojo";
```

POJO

```java
@XmlRootElement
public class Pojo {

    private String foo;
    private int boo;
    private double too;

    public Pojo(){
    }

    public String getFoo() {
        return foo;
    }

    public Pojo setFoo(String foo) {
        this.foo = foo;
        return this;
    }

    public int getBoo() {
        return boo;
    }

    public Pojo setBoo(int boo) {
        this.boo = boo;
        return this;
    }

    public double getToo() {
        return too;
    }

    public Pojo setToo(double too) {
        this.too = too;
        return this;
    }
}
```

XML
```xml
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
       <pojo>
           <boo>100</boo>
           <foo>vicboma1</foo>
           <too>222.369</too>
       </pojo>
```

* Local

```xml
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
       <Local>
           <boo>100</boo>
           <foo>vicboma1</foo>
           <too>222.369</too>
       </Local>
```

* Uri - Local

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <ns2:local xmlns:ns2="uri">
           <boo>100</boo>
           <foo>vicboma1</foo>
           <too>222.369</too>
      </ns2:local>
```

* Uri - Local - Prefix

```xml
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
       <prefix:local xmlns:prefix="uri">
           <boo>100</boo>
           <foo>vicboma1</foo>
           <too>222.369</too>
       </prefix:local>
```