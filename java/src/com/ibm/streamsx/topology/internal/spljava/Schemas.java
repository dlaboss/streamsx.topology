/*
# Licensed Materials - Property of IBM
# Copyright IBM Corp. 2015  
 */
package com.ibm.streamsx.topology.internal.spljava;

import static com.ibm.streams.operator.Type.Factory.getStreamSchema;

import com.ibm.streams.operator.StreamSchema;
import com.ibm.streams.operator.types.Blob;
import com.ibm.streams.operator.types.XML;

public class Schemas {

    public static final StreamSchema STRING = getStreamSchema("tuple<rstring string>");
    public static final StreamSchema BLOB = getStreamSchema("tuple<blob binary>");
    public static final StreamSchema XML = getStreamSchema("tuple<xml document>");
    public static final StreamSchema JAVA_OBJECT = getStreamSchema("tuple<blob " + SPLJavaObject.SPL_JAVA_OBJECT + ">");
    

    public StreamSchema getSPLSchema(Class<?> tupleClass) {

        if (String.class.equals(tupleClass))
            return STRING;
        if (Blob.class.equals(tupleClass))
            return BLOB;
        if (XML.class.equals(tupleClass))
            return XML;

        throw new IllegalArgumentException(tupleClass.getName());
    }

    @SuppressWarnings("unchecked")
    public static <T> SPLMapping<T> getSPLMapping(Class<T> tupleClass) {

        if (String.class.equals(tupleClass)) {
            return (SPLMapping<T>) SPLMapping.JavaString;
        }
        if (Blob.class.equals(tupleClass)) {
            return (SPLMapping<T>) SPLMapping.JavaBlob;
        }
        if (XML.class.equals(tupleClass)) {
            return (SPLMapping<T>) SPLMapping.JavaXML;
        }

        return SPLJavaObject.createMappping(tupleClass);
    }

    public static SPLMapping<?> getSPLMapping(StreamSchema schema) {

        if (STRING.equals(schema)) {
            return SPLMapping.JavaString;
        }
        if (JAVA_OBJECT.equals(schema)) {
            return new SPLJavaObject<Object>(schema, Object.class);
        }
        if (BLOB.equals(schema)) {
            return SPLMapping.JavaBlob;
        }
        if (XML.equals(schema)) {
            return SPLMapping.JavaXML;
        }

        return new SPLTuple(schema);
    }

}
