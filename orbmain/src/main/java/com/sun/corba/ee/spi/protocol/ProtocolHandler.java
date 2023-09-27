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

package com.sun.corba.ee.spi.protocol;

import com.sun.corba.ee.impl.protocol.giopmsgheaders.LocateRequestMessage;
import com.sun.corba.ee.impl.protocol.giopmsgheaders.RequestMessage;
import com.sun.corba.ee.spi.ior.IOR;
import com.sun.corba.ee.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.UnknownException;

/**
 * @author Harold Carr
 */
public abstract interface ProtocolHandler {
    public void handleRequest(RequestMessage header,
                              MessageMediator messageMediator);

    public void handleRequest(LocateRequestMessage header,
                              MessageMediator messageMediator);

    public MessageMediator createResponse(
            MessageMediator messageMediator,
            ServiceContexts svc);

    public MessageMediator createUserExceptionResponse(
            MessageMediator messageMediator,
            ServiceContexts svc);

    public MessageMediator createUnknownExceptionResponse(
            MessageMediator messageMediator,
            UnknownException ex);

    public MessageMediator createSystemExceptionResponse(
            MessageMediator messageMediator,
            SystemException ex,
            ServiceContexts svc);

    public MessageMediator createLocationForward(
            MessageMediator messageMediator,
            IOR ior,
            ServiceContexts svc);

    public void handleThrowableDuringServerDispatch(
            MessageMediator request,
            Throwable exception,
            CompletionStatus completionStatus);

    public boolean handleRequest(MessageMediator messageMediator);

}

// End of file.
