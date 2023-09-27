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

import java.lang.String;

public class ObjectByValue implements java.io.Serializable {

    public static int foo = 1;
    public static final int FOO = 3;

    int value1;
    int value2;
    private String str1;
    String str2;
    private transient PrivateValue1 transientPrivate = null;
    private static PrivateValue2 staticPrivate = new PrivateValue2();
    private static final PrivateValue3 constantPrivate = new PrivateValue3();

    //private PrivateValue4 memberPrivate; // Should not be allowed by rmic.

    public ObjectByValue(int val1, int val2, String str1, String str2) {
        this.value1 = val1;
        this.value2 = val2;
        this.str1 = str1;
        this.str2 = str2;
    }

    public String toString() {
        return "{" + value1 + ", " + value2 + ", " + str1 + ", " + str2 + "}";
    }

    public boolean equals(Object right) {
        if (right instanceof ObjectByValue) {
            ObjectByValue other = (ObjectByValue) right;
            return value1 == other.value1 &&
                    value2 == other.value2 &&
                    str1.equals(other.str1) &&
                    str2.equals(other.str2);
        } else {
            return false;
        }
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public String getString1() {
        return str1;
    }

    public String getString2() {
        return str2;
    }

    public synchronized void checkPrivate(PrivateValue1 it) {
    }
}

class PrivateValue1 implements java.io.Serializable {
    private int value = 1;
}

class PrivateValue2 implements java.io.Serializable {
    private int value = 2;
}

class PrivateValue3 implements java.io.Serializable {
    private int value = 3;
}

class PrivateValue4 implements java.io.Serializable {
    private int value = 4;
}
