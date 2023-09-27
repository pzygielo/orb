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

package corba.custom;

import org.omg.CORBA.portable.*;

import javax.rmi.PortableRemoteObject;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.util.Properties;

import org.omg.PortableServer.*;

// Nothing interesting here
public class Server {
    public static void main(String args[]) {
        try {

            String fragmentSize = System.getProperty(com.sun.corba.ee.spi.misc.ORBConstants.GIOP_FRAGMENT_SIZE);

            if (fragmentSize != null) {
                System.out.println("---- Fragment size: " + fragmentSize);
            }

            ORB orb = ORB.init(args, System.getProperties());

            // Get rootPOA
            POA rootPOA = (POA) orb.resolve_initial_references("RootPOA");
            rootPOA.the_POAManager().activate();

            VerifierImpl impl = new VerifierImpl();
            javax.rmi.CORBA.Tie tie = javax.rmi.CORBA.Util.getTie(impl);

            byte[] id = rootPOA.activate_object((org.omg.PortableServer.Servant) tie);
            org.omg.CORBA.Object obj = rootPOA.id_to_reference(id);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);

            // bind the Object Reference in Naming
            NameComponent nc = new NameComponent("Verifier", "");
            NameComponent path[] = { nc };

            ncRef.rebind(path, obj);

            // Emit the handshake the test framework expects
            // (can be changed in Options by the running test)
            System.out.println("Server is ready.");

            // Wait for clients
            orb.run();

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);

            // Make sure to exit with a value greater than 0 on
            // error.
            System.exit(1);
        }
    }
}
