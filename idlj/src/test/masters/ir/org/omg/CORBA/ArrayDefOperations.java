package org.omg.CORBA;


/**
* org/omg/CORBA/ArrayDefOperations.java .
* IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
* IGNORE Sunday, January 21, 2018 1:54:23 PM EST
*/

public interface ArrayDefOperations  extends org.omg.CORBA.IDLTypeOperations
{
  int length ();
  void length (int newLength);
  org.omg.CORBA.TypeCode element_type ();
  org.omg.CORBA.IDLType element_type_def ();
  void element_type_def (org.omg.CORBA.IDLType newElement_type_def);
} // interface ArrayDefOperations
