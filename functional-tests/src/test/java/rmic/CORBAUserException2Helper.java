/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
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

package rmic;

/**
 * rmic/CORBAUserException2Helper.java
 * Generated by the IBM IDL-to-Java compiler (portable), version "3.0"
 * from cue.idl
 * Monday, May 10, 1999 12:31:05 o'clock PM PDT
 */

public final class CORBAUserException2Helper {
    private static String _id = "IDL:rmic/CORBAUserException2:1.0";

    public CORBAUserException2Helper() {
    }

    public static void insert(org.omg.CORBA.Any a, rmic.CORBAUserException2 that) {
        org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
        a.type(type());
        write(out, that);
        a.read_value(out.create_input_stream(), type());
    }

    public static rmic.CORBAUserException2 extract(org.omg.CORBA.Any a) {
        return read(a.create_input_stream());
    }

    private static org.omg.CORBA.TypeCode __typeCode = null;
    private static boolean __active = false;

    synchronized public static org.omg.CORBA.TypeCode type() {
        if (__typeCode == null) {
            synchronized (org.omg.CORBA.TypeCode.class) {
                if (__typeCode == null) {
                    if (__active) {
                        return org.omg.CORBA.ORB.init().create_recursive_tc(_id);
                    }
                    __active = true;
                    org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[0];
                    org.omg.CORBA.TypeCode _tcOf_members0 = null;
                    __typeCode = org.omg.CORBA.ORB.init().create_struct_tc(rmic.CORBAUserException2Helper.id(), "CORBAUserException2", _members0);
                    __active = false;
                }
            }
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static rmic.CORBAUserException2 read(org.omg.CORBA.portable.InputStream istream) {
        rmic.CORBAUserException2 value = new rmic.CORBAUserException2();
        // read and discard the repository ID
        istream.read_string();
        return value;
    }

    public static void write(org.omg.CORBA.portable.OutputStream ostream, rmic.CORBAUserException2 value) {
        // write the repository ID
        ostream.write_string(id());
    }

}
