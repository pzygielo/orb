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

import org.glassfish.rmic.tools.java.*;
import org.glassfish.rmic.tools.tree.StringExpression;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A table of constants
 * <p>
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public final
class ConstantPool implements RuntimeConstants {
    Hashtable<Object, ConstantPoolData> hash = new Hashtable<>(101);

    /**
     * Find an entry, may return 0
     */
    public int index(Object obj) {
        return hash.get(obj).index;
    }

    /**
     * Add an entry
     */
    public void put(Object obj) {
        ConstantPoolData data = hash.get(obj);
        if (data == null) {
            if (obj instanceof String) {
                data = new StringConstantData(this, (String) obj);
            } else if (obj instanceof StringExpression) {
                data = new StringExpressionConstantData(this, (StringExpression) obj);
            } else if (obj instanceof ClassDeclaration) {
                data = new ClassConstantData(this, (ClassDeclaration) obj);
            } else if (obj instanceof Type) {
                data = new ClassConstantData(this, (Type) obj);
            } else if (obj instanceof MemberDefinition) {
                data = new FieldConstantData(this, (MemberDefinition) obj);
            } else if (obj instanceof NameAndTypeData) {
                data = new NameAndTypeConstantData(this, (NameAndTypeData) obj);
            } else if (obj instanceof Number) {
                data = new NumberConstantData(this, (Number) obj);
            }
            hash.put(obj, data);
        }
    }

    /**
     * Write to output
     */
    public void write(Environment env, DataOutputStream out) throws IOException {
        ConstantPoolData list[] = new ConstantPoolData[hash.size()];
        String keys[] = new String[list.length];
        int index = 1, count = 0;

        // Make a list of all the constant pool items
        for (int n = 0; n < 5; n++) {
            int first = count;
            for (Enumeration<ConstantPoolData> e = hash.elements(); e.hasMoreElements(); ) {
                ConstantPoolData data = e.nextElement();
                if (data.order() == n) {
                    keys[count] = sortKey(data);
                    list[count++] = data;
                }
            }
            xsort(list, keys, first, count - 1);
        }

        // Assign an index to each constant pool item
        for (int n = 0; n < list.length; n++) {
            ConstantPoolData data = list[n];
            data.index = index;
            index += data.width();
        }

        // Write length
        out.writeShort(index);

        // Write each constant pool item
        for (int n = 0; n < count; n++) {
            list[n].write(env, out, this);
        }
    }

    private
    static String sortKey(ConstantPoolData f) {
        if (f instanceof NumberConstantData) {
            Number num = ((NumberConstantData) f).num;
            String str = num.toString();
            int key = 3;
            if (num instanceof Integer) {
                key = 0;
            } else if (num instanceof Float) {
                key = 1;
            } else if (num instanceof Long) {
                key = 2;
            }
            return "\0" + (char) (str.length() + key << 8) + str;
        }
        if (f instanceof StringExpressionConstantData) {
            return (String) ((StringExpressionConstantData) f).str.getValue();
        }
        if (f instanceof FieldConstantData) {
            MemberDefinition fd = ((FieldConstantData) f).field;
            return fd.getName() + " " + fd.getType().getTypeSignature()
                    + " " + fd.getClassDeclaration().getName();
        }
        if (f instanceof NameAndTypeConstantData) {
            return ((NameAndTypeConstantData) f).name +
                    " " + ((NameAndTypeConstantData) f).type;
        }
        if (f instanceof ClassConstantData) {
            return ((ClassConstantData) f).name;
        }
        return ((StringConstantData) f).str;
    }

    /**
     * Quick sort an array of pool entries and a corresponding array of Strings
     * that are the sort keys for the field.
     */
    private
    static void xsort(ConstantPoolData ff[], String ss[], int left, int right) {
        if (left >= right) {
            return;
        }
        String pivot = ss[left];
        int l = left;
        int r = right;
        while (l < r) {
            while (l <= right && ss[l].compareTo(pivot) <= 0) {
                l++;
            }
            while (r >= left && ss[r].compareTo(pivot) > 0) {
                r--;
            }
            if (l < r) {
                // swap items at l and at r
                ConstantPoolData def = ff[l];
                String name = ss[l];
                ff[l] = ff[r];
                ff[r] = def;
                ss[l] = ss[r];
                ss[r] = name;
            }
        }
        int middle = r;
        // swap left and middle
        ConstantPoolData def = ff[left];
        String name = ss[left];
        ff[left] = ff[middle];
        ff[middle] = def;
        ss[left] = ss[middle];
        ss[middle] = name;
        xsort(ff, ss, left, middle - 1);
        xsort(ff, ss, middle + 1, right);
    }

}
