package org.omg.CORBA;

/**
 * org/omg/CORBA/ModuleDescription.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:23 PM EST
 */

public final class ModuleDescription implements org.omg.CORBA.portable.IDLEntity {
    public String name = null;
    public String id = null;
    public String defined_in = null;
    public String version = null;

    public ModuleDescription() {
    } // ctor

    public ModuleDescription(String _name, String _id, String _defined_in, String _version) {
        name = _name;
        id = _id;
        defined_in = _defined_in;
        version = _version;
    } // ctor

} // class ModuleDescription
