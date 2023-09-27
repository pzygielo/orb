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

import java.io.*;
import java.math.*;

public class TestObjectSuperSub extends TestObjectSuper {
    public int dataxss0;
    public Long dataxss1;

    private transient BigInteger optData;

    public static final int INITIAL_DATAXSS0 = 256;
    public static final Long INITIAL_DATAXSS1 = new Long(128000L);

    private static final BigInteger INITIAL_BIGINT = new BigInteger("209487109248102948109248102948124");

    private static final long serialVersionUID = -2547646174414134925L;

    public TestObjectSuperSub() {
        dataxss0 = 256;
        dataxss1 = new Long(128000L);

        optData = INITIAL_BIGINT;
    }

    public boolean testObjectSuperSubHasStreamDefaults() {
        return dataxss0 == 0 && dataxss1 == null;
    }

    public boolean equals(Object obj) {
        try {
            TestObjectSuperSub other = (TestObjectSuperSub) obj;

            if (other == null) {
                return false;
            }

            return (testObjectSuperSubHasStreamDefaults() ||
                    other.testObjectSuperSubHasStreamDefaults() ||
                    (dataxss0 == other.dataxss0 &&
                            dataxss1.equals(other.dataxss1))) &&
                    (other.optData == null || optData.equals(INITIAL_BIGINT)) &&
                    super.equals(obj);
        } catch (ClassCastException cce) {
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject();

        out.writeObject(optData);
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        in.defaultReadObject();

        try {
            optData = (BigInteger) in.readObject();
        } catch (OptionalDataException ode) {
            optData = null;
        }
    }
}
