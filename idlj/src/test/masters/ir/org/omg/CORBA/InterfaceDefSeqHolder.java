package org.omg.CORBA;


/**
* org/omg/CORBA/InterfaceDefSeqHolder.java .
* IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
* IGNORE Sunday, January 21, 2018 1:54:22 PM EST
*/

public final class InterfaceDefSeqHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.CORBA.InterfaceDef value[] = null;

  public InterfaceDefSeqHolder ()
  {
  }

  public InterfaceDefSeqHolder (org.omg.CORBA.InterfaceDef[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.CORBA.InterfaceDefSeqHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.CORBA.InterfaceDefSeqHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.CORBA.InterfaceDefSeqHelper.type ();
  }

}
