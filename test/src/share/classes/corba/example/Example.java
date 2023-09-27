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

import test.Test;
import corba.framework.*;

import java.util.*;

/**
 * This is a POA version of the mthello test.  The
 * client creates multiple threads that invoke a simple sayHello
 * method on the remote servant.
 */
public class Example extends CORBATest {
    // Define the IDL files that need to be compiled
    public static String[] idlFiles = { "hello.idl" };

    // Define the .java files that need to be compiled.  Any
    // java files generated by compiling IDL will automatically be
    // compiled with these.
    public static String[] javaFiles = { "Server.java",
            "Client.java" };

    // This is the main method defining the test.  All tests
    // should have this.
    protected void doTest() throws Throwable {
        // Add some command line arguments for the IDL compiler.
        Options.addIDLCompilerArgs("-fall");

        // Tell the test about the IDL and Java files defined above.
        Options.setIDLFiles(idlFiles);
        Options.setJavaFiles(javaFiles);

        // Run the compilers.
        compileIDLFiles();
        compileJavaFiles();

        // Create (but don't start) a "Controller" for ORBD.  This
        // allows you to start, stop, and wait for this process, among
        // other things.
        Controller orbd = createORBD();

        // Create server and client controllers using the given
        // classes.  You can also specify names for these (for instance,
        // you may want to distinguish between many clients) by using
        // the equivalent methods that take two Strings.
        Controller server = createServer("corba.example.Server");
        Controller client = createClient("corba.example.Client");

        // Start the ORBD process.  The execution strategies and
        // parameters used by default will wait for the ORBD and Server
        // to emit handshakes ("ORBD is ready" etc) before returning from
        // the start methods.  Execution strategies can be changed to
        // allow something to run in the test process.
        orbd.start();
        server.start();
        client.start();

        // Wait for the client to finish for up to 2 minutes, then
        // throw an exception.
        client.waitFor(120000);

        // Make sure all the processes are shut down.
        client.stop();
        server.stop();
        orbd.stop();
    }
}

