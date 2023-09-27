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

package org.omg.CORBA.portable;

import java.io.Serializable;

/**
 * The ValueFactory interface is the native mapping for the IDL
 * type CORBA::ValueFactory. The read_value() method is called by
 * the ORB runtime while in the process of unmarshaling a value type.
 * A user shall implement this method as part of implementing a type
 * specific value factory. In the implementation, the user shall call
 * is.read_value(java.io.Serializable) with a uninitialized valuetype
 * to use for unmarshaling. The value returned by the stream is
 * the same value passed in, with all the data unmarshaled.
 *
 * @see org.omg.CORBA_2_3.ORB
 */

public interface ValueFactory {
    /**
     * Is called by
     * the ORB runtime while in the process of unmarshaling a value type.
     * A user shall implement this method as part of implementing a type
     * specific value factory.
     *
     * @param is an InputStream object--from which the value will be read.
     * @return a Serializable object--the value read off of "is" Input stream.
     */
    Serializable read_value(org.omg.CORBA_2_3.portable.InputStream is);
}
