package org.omg.CORBA;

/**
 * org/omg/CORBA/ConstantDefHolder.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:23 PM EST
 */

public final class ConstantDefHolder implements org.omg.CORBA.portable.Streamable {
    public org.omg.CORBA.ConstantDef value = null;

    public ConstantDefHolder() {
    }

    public ConstantDefHolder(org.omg.CORBA.ConstantDef initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = org.omg.CORBA.ConstantDefHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        org.omg.CORBA.ConstantDefHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.ConstantDefHelper.type();
    }

}
