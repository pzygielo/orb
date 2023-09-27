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

import java.io.*;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.util.Properties;
import java.util.Hashtable;

import corba.framework.*;
import com.sun.corba.ee.spi.misc.ORBConstants;

public class INSServer implements InternalProcess {

    public static void main(String args[]) {
        try {
            (new INSServer()).run(System.getProperties(),
                                  args, System.out, System.err, null);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public void run(Properties environment,
                    String args[],
                    PrintStream out,
                    PrintStream err,
                    Hashtable extra) throws Exception {
        try {

            //We need to set ORBInitialPort = PersistentServerPort to start this
            //process as a Bootstrap server which can listen to INS Requests on
            //an assigned port.
            args = new String[2];
            args[0] = "-ORBInitialPort";
            args[1] = TestConstants.ORBInitialPort;
            environment.put(ORBConstants.PERSISTENT_SERVER_PORT_PROPERTY,
                            TestConstants.ORBInitialPort);

            ORB orb = ORB.init(args, environment);

            HelloImpl helloRef = new HelloImpl();
            orb.connect(helloRef);
            ((com.sun.corba.ee.spi.orb.ORB) orb).register_initial_reference(
                    TestConstants.INSServiceName, helloRef);

            //handshake:
            out.println("Server is ready.");
            out.flush();

            orb.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}

        
        



