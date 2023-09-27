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
 * The CORBA <code>TRANSACTION_UNAVAILABLE</code> exception is thrown
 * by the ORB when it cannot process a transaction service context because
 * its connection to the Transaction Service has been abnormally terminated.
 * <p>
 * It contains a minor code, which gives information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * The OMG CORBA core 2.4 specification has details.
 *
 * @version 1.0 10/22/2000
 * @see <A href="../../../../guide/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSACTION_UNAVAILABLE extends SystemException {
    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception
     * with a default minor code of 0, a completion state of
     * CompletionStatus.COMPLETED_NO, and a null description.
     */
    public TRANSACTION_UNAVAILABLE() {
        this("");
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specifieddescription message, a minor code of 0, and a completion state
     * of COMPLETED_NO.
     *
     * @param s the String containing a detail message
     */
    public TRANSACTION_UNAVAILABLE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specified minor code and completion status.
     *
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_UNAVAILABLE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specified description message, minor code, and completion status.
     *
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_UNAVAILABLE(String s, int minor,
                                   CompletionStatus completed) {
        super(s, minor, completed);
    }
}
