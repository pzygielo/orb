package org.omg.CORBA;

/**
 * org/omg/CORBA/ContainedOperations.java .
 * IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
 * IGNORE Sunday, January 21, 2018 1:54:22 PM EST
 */

public interface ContainedOperations extends org.omg.CORBA.IRObjectOperations {

    // read/write interface
    String id();

    // read/write interface
    void id(String newId);

    String name();

    void name(String newName);

    String version();

    void version(String newVersion);

    // read interface
    org.omg.CORBA.Container defined_in();

    String absolute_name();

    org.omg.CORBA.Repository containing_repository();

    org.omg.CORBA.ContainedPackage.Description describe();

    // write interface
    void move(org.omg.CORBA.Container new_container, String new_name, String new_version);
} // interface ContainedOperations
