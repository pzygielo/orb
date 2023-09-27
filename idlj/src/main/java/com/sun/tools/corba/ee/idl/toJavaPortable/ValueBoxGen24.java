/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 1997-1999 IBM Corp. All rights reserved.
 * Copyright (c) 2019 Payara Services Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License v2.0
 * w/Classpath exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause OR GPL-2.0 WITH
 * Classpath-exception-2.0
 */

package com.sun.tools.corba.ee.idl.toJavaPortable;

// NOTES:
// -D62023  <klr> Update for Java 2.4 RTF

import com.sun.tools.corba.ee.idl.*;

import java.io.PrintWriter;
import java.util.Vector;

/**
 *
 **/
public class ValueBoxGen24 extends ValueBoxGen {
    /**
     * Public zero-argument constructor.
     **/
    public ValueBoxGen24() {
    } // ctor

    protected void writeTruncatable() // <d60929>
    {
        stream.print("  private static String[] _truncatable_ids = {");
        stream.println(com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(v, true) + ".id ()};");
        stream.println();
        stream.println("  public String[] _truncatable_ids() {");
        stream.println("    return _truncatable_ids;");
        stream.println("  }");
        stream.println();
    } // writeTruncatable

    @Override
    public void helperRead(String entryName, SymtabEntry entry, PrintWriter stream) {
        stream.println("    if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {");
        stream.println("      throw new org.omg.CORBA.BAD_PARAM(); }");
        stream.println("    return (" + entryName + ") ((org.omg.CORBA_2_3.portable.InputStream) istream).read_value (_instance);");
        stream.println("  }");
        stream.println();

        // done with "read", now do "read_value with real marshalling code.

        stream.println("  public java.io.Serializable read_value (org.omg.CORBA.portable.InputStream istream)"); // <d60929>
        stream.println("  {");

        String indent = "    ";
        Vector<InterfaceState> vMembers = ((ValueBoxEntry) entry).state();
        TypedefEntry member = vMembers.elementAt(0).entry;
        SymtabEntry mType = member.type();
        if (mType instanceof PrimitiveEntry ||
                mType instanceof SequenceEntry ||
                mType instanceof TypedefEntry ||
                mType instanceof StringEntry ||
                !member.arrayInfo().isEmpty()) {
            stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(mType) + " tmp;");
            ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) member.generator()).read(0, indent, "tmp", member, stream);
        } else {
            stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(mType) + " tmp = " +
                                   com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(mType, true) + ".read (istream);");
        }
        if (mType instanceof PrimitiveEntry) {
            stream.println(indent + "return new " + entryName + " (tmp);");
        } else {
            stream.println(indent + "return (java.io.Serializable) tmp;");
        }
    } // helperRead

    public void helperWrite(SymtabEntry entry, PrintWriter stream) {
        stream.println("    if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {");
        stream.println("      throw new org.omg.CORBA.BAD_PARAM(); }");
        stream.println("    ((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value (value, _instance);");
        stream.println("  }");
        stream.println();

        // done with "write", now do "write_value with real marshalling code.

        stream.println("  public void write_value (org.omg.CORBA.portable.OutputStream ostream, java.io.Serializable value)");
        stream.println("  {");

        String entryName = com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(entry);
        stream.println("    if (!(value instanceof " + entryName + ")) {");
        stream.println("      throw new org.omg.CORBA.MARSHAL(); }");
        stream.println("    " + entryName + " valueType = (" + entryName + ") value;");
        write(0, "    ", "valueType", entry, stream);
    } // helperWrite

    @Override
    public int write(int index, String indent, String name, SymtabEntry entry, PrintWriter stream) {
        Vector<InterfaceState> vMembers = ((ValueEntry) entry).state();
        TypedefEntry member = vMembers.elementAt(0).entry;
        SymtabEntry mType = member.type();

        if (mType instanceof PrimitiveEntry || !member.arrayInfo().isEmpty()) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) member.generator()).write(index, indent, name + ".value", member, stream);
        } else if (mType instanceof SequenceEntry || mType instanceof StringEntry || mType instanceof TypedefEntry || !member.arrayInfo().isEmpty()) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) member.generator()).write(index, indent, name, member, stream);
        } else {
            stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(mType, true) + ".write (ostream, " + name + ");"); // <d61056>
        }
        return index;
    } // write
}
