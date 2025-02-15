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

package com.sun.corba.ee.spi.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

import com.sun.corba.ee.impl.misc.ORBUtility;
import com.sun.corba.ee.spi.misc.ORBConstants;
import com.sun.corba.ee.spi.orb.ORB;

import static com.sun.corba.ee.spi.misc.ORBConstants.TRANSPORT_TCP_CONNECT_MAX_TIME_TO_WAIT;

/**
 * @author Harold Carr
 */
public interface ORBSocketFactory
{
    public void setORB(ORB orb);

    public ServerSocket createServerSocket(String type, 
                                           InetSocketAddress inetSocketAddress)
        throws IOException;

    public default Socket createSocket(String type,
                                       InetSocketAddress inetSocketAddress) throws IOException {
        return createSocket(type, inetSocketAddress, TRANSPORT_TCP_CONNECT_MAX_TIME_TO_WAIT);
    }

    public default Socket createSocket(String type,
                                       InetSocketAddress inetSocketAddress,
                                       int timeout)
            throws IOException {
        SocketChannel socketChannel = null;
        Socket socket = null;

        if (type.equals(ORBConstants.SOCKETCHANNEL)) {
            socketChannel = ORBUtility.openSocketChannel(inetSocketAddress, timeout);
            socket = socketChannel.socket();
        } else {
            socket = new Socket();
            socket.connect(inetSocketAddress, timeout);
        }

        // Disable Nagle's algorithm (i.e., always send immediately).
        socket.setTcpNoDelay(true);

        return socket;
    }

    public void setAcceptedSocketOptions(Acceptor acceptor,
                                         ServerSocket serverSocket,
                                         Socket socket)
        throws SocketException;

}

// End of file.

