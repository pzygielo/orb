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

package corba.cdrext;

import java.io.*;

public class TestClass implements Serializable {
    private NestedInnerClass instance;

    public TestClass() {
        instance = new NestedInnerClass();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        try {
            return instance.equals(((TestClass) obj).instance);
        } catch (ClassCastException cce) {
            return false;
        }
    }

    private static class NestedInnerClass implements Externalizable {

        private NestedInnerClass() {
            data = 12344512L;
        }

        private long data;

        public void readExternal(ObjectInput decoder)
                throws IOException, ClassNotFoundException {

            data = decoder.readLong();
        }

        public void writeExternal(ObjectOutput encoder)
                throws IOException {

            encoder.writeLong(data);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            try {
                return data == ((NestedInnerClass) obj).data;
            } catch (ClassCastException cce) {
                return false;
            }
        }
    }
}


