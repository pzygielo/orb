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

package corba.example;

import com.sun.corba.ee.spi.misc.ORBConstants;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import HelloApp.*;

public class Client implements Runnable {
    static final int NTHREADS = 100;
    static final int NITNS = 10;
    static hello helloRef;

    static final java.lang.Object lock = new java.lang.Object();
    static boolean errorOccured = false;

    public void signalError() {
        synchronized (Client.lock) {
            errorOccured = true;
        }
    }

    public static void main(String args[]) {
        try {
            Properties props = new Properties(System.getProperties());
            // Examples of how to set ORB debug properties and default fragment size
            //          props.put(ORBConstants.DEBUG_PROPERTY, "transport,giop");
            //            props.put(ORBConstants.GIOP_FRAGMENT_SIZE, "32");
            // create and initialize the ORB
            ORB orb = ORB.init(args, props);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            NameComponent nc = new NameComponent("Hello", "");
            NameComponent path[] = { nc };
            helloRef = helloHelper.narrow(ncRef.resolve(path));

            System.out.println("Starting client threads...");

            Thread[] threads = new Thread[NTHREADS];
            for (int i = 0; i < NTHREADS; i++) {
                threads[i] = new Thread(new Client());
            }
            System.out.println("Starting all threads");
            for (int i = 0; i < NTHREADS; i++) {
                threads[i].start();
            }

            // Wait for all threads to finish
            for (int i = 0; i < NTHREADS; i++) {
                threads[i].join();
            }

            // Perform a simple test on stub equality. ie., test two stubs
            // which point to the same object for equality.

            nc = new NameComponent("Hello", "");
            path = new NameComponent[] { nc };
            hello helloRef2 = helloHelper.narrow(ncRef.resolve(path));

            boolean result = helloRef.equals(helloRef2);
            System.out.println("equals: " + result);
            if (result == false) {
                errorOccured = true;
            }

            // test finished

            System.out.println("All threads returned, client finished");

            if (errorOccured) {
                System.exit(1);
            }

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public void run() {
        try {
            for (int i = 0; i < NITNS; i++) {
                // call the hello server object and print results
                String hello = helloRef.sayHello();
                System.out.println(hello);
                if (!hello.equals("Hello world!")) {
                    System.out.println("Bad result of \"" + hello + "\" in "
                                               + Thread.currentThread());
                    signalError();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR in thread: " + e);
            e.printStackTrace(System.out);
            signalError();
        }
        System.out.println("Thread " + Thread.currentThread() + " done.");
    }
}
