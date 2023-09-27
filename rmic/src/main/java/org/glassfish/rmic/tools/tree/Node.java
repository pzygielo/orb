/*
 * Copyright (c) 1994, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.rmic.tools.tree;

import org.glassfish.rmic.tools.java.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public
class Node implements Constants, Cloneable {
    int op;
    long where;

    /**
     * Constructor
     */
    Node(int op, long where) {
        this.op = op;
        this.where = where;
    }

    /**
     * Get the operator
     */
    public int getOp() {
        return op;
    }

    /**
     * Get where
     */
    public long getWhere() {
        return where;
    }

    /**
     * Implicit conversions
     */
    public Expression convert(Environment env, Context ctx, Type t, Expression e) {
        if (e.type.isType(TC_ERROR) || t.isType(TC_ERROR)) {
            // An error was already reported
            return e;
        }

        if (e.type.equals(t)) {
            // The types are already the same
            return e;
        }

        try {
            if (e.fitsType(env, ctx, t)) {
                return new ConvertExpression(where, t, e);
            }

            if (env.explicitCast(e.type, t)) {
                env.error(where, "explicit.cast.needed", opNames[op], e.type, t);
                return new ConvertExpression(where, t, e);
            }
        } catch (ClassNotFound ee) {
            env.error(where, "class.not.found", ee.name, opNames[op]);
        }

        // The cast is not allowed
        env.error(where, "incompatible.type", opNames[op], e.type, t);
        return new ConvertExpression(where, Type.tError, e);
    }

    /**
     * Print
     */
    public void print(PrintStream out) {
        throw new CompilerError("print");
    }

    /**
     * Clone this object.
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw (InternalError) new InternalError().initCause(e);
        }
    }

    /*
     * Useful for simple debugging
     */
    public String toString() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        print(new PrintStream(bos));
        return bos.toString();
    }

}
