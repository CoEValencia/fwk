# Json To JAXB

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
   fun <T : Any> toClass(json: String, _type: Type) : T

   fun <T : Any> toClass(json: String): T

   fun <T : Any> toJson(clazz: T, _type: Type): String

   fun <T : Any> toJson(clazz: T): String

   @Throws(JAXBException::class)
   fun <T : Any> ToXml(obj: T): String

   @Throws(JAXBException::class)
   fun <T : Any, O> ToXml(json: String, _type: Type): String

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElementString(obj: T): String

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElementString(local: String, obj: T): String

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElementString(uri: String, local: String, obj: T): String

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElementString(uri: String, local: String, prefix: String, obj: T): String

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElement(obj: T): JAXBElement<T>

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElement(local: String, obj: T): JAXBElement<T>

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, obj: T): JAXBElement<T>

   @Throws(JAXBException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, obj: T): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class)
   fun <T : Any> ToJaxbElement(fullPathNamespaceClass: String, json: String): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(local: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(local: String, fullPathNamespaceClass: String, json: String): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, fullPathNamespaceClass: String, json: String): JAXBElement<T>

   @Throws(JAXBException::class, ClassNotFoundException::class)
   fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, fullPathNamespaceClass: String, json: String): JAXBElement<T>

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
