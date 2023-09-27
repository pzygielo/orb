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
 * <code>TIMEOUT</code> is raised when no delivery has been made and the
 * specified time-to-live period has been exceeded. It is a standard system
 * exception because time-to-live QoS can be applied to any invocation.
 *
 * @version 1.0, 03/05/2004
 * @see <A href="../../../../guide/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since J2SE 1.5
 */

public final class TIMEOUT extends SystemException {

    /**
     * Constructs an <code>TIMEOUT</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     */
    public TIMEOUT() {
        this("");
    }

    /**
     * Constructs an <code>TIMEOUT</code> exception with the
     * specified message.
     *
     * @param detailMessage string containing a detailed message.
     */
    public TIMEOUT(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>TIMEOUT</code> exception with the
     * specified minor code and completion status.
     *
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public TIMEOUT(int minorCode,
                   CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>TIMEOUT</code> exception with the
     * specified message, minor code, and completion status.
     *
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public TIMEOUT(String detailMessage,
                   int minorCode,
                   CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
