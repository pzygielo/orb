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

import com.sun.tools.corba.ee.idl.*;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 **/
public class AttributeGen extends MethodGen implements com.sun.tools.corba.ee.idl.AttributeGen {
    /**
     * Public zero-argument constructor.
     **/
    public AttributeGen() {
    } // ctor

    /**
     *
     **/
    private boolean unique(InterfaceEntry entry, String name) {
        // Compare the name to the methods of this interface
        Enumeration methods = entry.methods().elements();
        while (methods.hasMoreElements()) {
            SymtabEntry method = (SymtabEntry) methods.nextElement();
            if (name.equals(method.name())) {
                return false;
            }
        }

        // Recursively call unique on each derivedFrom interface
        Enumeration derivedFrom = entry.derivedFrom().elements();
        while (derivedFrom.hasMoreElements()) {
            if (!unique((InterfaceEntry) derivedFrom.nextElement(), name)) {
                return false;
            }
        }

        // If the name isn't in any method, nor in any method of the
        // derivedFrom interfaces, then the name is unique.
        return true;
    } // unique

    /**
     * Method generate() is not used in MethodGen.  They are replaced by the
     * more granular interfaceMethod, stub, skeleton, dispatchSkeleton.
     **/
    public void generate(Hashtable symbolTable, AttributeEntry m, PrintWriter stream) {
    } // generate

    @Override
    protected void interfaceMethod(Hashtable symbolTable, MethodEntry m, PrintWriter stream) {
        AttributeEntry a = (AttributeEntry) m;

        // Generate for the get method
        super.interfaceMethod(symbolTable, a, stream);

        // Generate for the set method if the attribute is not readonly
        if (!a.readOnly()) {
            setupForSetMethod();
            super.interfaceMethod(symbolTable, a, stream);
            clear();
        }
    } // interfaceMethod

    /**
     *
     **/
    protected void stub(String className, boolean isAbstract, Hashtable symbolTable, MethodEntry m, PrintWriter stream, int index) {
        AttributeEntry a = (AttributeEntry) m;

        // Generate for the get method
        super.stub(className, isAbstract, symbolTable, a, stream, index);

        // Generate for the set method if the attribute is not readonly
        if (!a.readOnly()) {
            setupForSetMethod();
            super.stub(className, isAbstract, symbolTable, a, stream, index + 1);
            clear();
        }
    } // stub

    /**
     *
     **/
    protected void skeleton(Hashtable symbolTable, MethodEntry m, PrintWriter stream, int index) {
        AttributeEntry a = (AttributeEntry) m;

        // Generate for the get method
        super.skeleton(symbolTable, a, stream, index);

        // Generate for the set method if the attribute is not readonly
        if (!a.readOnly()) {
            setupForSetMethod();
            super.skeleton(symbolTable, a, stream, index + 1);
            clear();
        }
    } // skeleton

    /**
     *
     **/
    protected void dispatchSkeleton(Hashtable symbolTable, MethodEntry m, PrintWriter stream, int index) {
        AttributeEntry a = (AttributeEntry) m;

        // Generate for the get method
        super.dispatchSkeleton(symbolTable, a, stream, index);

        // Generate for the set method if the attribute is not readonly
        if (!a.readOnly()) {
            setupForSetMethod();
            super.dispatchSkeleton(symbolTable, m, stream, index + 1);
            clear();
        }
    } // dispatchSkeleton

    private SymtabEntry realType = null;

    /**
     *
     **/
    protected void setupForSetMethod() {
        ParameterEntry parm = com.sun.tools.corba.ee.idl.toJavaPortable.Compile.compiler.factory.parameterEntry();
        parm.type(m.type());
        parm.name("new" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.capitalize(m.name()));
        m.parameters().addElement(parm);
        realType = m.type();
        m.type(null);
    } // setupForSetMethod

    /**
     *
     **/
    protected void clear() {
        // Set back to normal
        m.parameters().removeAllElements();
        m.type(realType);
    } // clear
} // class AttributeGen
