package org.omg.CORBA;

/**
 * org/omg/CORBA/InitializerHolder.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:22 PM EST
 */

public final class InitializerHolder implements org.omg.CORBA.portable.Streamable {
    public org.omg.CORBA.Initializer value = null;

    public InitializerHolder() {
    }

    public InitializerHolder(org.omg.CORBA.Initializer initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = org.omg.CORBA.InitializerHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        org.omg.CORBA.InitializerHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.InitializerHelper.type();
    }

}
