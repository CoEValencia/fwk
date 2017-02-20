package jaxb

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.StringWriter
import java.lang.reflect.Type
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.namespace.QName
import kotlin.reflect.KClass


class JsonToJaxbImpl internal constructor(private val gson: Gson) : JsonToJaxb {

    @SuppressWarnings("unchecked")
    private fun<T: Any> T.getClass(): KClass<T> = javaClass.kotlin

    companion object {

        private val UTF_8 = "UTF-8"

        fun create(builder: GsonBuilder) = JsonToJaxbImpl(builder.create())

        fun create(): JsonToJaxbImpl = JsonToJaxbImpl(GsonBuilder().create())

        private fun <T> factoryTypeToken() = object : TypeToken<T>() {}.type
    }

    override fun <T : Any> toClass(json: String, _type: Type) = gson.fromJson<T>(json, _type)

    @SuppressWarnings("static-access")
    override fun <T : Any> toClass(json: String): T {
        val type = factoryTypeToken<T>()
        val newclass = gson.fromJson<T>(json, type)
        return newclass
    }

    override fun <T : Any> toJson(clazz: T, _type: Type) =  gson.toJson(clazz, _type)

    override fun <T : Any> toJson(clazz: T) = toJson(clazz, factoryTypeToken<T>())

    @Throws(JAXBException::class)
    override fun <T : Any> ToXml(obj: T): String {
        val jaxbContext = JAXBContext.newInstance(obj.getClass().java)
        val jaxbMarshaller = jaxbContext.createMarshaller()

        val sw = StringWriter()

        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8)
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

        jaxbMarshaller.marshal(obj, sw)

        return sw.toString()
    }

    @Throws(JAXBException::class)
    override fun <I : Any, O> ToXml(json: String, _type: Type): String {
        val obj = toClass<I>(json, _type)
        val res = ToXml(obj)

        return res
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElementString(obj: T): String {
        return ToJaxbElementString(XMLConstants.NULL_NS_URI, "", XMLConstants.DEFAULT_NS_PREFIX, obj)
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElementString(local: String, obj: T): String {
        return ToJaxbElementString(XMLConstants.NULL_NS_URI, local, XMLConstants.DEFAULT_NS_PREFIX, obj)
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElementString(uri: String, local: String, obj: T): String {
        return ToJaxbElementString(uri, local, XMLConstants.DEFAULT_NS_PREFIX, obj)
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElementString(uri: String, local: String, prefix: String, obj: T): String {
        val jaxbContext = JAXBContext.newInstance(obj.getClass().java)
        val jaxbMarshaller = jaxbContext.createMarshaller()
        val element = ToJaxbElement(uri, local, prefix, obj)
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, java.lang.Boolean.TRUE)

        val sw = StringWriter()
        jaxbMarshaller.marshal(element, sw)

        return sw.toString()

    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElement(obj: T): JAXBElement<T> {
        val element = JAXBElement(QName(XMLConstants.NULL_NS_URI, "", XMLConstants.DEFAULT_NS_PREFIX), obj.getClass().java, obj)
        return element
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElement(local: String, obj: T): JAXBElement<T> {
        val element = JAXBElement(QName(XMLConstants.NULL_NS_URI, local, XMLConstants.DEFAULT_NS_PREFIX), obj.getClass().java, obj)
        return element
    }


    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, obj: T): JAXBElement<T> {
        val element = JAXBElement(QName(uri, local, XMLConstants.DEFAULT_NS_PREFIX), obj.getClass().java, obj)
        return element
    }

    @Throws(JAXBException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, obj: T): JAXBElement<T> {
        val element = JAXBElement(QName(uri, local, prefix), obj.getClass().java, obj)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json, type)
        val element = ToJaxbElement(objJson)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class)
    override fun <T : Any> ToJaxbElement(fullPathNamespaceClass: String, json: String): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json)
        val element = ToJaxbElement(objJson)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(local: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json, type)
        val element = ToJaxbElement(local, objJson)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json, type)
        val element = ToJaxbElement(uri, local, objJson)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, fullPathNamespaceClass: String, json: String, type: Type): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json, type)
        val element = ToJaxbElement(uri, local, prefix, objJson)
        return element
    }

    @SuppressWarnings("static-access")
    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(local: String, fullPathNamespaceClass: String, json: String): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json)
        val element = ToJaxbElement(local, objJson)
        return element
    }

    @SuppressWarnings("static-access")
    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, fullPathNamespaceClass: String, json: String): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json)
        val element = ToJaxbElement(uri, local, objJson)
        return element
    }

    @Throws(JAXBException::class, ClassNotFoundException::class)
    override fun <T : Any> ToJaxbElement(uri: String, local: String, prefix: String, fullPathNamespaceClass: String, json: String): JAXBElement<T> {
        val objJson = this.getClassWithFullNameObject<T>(fullPathNamespaceClass, json)
        val element = ToJaxbElement(uri, local, prefix, objJson)
        return element
    }

    @SuppressWarnings("rawtypes")
    @Throws(ClassNotFoundException::class)
    private fun <T> getClassWithFullNameObject(fullPathNamespaceClass: String, json: String): T {
        val obj = Class.forName(fullPathNamespaceClass)
        val objJson = gson.fromJson(json, obj) as T
        return objJson
    }

    @SuppressWarnings("rawtypes", "unused")
    @Throws(ClassNotFoundException::class)
    private fun <T> getClassWithFullNameObject(fullPathNamespaceClass: String, json: String, type: Type): T {
        val obj = Class.forName(fullPathNamespaceClass)
        val objJson = gson.fromJson<T>(json, type)
        return objJson
    }
}


