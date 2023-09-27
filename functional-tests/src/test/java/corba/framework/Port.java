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

package corba.framework;

import java.io.*;
import java.net.*;

/**
 * Simple port abstraction with the capability of
 * late binding to an unused port.
 */
public class Port {
    public static class PortException extends RuntimeException {
        public PortException(String reason) {
            super(reason);
        }
    }

    private static final int UNBOUND = 0;
    private int port = Port.UNBOUND;

    /**
     * Port will late bind to an unused port when
     * getValue or toString is called.
     */
    public Port() {
    }

    /**
     * Will use the provided port value when
     * getValue or toString is called.
     */
    public Port(int value) {
        port = value;
    }

    public int getValue() {
        bind();

        return port;
    }

    public String toString() {
        return String.valueOf(getValue());
    }

    /**
     * Is this port available for use?
     */
    public boolean isFree() {
        bind();

        return Port.isFree(port);
    }

    /**
     * If the port is unbound, find an unused and
     * set our instance variable to it.
     */
    private int bind() {
        if (port == Port.UNBOUND) {

            try {
                ServerSocket socket = new ServerSocket(0);

                port = socket.getLocalPort();

                socket.close();
            } catch (IOException ex) {
                throw new PortException(ex.getMessage());
            }
        }

        return port;
    }

    /**
     * Determine if the provided port is unused.  Tries to
     * create a ServerSocket at the given port.
     *
     * @param port Port to test
     * @return true if port is unused
     */
    public static boolean isFree(int port) {
        boolean result = false;
        try {
            ServerSocket ssocket = new ServerSocket(port);

            ssocket.close();

            return true;

        } catch (IOException ex) {
            return false;
        }
    }
}
