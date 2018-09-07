/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.rmic.classes.primitives;

import java.rmi.RemoteException;

public class RmiTestRemoteImpl implements RmiTestRemote {
    @Override
    public void test_ping() throws RemoteException {
        System.out.println("ping");
    }

    @Override
    public int test_int(int x) throws RemoteException {
        return 0;
    }
}