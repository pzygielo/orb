package org.omg.CORBA;

/**
* org/omg/CORBA/AttributeDefHolder.java .
* IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
* IGNORE Sunday, January 21, 2018 1:54:23 PM EST
*/

public final class AttributeDefHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.CORBA.AttributeDef value = null;

  public AttributeDefHolder ()
  {
  }

  public AttributeDefHolder (org.omg.CORBA.AttributeDef initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.CORBA.AttributeDefHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.CORBA.AttributeDefHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.CORBA.AttributeDefHelper.type ();
  }

}