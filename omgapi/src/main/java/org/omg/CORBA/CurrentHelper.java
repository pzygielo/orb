/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
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

package org.omg.CORBA;

/**
 * The Helper for <tt>Current</tt>.  For more information on
 * Helper files, see <a href="doc-files/generatedfiles.html#helper">
 * "Generated Files: Helper Files"</a>.<P>
 * org/omg/CORBA/CurrentHelper.java
 * Generated by the IDL-to-Java compiler (portable), version "3.0"
 * from ../../../../../src/share/classes/org/omg/PortableServer/corba.idl
 * Saturday, July 17, 1999 12:26:21 AM PDT
 */

abstract public class CurrentHelper {
    private static String _id = "IDL:omg.org/CORBA/Current:1.0";

    public static void insert(org.omg.CORBA.Any a, org.omg.CORBA.Current that) {
        throw new org.omg.CORBA.MARSHAL();
    }

    public static org.omg.CORBA.Current extract(org.omg.CORBA.Any a) {
        throw new org.omg.CORBA.MARSHAL();
    }

    private static org.omg.CORBA.TypeCode __typeCode = null;

    synchronized public static org.omg.CORBA.TypeCode type() {
        if (__typeCode == null) {
            __typeCode = org.omg.CORBA.ORB.init().create_interface_tc(org.omg.CORBA.CurrentHelper.id(), "Current");
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static org.omg.CORBA.Current read(org.omg.CORBA.portable.InputStream istream) {
        throw new org.omg.CORBA.MARSHAL();
    }

    public static void write(org.omg.CORBA.portable.OutputStream ostream, org.omg.CORBA.Current value) {
        throw new org.omg.CORBA.MARSHAL();
    }

    public static org.omg.CORBA.Current narrow(org.omg.CORBA.Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof org.omg.CORBA.Current) {
            return (org.omg.CORBA.Current) obj;
        } else {
            throw new org.omg.CORBA.BAD_PARAM();
        }
    }

}
