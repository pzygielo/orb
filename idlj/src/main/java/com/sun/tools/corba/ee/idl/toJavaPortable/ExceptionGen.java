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

import com.sun.tools.corba.ee.idl.ExceptionEntry;

import java.io.PrintWriter;
import java.util.Hashtable;

/**
 *
 **/
public class ExceptionGen extends StructGen implements com.sun.tools.corba.ee.idl.ExceptionGen {
    /**
     * Public zero-argument constructor.
     **/
    public ExceptionGen() {
        super(true);
    } // ctor

    /**
     *
     **/
    public void generate(Hashtable symbolTable, ExceptionEntry entry, PrintWriter stream) {
        super.generate(symbolTable, entry, stream);
    } // generate
} // class ExceptionGen
