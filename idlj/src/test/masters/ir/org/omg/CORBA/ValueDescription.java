package org.omg.CORBA;

/**
 * org/omg/CORBA/ValueDescription.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:24 PM EST
 */

public final class ValueDescription implements org.omg.CORBA.portable.IDLEntity {
    public String name = null;
    public String id = null;
    public boolean is_abstract = false;
    public boolean is_custom = false;
    public byte flags = (byte) 0;

    // always 0
    public String defined_in = null;
    public String version = null;
    public String supported_interfaces[] = null;
    public String abstract_base_values[] = null;
    public boolean has_safe_base = false;
    public String base_value = null;

    public ValueDescription() {
    } // ctor

    public ValueDescription(String _name, String _id, boolean _is_abstract, boolean _is_custom, byte _flags, String _defined_in, String _version,
                            String[] _supported_interfaces, String[] _abstract_base_values, boolean _has_safe_base, String _base_value) {
        name = _name;
        id = _id;
        is_abstract = _is_abstract;
        is_custom = _is_custom;
        flags = _flags;
        defined_in = _defined_in;
        version = _version;
        supported_interfaces = _supported_interfaces;
        abstract_base_values = _abstract_base_values;
        has_safe_base = _has_safe_base;
        base_value = _base_value;
    } // ctor

} // class ValueDescription
