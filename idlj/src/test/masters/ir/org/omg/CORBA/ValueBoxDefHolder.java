package org.omg.CORBA;

/**
 * org/omg/CORBA/ValueBoxDefHolder.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:24 PM EST
 */

public final class ValueBoxDefHolder implements org.omg.CORBA.portable.Streamable {
    public org.omg.CORBA.ValueBoxDef value = null;

    public ValueBoxDefHolder() {
    }

    public ValueBoxDefHolder(org.omg.CORBA.ValueBoxDef initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = org.omg.CORBA.ValueBoxDefHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        org.omg.CORBA.ValueBoxDefHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.ValueBoxDefHelper.type();
    }

}
