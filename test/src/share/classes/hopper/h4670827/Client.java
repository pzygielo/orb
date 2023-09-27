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

package hopper.h4670827;

import org.glassfish.pfl.test.JUnitReportHelper;
import org.omg.CORBA.ORB;

public class Client implements Runnable {
    private JUnitReportHelper helper;
    private boolean failed = false;

    public static void main(String args[]) {
        new Client().run();
    }

    private ORB orb;

    public void run() {
        helper = new JUnitReportHelper(Client.class.getName());
        orb = ORB.init((String[]) null, null);

        for (Object[] arr : TestConstants.data) {
            String name = (String) arr[0];
            String url = (String) arr[1];
            boolean shouldSucceed = (Boolean) arr[2];
            helper.start(name);
            try {
                if (testURL(url, shouldSucceed)) {
                    System.out.println("Passed test " + name);
                    helper.pass();
                } else {
                    System.out.println("Test " + name + " failed");
                    helper.fail("failed");
                    failed = true;
                }
            } catch (Exception exc) {
                helper.fail(exc);
                failed = true;
            }
        }

        System.out.println("Thread " + Thread.currentThread() + " done.");
        if (failed) {
            System.exit(1);
        }
    }

    private boolean testURL(String url, boolean shouldPass) {
        if (shouldPass) {
            org.omg.CORBA.Object obj = orb.string_to_object(url);
            if (obj == null) {
                System.err.println(url + " lookup failed..");
                return false;
            }
            Hello helloRef = HelloHelper.narrow(obj);
            String returnString = helloRef.sayHello();
            if (!returnString.equals(TestConstants.returnString)) {
                System.err.println(" hello.sayHello() did not return.." +
                                           TestConstants.returnString);
                System.err.flush();
                return false;
            }
        } else {
            try {
                org.omg.CORBA.Object obj = orb.string_to_object(url);
                Hello helloRef = HelloHelper.narrow(obj);
                String returnString = helloRef.sayHello();
                // Shouldn't be here
                return false;
            } catch (Exception e) {
                System.out.println("Caught Exception " + e + " as expected ");
            }
        }

        // If we are here then we passed the test
        return true;
    }
}
