package org.glassfish.idlj;

/**
 * org/glassfish/idlj/LongSeqHolder.java .
 * Generated by the IDL-to-Java compiler (portable), version "4.1"
 * from /Users/rgold/projects/glassfish/glassfish-corba/idlj/src/main/idl/org/glassfish/idlj/CORBAServerTest.idl
 * Monday, January 29, 2018 11:19:41 AM EST
 */

public final class LongSeqHolder implements org.omg.CORBA.portable.Streamable {
    public int value[] = null;

    public LongSeqHolder() {
    }

    public LongSeqHolder(int[] initialValue) {
        value = initialValue;
    }

    public void _read(org.omg.CORBA.portable.InputStream i) {
        value = org.glassfish.idlj.LongSeqHelper.read(i);
    }

    public void _write(org.omg.CORBA.portable.OutputStream o) {
        org.glassfish.idlj.LongSeqHelper.write(o, value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return org.glassfish.idlj.LongSeqHelper.type();
    }

}
