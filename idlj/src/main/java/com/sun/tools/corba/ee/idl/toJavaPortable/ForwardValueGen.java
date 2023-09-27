/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 * Copyright (c) 1997-1999 IBM Corp. All rights reserved.
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
// -D61056   <klr> Use Util.helperName

import com.sun.tools.corba.ee.idl.ForwardValueEntry;
import com.sun.tools.corba.ee.idl.GenFileStream;
import com.sun.tools.corba.ee.idl.SymtabEntry;

import java.io.PrintWriter;
import java.util.Hashtable;

/**
 *
 **/
public class ForwardValueGen implements com.sun.tools.corba.ee.idl.ForwardValueGen, JavaGenerator {
    /**
     * Public zero-argument constructor.
     **/
    public ForwardValueGen() {
    } // ctor

    /**
     *
     **/
    public void generate(Hashtable symbolTable, ForwardValueEntry v, PrintWriter str) {
        this.symbolTable = symbolTable;
        this.v = v;

        openStream();
        if (stream == null) {
            return;
        }
        generateHelper();
        generateHolder();
        generateStub();
        writeHeading();
        writeBody();
        writeClosing();
        closeStream();
    } // generate

    /**
     *
     **/
    protected void openStream() {
        stream = com.sun.tools.corba.ee.idl.toJavaPortable.Util.stream(v, ".java");
    } // openStream

    /**
     *
     **/
    protected void generateHelper() {
        ((com.sun.tools.corba.ee.idl.toJavaPortable.Factories) com.sun.tools.corba.ee.idl.toJavaPortable.Compile.compiler.factories()).helper()
                .generate(symbolTable, v);
    } // generateHelper

    /**
     *
     **/
    protected void generateHolder() {
        ((com.sun.tools.corba.ee.idl.toJavaPortable.Factories) com.sun.tools.corba.ee.idl.toJavaPortable.Compile.compiler.factories()).holder()
                .generate(symbolTable, v);
    } // generateHolder

    /**
     *
     **/
    protected void generateStub() {
    } // generateStub

    /**
     *
     **/
    protected void writeHeading() {
        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writePackage(stream, v);
        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writeProlog(stream, ((GenFileStream) stream).name());

        if (v.comment() != null) {
            v.comment().generate("", stream);
        }

        stream.print("public class " + v.name() + " implements org.omg.CORBA.portable.IDLEntity");
        // There should ALWAYS be at least one:  ValueBase

        stream.println("{");
    } // writeHeading

    /**
     *
     **/
    protected void writeBody() {
    } // writeBody

    /**
     *
     **/
    protected void writeClosing() {
        stream.println("} // class " + v.name());
    } // writeClosing

    /**
     *
     **/
    protected void closeStream() {
        stream.close();
    } // closeStream

    ///////////////
    // From JavaGenerator

    public int helperType(int index, String indent, com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets tcoffsets, String name, SymtabEntry entry,
                          PrintWriter stream) {
        return index;
    } // helperType

    public int type(int index, String indent, com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets tcoffsets, String name, SymtabEntry entry,
                    PrintWriter stream) {
        stream.println(indent + name + " = " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(entry, true) + ".type ();"); // <d61056>
        return index;
    } // type

    public void helperRead(String entryName, SymtabEntry entry, PrintWriter stream) {
        stream.println("    " + entryName + " value = new " + entryName + " ();");
        read(0, "    ", "value", entry, stream);
        stream.println("    return value;");
    } // helperRead

    public int read(int index, String indent, String name, SymtabEntry entry, PrintWriter stream) {
        return index;
    } // read

    public void helperWrite(SymtabEntry entry, PrintWriter stream) {
        write(0, "    ", "value", entry, stream);
    } // helperWrite

    public int write(int index, String indent, String name, SymtabEntry entry, PrintWriter stream) {
        return index;
    } // write

    // From JavaGenerator
    ///////////////

    /**
     *
     **/
    protected void writeAbstract() {
    } // writeAbstract

    protected Hashtable symbolTable = null;
    protected ForwardValueEntry v = null;
    protected PrintWriter stream = null;
} // class ForwardValueGen
