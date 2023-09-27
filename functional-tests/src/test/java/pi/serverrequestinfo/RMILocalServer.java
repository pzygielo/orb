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

package pi.serverrequestinfo;

import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.ServantLocatorPackage.*;
import org.omg.PortableInterceptor.*;
import com.sun.corba.ee.impl.interceptors.*;
import corba.framework.*;
import com.sun.corba.ee.spi.misc.ORBConstants;

import java.util.*;
import java.io.*;

import ServerRequestInfo.*;

import java.rmi.*;
import javax.rmi.*;
import javax.naming.*;

public class RMILocalServer
        extends RMIServer {
    // Object to syncrhornize on to wait for server to start:
    private java.lang.Object syncObject;

    public static void main(String args[]) {
        final String[] arguments = args;
        try {
            final RMILocalServer server = new RMILocalServer();

            TestInitializer.out = System.out;
            server.out = System.out;
            server.err = System.err;

            System.out.println("===============================");
            System.out.println("Creating ORB for RMI Local test");
            System.out.println("===============================");

            // For this test, start botht he client and the server using
            // the same ORB.
            System.out.println("+ Creating ORB for client and server...");
            server.createORB(args, new Properties());

            System.out.println("+ Starting Server...");
            server.syncObject = new java.lang.Object();

            new Thread() {
                public void run() {
                    try {
                        server.run(
                                System.getProperties(),
                                arguments, System.out,
                                System.err, null);
                    } catch (Exception e) {
                        System.err.println("SERVER CRASHED:");
                        e.printStackTrace(System.err);
                        System.exit(1);
                    }
                }
            }.start();

            // Wait for server to start...
            synchronized (server.syncObject) {
                try {
                    server.syncObject.wait();
                } catch (InterruptedException e) {
                    // ignore.
                }
            }

            // Start client:
            System.out.println("+ Starting client...");
            RMILocalClient client = new RMILocalClient(server.orb);
            client.run(System.getProperties(),
                       args, System.out, System.err, null);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public void run(Properties environment, String args[], PrintStream out,
                    PrintStream err, Hashtable extra)
            throws Exception {
        super.run(environment, args, out, err, extra);
    }

    void handshake() {
        synchronized (syncObject) {
            syncObject.notifyAll();
        }
    }

    void waitForClients() {
    }
}

