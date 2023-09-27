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

package pi.clientrequestinfo;

import com.sun.corba.ee.impl.interceptors.*;
import org.omg.PortableInterceptor.*;
import org.omg.CORBA.*;

/**
 * Invocation strategy in which each interception point is visited.
 * The following order is used:
 * send_request, receive_reply
 * send_request, receive_exception
 * send_request, receive_other
 */
public class InvokeVisitAll
        extends InvokeStrategy {
    public void invoke() throws Exception {
        super.invoke();

        // Invoke send_request then receive_reply
        invokeMethod("sayHello");

        // Invoke send_request then receive_exception:
        try {
            invokeMethod("saySystemException");
        } catch (UNKNOWN e) {
            // We expect this, but no other exception.
        }

        // Invoke send_request then receive_other:
        SampleClientRequestInterceptor.exceptionRedirectToOther = true;
        try {
            invokeMethod("saySystemException");
        } catch (UNKNOWN e) {
            // We expect this, but no other exception.
        }
    }
}
