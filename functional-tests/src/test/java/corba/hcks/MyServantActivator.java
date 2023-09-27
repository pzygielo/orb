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

//
// Created       : 2000 Nov 08 (Wed) 09:18:37 by Harold Carr.
// Last Modified : 2000 Nov 25 (Sat) 13:11:12 by Harold Carr.

package corba.hcks;

import org.omg.CORBA.IMP_LIMIT;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.SystemException;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;

public class MyServantActivator
        extends
        org.omg.CORBA.LocalObject
        implements
        ServantActivator {

    public static final String baseMsg = MyServantActivator.class.getName();

    public ORB orb;

    public MyServantActivator(ORB orb) {
        this.orb = orb;
    }

    public Servant incarnate(byte[] oid, POA poa)
            throws
            ForwardRequest {
        String soid = new String(oid);
        U.sop(baseMsg + ".incarnate " + soid);

        if (soid.startsWith("idl")) {

            // IDL.

            if (soid.equals(C.idlSAI1)) {
                throw new ForwardRequest(
                        poa.create_reference_with_id(C.idlSAI2.getBytes(),
                                                     idlSAIHelper.id()));
            } else if (soid.equals(C.idlSAIRaiseObjectNotExistInIncarnate)) {
                throw new OBJECT_NOT_EXIST();
            } else if (soid.equals(C.idlSAIRaiseSystemExceptionInIncarnate)) {
                throw new IMP_LIMIT();
            }
            return new idlSAIServant(orb);

        } else if (soid.startsWith("rmii")) {

            // RMII

            return makermiiIServant(orb, soid);

        } else {
            SystemException e = new INTERNAL(U.SHOULD_NOT_SEE_THIS);
            U.sopUnexpectedException(baseMsg + ".incarnate", e);
            throw e;
        }
    }

    public void etherealize(byte[] oid, POA poa, Servant servant,
                            boolean cleanupInProgress,
                            boolean remainingActivations) {
        String soid = new String(oid);
        U.sop(baseMsg + ".etherealize " + soid);
    }

    static Servant makermiiIServant(ORB orb, String name) {
        Servant servant = null;
        try {
            servant =
                    (Servant) javax.rmi.CORBA.Util.getTie(new rmiiIServantPOA(orb, name));
        } catch (Exception e) {
            U.sopUnexpectedException(baseMsg + ".makermiiIServant", e);
        }
        return servant;
    }
}

// End of file.
