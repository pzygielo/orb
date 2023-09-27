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

package org.omg.CORBA.TSIdentificationPackage;

/**
 * A user-defined exception thrown if the ORB is unavailable
 * to register the call-back interfaces identified by the
 * OTS during initialization.
 *
 * @see org.omg.CORBA.TSIdentification
 * @see AlreadyIdentified
 */
public final class NotAvailable
        extends org.omg.CORBA.UserException {

    //  constructor

    /**
     * Constructs a <code>NotAvailable</code> exception.
     */
    public NotAvailable() {
        super();
    }
}

