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

package corba.strm2;

import java.util.*;
import java.rmi.*;
import java.io.*;
import javax.rmi.PortableRemoteObject;
import javax.naming.*;
import javax.rmi.*;

public class Client {
    public static String getDescription() {
        return createTestObject().getDescription();
    }

    public static Testable createTestObject() {
        try {
            Class testObjectClass = Class.forName("TestObject");
            return (Testable) testObjectClass.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create TestObject", ex);
        }
    }

    private static InitialContext rootContext;

    public static void main(String[] args) {
        try {

            rootContext = new InitialContext();

            for (int i = 0; i < Versions.testableVersions.length; i++) {

                String version = Versions.testableVersions[i];

                System.out.println("Client with Testable "
                                           + getDescription()
                                           + " looking up server "
                                           + version);

                Tester tester = (Tester) PortableRemoteObject.narrow(rootContext.lookup(version),
                                                                     Tester.class);

                if (!version.equals(tester.getDescription())) {
                    throw new Exception("Version in naming doesn't match Tester.  "
                                                + version);
                }

                System.out.println("Client with Testable "
                                           + getDescription()
                                           + " verifying with server with Testable "
                                           + tester.getDescription());

                Testable t = createTestObject();

                System.out.println("Sending: ");
                System.out.println(t.toString());

                Testable result = tester.verify(t);

                System.out.println("Received: ");
                System.out.println(result.toString());

                // Note that equals has been written such that
                // data set by the stream to defaults due to
                // incompatibilities will be ignored.  
                // Data set to incorrect values will be
                // reported, though.
                if (!t.equals(result)) {
                    throw new Exception("Result not equal");
                } else {
                    System.out.println("PASSED");
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
}


