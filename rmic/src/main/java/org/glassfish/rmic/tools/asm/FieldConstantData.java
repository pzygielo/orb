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

package org.glassfish.rmic.tools.asm;

import org.glassfish.rmic.tools.java.Environment;
import org.glassfish.rmic.tools.java.MemberDefinition;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This is a field constant pool data item
 * <p>
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
final
class FieldConstantData extends ConstantPoolData {
    MemberDefinition field;
    NameAndTypeData nt;

    /**
     * Constructor
     */
    FieldConstantData(ConstantPool tab, MemberDefinition field) {
        this.field = field;
        nt = new NameAndTypeData(field);
        tab.put(field.getClassDeclaration());
        tab.put(nt);
    }

    /**
     * Write the constant to the output stream
     */
    void write(Environment env, DataOutputStream out, ConstantPool tab) throws IOException {
        if (field.isMethod()) {
            if (field.getClassDefinition().isInterface()) {
                out.writeByte(CONSTANT_INTERFACEMETHOD);
            } else {
                out.writeByte(CONSTANT_METHOD);
            }
        } else {
            out.writeByte(CONSTANT_FIELD);
        }
        out.writeShort(tab.index(field.getClassDeclaration()));
        out.writeShort(tab.index(nt));
    }

    /**
     * Return the order of the constant
     */
    int order() {
        return 2;
    }
}
