package org.omg.CORBA;

/**
 * org/omg/CORBA/ArrayDefHelper.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:23 PM EST
 */

abstract public class ArrayDefHelper {
    private static String _id = "IDL:omg.org/CORBA/ArrayDef:1.0";

    public static void insert(org.omg.CORBA.Any a, org.omg.CORBA.ArrayDef that) {
        org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
        a.type(type());
        write(out, that);
        a.read_value(out.create_input_stream(), type());
    }

    public static org.omg.CORBA.ArrayDef extract(org.omg.CORBA.Any a) {
        return read(a.create_input_stream());
    }

    private static org.omg.CORBA.TypeCode __typeCode = null;

    synchronized public static org.omg.CORBA.TypeCode type() {
        if (__typeCode == null) {
            __typeCode = org.omg.CORBA.ORB.init().create_interface_tc(org.omg.CORBA.ArrayDefHelper.id(), "ArrayDef");
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static org.omg.CORBA.ArrayDef read(org.omg.CORBA.portable.InputStream istream) {
        return narrow(istream.read_Object(_ArrayDefStub.class));
    }

    public static void write(org.omg.CORBA.portable.OutputStream ostream, org.omg.CORBA.ArrayDef value) {
        ostream.write_Object((org.omg.CORBA.Object) value);
    }

    public static org.omg.CORBA.ArrayDef narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof org.omg.CORBA.ArrayDef) {
            return (org.omg.CORBA.ArrayDef) obj;
        } else if (!obj._is_a(id())) {
            throw new org.omg.CORBA.BAD_PARAM();
        } else {
            org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
            org.omg.CORBA._ArrayDefStub stub = new org.omg.CORBA._ArrayDefStub();
            stub._set_delegate(delegate);
            return stub;
        }
    }

    public static org.omg.CORBA.ArrayDef unchecked_narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof org.omg.CORBA.ArrayDef) {
            return (org.omg.CORBA.ArrayDef) obj;
        } else {
            org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
            org.omg.CORBA._ArrayDefStub stub = new org.omg.CORBA._ArrayDefStub();
            stub._set_delegate(delegate);
            return stub;
        }
    }

}
