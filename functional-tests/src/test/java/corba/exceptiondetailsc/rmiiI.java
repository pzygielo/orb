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
// Created       : 2003 Apr 10 (Thu) 11:30:38 by Harold Carr.
// Last Modified : 2003 Jul 28 (Mon) 09:26:35 by Harold Carr.
//

package corba.exceptiondetailsc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface rmiiI
        extends
        Remote {
    public void raiseSystemException(String x)
            throws
            RemoteException;

    public void raiseUserException(String x)
            throws
            RemoteException,
            rmiiException;

    public void raiseRuntimeException(String x)
            throws
            RemoteException;
}

// End of file.

