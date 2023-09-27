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

package corba.poaandequals;

import WombatStuff.Wombat;
import WombatStuff.WombatHelper;
import corba.framework.Controller;
import corba.framework.ThreadProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.omg.CORBA.ORB;

public class WombatClient extends ThreadProcess {
    ORB orb;

    public org.omg.CORBA.Object readObjref(String file) throws IOException {

        String fil = environment.getProperty("output.dir")
                + File.separator
                + file;

        String ior = null;

        try {
            java.io.DataInputStream in =
                    new java.io.DataInputStream(new FileInputStream(fil));
            ior = in.readLine();
            out.println("IOR: " + ior);
            in.close();

        } catch (java.io.IOException e) {
            err.println("Unable to open file " + fil);
            throw e;
        }

        return orb.string_to_object(ior);
    }

    public void run() {

        try {

            orb = (ORB) extra.get("orb");

            Wombat w = WombatHelper.narrow(readObjref("WombatObjRef"));

            out.println("Invoking...");

            out.println("Result: " + w.squawk() + " squawked");

            setExitValue(Controller.SUCCESS);

        } catch (Exception ex) {
            setExitValue(1);
            ex.printStackTrace(err);
        } finally {
            setFinished();
        }

        out.println("Client finished");
    }
}
