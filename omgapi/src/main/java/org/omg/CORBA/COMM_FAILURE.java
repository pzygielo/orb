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
 * This exception is raised if communication is lost while an operation
 * is in progress, after the request was sent by the client, but before
 * the reply from the server has been returned to the client.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <p>
 * See the section <A href="../../../../guide/idl/jidlExceptions.html#minorcodemeanings">meaning
 * of minor codes</A> to see the minor codes for this exception.
 *
 * @version 1.17, 09/09/97
 * @see <A href="../../../../guide/idl/jidlExceptions.html#minorcodemeanings">meaning of
 * minor codes</A>
 * @since JDK1.2
 */

public final class COMM_FAILURE extends SystemException {

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with
     * a default minor code of 0 and a completion state of COMPLETED_NO.
     */
    public COMM_FAILURE() {
        this("");
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     *
     * @param s the <code>String</code> containing a detail message describing
     * this exception
     */
    public COMM_FAILURE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified
     * minor code and completion status.
     *
     * @param minor the minor code
     * @param completed the completion status, which must be one of
     * <code>COMPLETED_YES</code>, <code>COMPLETED_NO</code>, or
     * <code>COMPLETED_MAYBE</code>.
     */
    public COMM_FAILURE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     *
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status, which must be one of
     * <code>COMPLETED_YES</code>, <code>COMPLETED_NO</code>, or
     * <code>COMPLETED_MAYBE</code>.
     */
    public COMM_FAILURE(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
