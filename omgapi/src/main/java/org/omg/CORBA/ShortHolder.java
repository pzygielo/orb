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

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

/**
 * The Holder for <tt>Short</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>short</code>
 * that is used to store "out" and "inout" parameters in IDL operations.
 * If an IDL operation signature has an IDL <code>short</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>ShortHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * </P>
 * If <code>myShortHolder</code> is an instance of <code>ShortHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myShortHolder.value</code>.
 *
 * @version 1.14, 09/09/97
 * @since JDK1.2
 */
public final class ShortHolder implements Streamable {

    /**
     * The <code>short</code> value held by this <code>ShortHolder</code>
     * object.
     */
    public short value;

    /**
     * Constructs a new <code>ShortHolder</code> object with its
     * <code>value</code> field initialized to <code>0</code>.
     */
    public ShortHolder() {
    }

    /**
     * Constructs a new <code>ShortHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>short</code>.
     *
     * @param initial the <code>short</code> with which to initialize
     * the <code>value</code> field of the newly-created
     * <code>ShortHolder</code> object
     */
    public ShortHolder(short initial) {
        value = initial;
    }

    /**
     * Reads from <code>input</code> and initalizes the value in
     * this <code>ShortHolder</code> object
     * with the unmarshalled data.
     *
     * @param input the InputStream containing CDR formatted data from the wire.
     */
    public void _read(InputStream input) {
        value = input.read_short();
    }

    /**
     * Marshals to <code>output</code> the value in
     * this <code>ShortHolder</code> object.
     *
     * @param output the OutputStream which will contain the CDR formatted data.
     */
    public void _write(OutputStream output) {
        output.write_short(value);
    }

    /**
     * Returns the TypeCode corresponding to the value held in
     * this <code>ShortHolder</code> object.
     *
     * @return the TypeCode of the value held in
     * this <code>ShortHolder</code> object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_short);
    }
}
