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
 * Invocation strategy in which a recursive call is made, causing
 * send_request and receive_reply to be invoked twice, as follows:
 * send_request
 * send_request
 * receive_reply
 * receive_reply
 */
public class InvokeRecursive
        extends InvokeStrategy {
    public void invoke() throws Exception {
        super.invoke();

        // Invoke send_request, cause send_request to make a request causing
        // send_request then receive_reply, and then finally receive_reply.
        SampleClientRequestInterceptor.recursiveInvoke = true;
        invokeMethod("sayHello");
    }
}
