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

package corba.cmvt;

import java.io.*;
import java.util.*;

import org.omg.CORBA.*;

import java.io.DataInputStream;
import java.rmi.*;
import javax.rmi.PortableRemoteObject;

public class GIOPComboImpl extends PortableRemoteObject implements GIOPCombo {
    public GIOPComboImpl() throws RemoteException {
    }

    public int sayHello(int invalue)
            throws RemoteException {

        System.out.println("\nHello world !! invalue = " + invalue + "\n");
        return invalue;
    }

    public void throwError(Error it) throws java.rmi.RemoteException {
        System.out.println("Throwing error " + it);
        throw it;
    }

    public String echo(String string)
            throws java.rmi.RemoteException {
        System.out.println("echo String  " + string + ".");
        return string;
    }

    public java.util.Hashtable echo(java.util.Hashtable ht)
            throws java.rmi.RemoteException {
        System.out.println("echo Hashtable of size " + ht.size() + ".");
        return ht;
    }

    public java.util.Vector echo(java.util.Vector vector)
            throws java.rmi.RemoteException {
        System.out.println("echo Vector of size " + vector.size() + ".");
        return vector;
    }

    public CustomMarshalledValueType echo(CustomMarshalledValueType cmvt)
            throws java.rmi.RemoteException {
        System.out.println("echo CustomMarshalledValueType with body of size " + cmvt.body.length);
        return cmvt;
    }
}


