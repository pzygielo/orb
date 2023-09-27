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
// Created       : 2003 Apr 17 (Thu) 17:05:00 by Harold Carr.
// Last Modified : 2005 Jun 01 (Wed) 11:05:35 by Harold Carr.
//

package corba.folb_8_1;

import java.util.Properties;

import org.omg.CORBA.ORB;

public class ColocatedCS {
    public static final String baseMsg = ColocatedCS.class.getName();
    public static final String main = baseMsg + ".main";

    public static ORB orb;
    public static boolean isColocated = false;
    public static boolean clientTwoRefs = false;
    public static java.lang.Object signal = new java.lang.Object();

    public static void main(String[] av) {
        isColocated = true; // Used by Client and Server.

        try {
            // Share an ORB between a client and server.
            // So ClientDelegate.isLocal currently succeeds.

            Properties props = new Properties();
            props.setProperty("com.sun.corba.ee.ORBAllowLocalOptimization",
                              "true");
            Client.setProperties(props);
            Server.setProperties(props, Common.socketPorts);
            System.out.println(main + " : creating ORB.");
            orb = ORB.init(av, props);
            Server.orb = orb;
            if (clientTwoRefs) {
                ClientTwoRefs.orb = orb;
            } else {
                Client.orb = orb;
            }

            ServerThread ServerThread = new ServerThread(av);
            ServerThread.start();
            synchronized (signal) {
                try {
                    signal.wait();
                } catch (InterruptedException e) {
                    ;
                }
            }
            if (clientTwoRefs) {
                ClientTwoRefs.main(av);
            } else {
                Client.main(av);
            }
            if (Client.foundErrors) {
                System.out.println("FAIL");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.out.println(main);
            t.printStackTrace(System.out);
            System.exit(1);
        }
        System.out.println(main + " done");
    }
}

class ServerThread extends Thread {
    String[] args;

    ServerThread(String[] args) {
        this.args = args;
    }

    public void run() {
        Server.main(args);
    }
}

// End of file.
