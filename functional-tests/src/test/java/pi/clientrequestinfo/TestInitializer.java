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

import org.omg.PortableInterceptor.*;
import org.omg.PortableInterceptor.ORBInitInfoPackage.*;

import java.util.*;
import java.io.*;

import org.omg.CORBA.*;

import ClientRequestInfo.*; // hello interface

/**
 * Registers the necessary Client Interceptors to test
 * ClientRequestInterceptor.
 */
public class TestInitializer
        extends org.omg.CORBA.LocalObject
        implements ORBInitializer {

    // The PrintStream to pass to the ClientRequestInterceptor for output.
    // This is set from Client.java statically.
    static PrintStream out;

    /**
     * The ORB to pass to the ClientRequestInterceptor
     */
    static ORB orb;

    // Where to send normal requests
    static org.omg.CORBA.Object helloRef;

    // Where to forward the caller on a ForwardRequest
    static org.omg.CORBA.Object helloRefForward;

    /**
     * Creates a TestInitializer
     */
    public TestInitializer() {
    }

    /**
     * Called before all references are registered
     */
    public void pre_init(org.omg.PortableInterceptor.ORBInitInfo info) {
    }

    /**
     * Called after all references are registered
     */
    public void post_init(org.omg.PortableInterceptor.ORBInitInfo info) {
        ClientRequestInterceptor interceptor1;
        ClientRequestInterceptor interceptor2;
        ClientRequestInterceptor interceptor3;

        interceptor1 = new SampleClientRequestInterceptor("1");
        interceptor2 = new SampleClientRequestInterceptor("2");
        interceptor3 = new SampleClientRequestInterceptor("3");

        try {
            out.println("    - post_init: adding 3 client interceptors...");
            info.add_client_request_interceptor(interceptor1);
            info.add_client_request_interceptor(interceptor2);
            info.add_client_request_interceptor(interceptor3);
        } catch (DuplicateName e) {
            out.println("    - post_init: received DuplicateName!");
        }
    }

}
