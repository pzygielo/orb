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

package com.sun.corba.ee.spi.extension;

import com.sun.corba.ee.spi.misc.ORBConstants;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;

/**
 * Policy used to implement zero IIOP port policy in the POA.
 */
public class ZeroPortPolicy extends LocalObject implements Policy {
    private static ZeroPortPolicy policy = new ZeroPortPolicy(true);

    private boolean flag = true;

    private ZeroPortPolicy(boolean type) {
        this.flag = type;
    }

    public String toString() {
        return "ZeroPortPolicy[" + flag + "]";
    }

    public boolean forceZeroPort() {
        return flag;
    }

    public synchronized static ZeroPortPolicy getPolicy() {
        return policy;
    }

    public int policy_type() {
        return ORBConstants.ZERO_PORT_POLICY;
    }

    public org.omg.CORBA.Policy copy() {
        return this;
    }

    public void destroy() {
        // NO-OP
    }
}
