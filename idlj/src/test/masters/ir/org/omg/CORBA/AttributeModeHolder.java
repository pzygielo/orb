package org.omg.CORBA;

/**
 * org/omg/CORBA/AttributeModeHolder.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:23 PM EST
 */

public final class AttributeModeHolder implements org.omg.CORBA.portable.Streamable {
    public org.omg.CORBA.AttributeMode value = null;

    public AttributeModeHolder() {
    }

    public AttributeModeHolder(org.omg.CORBA.AttributeMode initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = org.omg.CORBA.AttributeModeHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        org.omg.CORBA.AttributeModeHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.AttributeModeHelper.type();
    }

}
