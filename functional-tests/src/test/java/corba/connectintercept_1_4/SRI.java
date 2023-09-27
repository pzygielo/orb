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

//
// Created       : 2000 Oct 16 (Mon) 16:49:37 by Harold Carr.
// Last Modified : 2003 Feb 11 (Tue) 14:10:14 by Harold Carr.
//

package corba.connectintercept_1_4;

import com.sun.corba.ee.spi.legacy.interceptor.RequestInfoExt;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ServerRequestInterceptor;

public class SRI
        extends
        org.omg.CORBA.LocalObject
        implements
        ServerRequestInterceptor {

    public static final String baseMsg = SRI.class.getName();

    public int balance = 0;

    public String name() {
        return baseMsg;
    }

    public void destroy() {
        if (balance != 0) {
            throw new RuntimeException(baseMsg + ": Interceptors not balanced.");
        }
    }

    public void receive_request_service_contexts(ServerRequestInfo sri) {
        balance++;
        System.out.println(baseMsg + ".receive_request_service_contexts " +
                                   sri.operation());
        System.out.println("    request on connection: " +
                                   ((RequestInfoExt) sri).connection());
    }

    public void receive_request(ServerRequestInfo sri) {
        //balance++; // DO NOT DO THIS IN AN INTERMEDIATE POINT!
        System.out.println(baseMsg + ".receive_request " + sri.operation());
    }

    public void send_reply(ServerRequestInfo sri) {
        balance--;
        System.out.println(baseMsg + ".send_reply " + sri.operation());
    }

    public void send_exception(ServerRequestInfo sri) {
        balance--;
        System.out.println(baseMsg + ".send_exception " + sri.operation());
    }

    public void send_other(ServerRequestInfo sri) {
        balance--;
        System.out.println(baseMsg + ".send_other " + sri.operation());
    }
}

// End of file.
