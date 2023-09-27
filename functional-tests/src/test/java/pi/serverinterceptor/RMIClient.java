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

package pi.serverinterceptor;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import com.sun.corba.ee.impl.corba.AnyImpl;
import com.sun.corba.ee.spi.misc.ORBConstants;
import com.sun.corba.ee.impl.interceptors.*;
import org.omg.PortableInterceptor.*;
import org.omg.IOP.*;
import org.omg.IOP.CodecPackage.*;
import org.omg.IOP.CodecFactoryPackage.*;
import corba.framework.*;

import java.util.*;
import java.io.*;
import java.rmi.*;
import javax.naming.*;
import javax.rmi.*;

import ServerRequestInterceptor.*;

/**
 * Common base class for RMI client test code
 */
public abstract class RMIClient
        extends ClientCommon {
    // The hello object to make invocations on.
    helloIF helloRef;

    // Reference to hello object to be forwarded to.
    helloIF helloRefForward;

    // RMI initial naming context
    InitialContext initialNamingContext;

    // to be invoked from subclasses after the ORB is created.
    public void run(Properties environment, String args[], PrintStream out,
                    PrintStream err, Hashtable extra)
            throws Exception {
        this.out = out;
        this.err = err;

        out.println("+ Creating initial naming context...");
        Hashtable env = new Hashtable();
        env.put("java.naming.corba.orb", orb);
        initialNamingContext = new InitialContext(env);

        // Obey the server's commands:
        obeyServer();
    }

    void resolveReferences() throws Exception {
        out.println("    - Resolving Hello1...");
        // Look up reference to hello object on server:
        helloRef = resolve("Hello1");
        out.println("    - Resolved.");

        out.println("    - Resolving Hello1Forward...");
        helloRefForward = resolve("Hello1Forward");
        out.println("    - Resolved.");
    }

    String syncWithServer() throws Exception {
        return helloRef.syncWithServer(exceptionRaised);
    }

    /**
     * Invoke the method with the given name on the object
     */
    protected void invokeMethod(String methodName)
            throws Exception {
        try {
            if (methodName.equals("sayHello")) {
                helloRef.sayHello();
            } else if (methodName.equals("sayOneway")) {
                helloRef.sayOneway();
            } else if (methodName.equals("saySystemException")) {
                helloRef.saySystemException();
            } else if (methodName.equals("sayUserException")) {
                try {
                    helloRef.sayUserException();
                    out.println("    - Did not catch ForwardRequest user " +
                                        "exception (error)");
                    throw new RuntimeException(
                            "Did not catch ForwardRequest user exception " +
                                    "on sayUserException");
                } catch (ForwardRequest e) {
                    out.println("    - Caught ForwardRequest user " +
                                        "exception (ok)");
                }
            }
        } catch (RemoteException e) {
            throw (Exception) e.detail;
        }
    }

    /**
     * Resolves name using RMI
     */
    helloIF resolve(String name)
            throws Exception {
        java.lang.Object obj = initialNamingContext.lookup(name);
        helloIF helloRef = (helloIF) PortableRemoteObject.narrow(
                obj, helloIF.class);

        return helloRef;
    }

}


