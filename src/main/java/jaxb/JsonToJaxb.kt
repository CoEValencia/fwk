package jaxb

import java.lang.reflect.Type
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException

interface JsonToJaxb {

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

}


