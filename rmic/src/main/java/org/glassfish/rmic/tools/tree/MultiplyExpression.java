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

import org.glassfish.rmic.tools.asm.Assembler;
import org.glassfish.rmic.tools.java.Environment;

/**
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public
class MultiplyExpression extends BinaryArithmeticExpression {
    /**
     * constructor
     */
    public MultiplyExpression(long where, Expression left, Expression right) {
        super(MUL, where, left, right);
    }

    /**
     * Evaluate
     */
    Expression eval(int a, int b) {
        return new IntExpression(where, a * b);
    }

    Expression eval(long a, long b) {
        return new LongExpression(where, a * b);
    }

    Expression eval(float a, float b) {
        return new FloatExpression(where, a * b);
    }

    Expression eval(double a, double b) {
        return new DoubleExpression(where, a * b);
    }

    /**
     * Simplify
     */
    Expression simplify() {
        if (left.equals(1)) {
            return right;
        }
        if (right.equals(1)) {
            return left;
        }
        return this;
    }

    /**
     * Code
     */
    void codeOperation(Environment env, Context ctx, Assembler asm) {
        asm.add(where, opc_imul + type.getTypeCodeOffset());
    }
}
