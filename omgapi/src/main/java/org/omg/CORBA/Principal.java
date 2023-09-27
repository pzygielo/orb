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
 * A class that contains information about the identity of
 * the client, for access control
 * and other purposes. It contains a single attribute, the name of the
 * <code>Principal</code>, encoded as a sequence of bytes.
 * <p>
 *
 * @deprecated Deprecated by CORBA 2.2.
 */

// @Deprecated
public class Principal {
    /**
     * Sets the name of this <code>Principal</code> object to the given value.
     *
     * @param value the value to be set in the <code>Principal</code>
     * @deprecated Deprecated by CORBA 2.2.
     */
    // @Deprecated
    public void name(byte[] value) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Gets the name of this <code>Principal</code> object.
     *
     * @return the name of this <code>Principal</code> object
     * @deprecated Deprecated by CORBA 2.2.
     */
    // @Deprecated
    public byte[] name() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
