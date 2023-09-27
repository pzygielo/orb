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
 * Exception  thrown
 * when a parameter passed to a call is out of range or
 * otherwise considered illegal. An ORB may raise this exception
 * if null values or null pointers are passed to an operation (for
 * language mappings where the concept of a null pointers or null
 * values applies). BAD_PARAM can also be raised as a result of a
 * client generating requests with incorrect parameters using the DII. <P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * @version 1.18, 09/09/97
 * @see <A href="../../../../guide/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @see <A href="../../../../guide/idl/jidlExceptions.html#minorcodemeanings">meaning of
 * minor codes</A>
 * @since JDK1.2
 */

// @SuppressWarnings({"serial"})
public final class BAD_PARAM extends SystemException {

    /**
     * Constructs a <code>BAD_PARAM</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     */
    public BAD_PARAM() {
        this("");
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     *
     * @param s the String containing a detail message describing this
     * exception
     */
    public BAD_PARAM(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified
     * minor code and completion status.
     *
     * @param minor the minor code
     * @param completed the completion status
     */
    public BAD_PARAM(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a <code>String</code> that describes
     * this particular exception.
     *
     * @param s the <code>String</code> containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    public BAD_PARAM(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
