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

// Test the ability to turn away a rogue client.
//
// This test creates a Server and a well behaved Client that simply pass
// a String array between. It also creates another Rogue Client that attempts
// a variety of rogue attack by connecting to the ORBInitialPort and sending
// partial GIOP headers, GIOP messages and making a large number of connections.

package corba.rogueclient;

import java.util.Properties;

import com.sun.corba.ee.spi.misc.ORBConstants;
import corba.framework.Controller;
import corba.framework.CORBATest;
import corba.framework.Options;

public class RogueClientTest extends CORBATest {

    public static final String thisPackage =
            RogueClientTest.class.getPackage().getName();

    private final static int CLIENT_TIMEOUT = 250000;

    protected void doTest() throws Throwable {
        Controller orbd = createORBD();
        orbd.start();
        //        Properties serverProps = Options.getServerProperties();
        //        serverProps.setProperty(ORBConstants.DEBUG_PROPERTY,"transport,giop");
        Controller server = createServer(thisPackage + ".Server", "Server");
        server.start();

        //        Properties rogueClientProps = Options.getClientProperties();
        //        rogueClientProps.setProperty(ORBConstants.DEBUG_PROPERTY,"transport,giop");
        Controller rogueClient = createClient(thisPackage + ".RogueClient", "RogueClient");

        // put some tougher than defaults settings on well behaved client
        // so command line property for read timeouts gets executed
        Properties clientProps = Options.getClientProperties();
        clientProps.setProperty(ORBConstants.TRANSPORT_TCP_TIMEOUTS_PROPERTY,
                                "150:2500:25");
        Controller client = createClient(thisPackage + ".Client", "Client");

        client.start();

        rogueClient.start();

        client.waitFor(CLIENT_TIMEOUT);
        rogueClient.waitFor(CLIENT_TIMEOUT);

        client.stop();
        rogueClient.stop();
        server.stop();
        orbd.stop();
    }
}

// End of file.

