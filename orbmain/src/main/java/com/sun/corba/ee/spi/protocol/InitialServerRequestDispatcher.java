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

import com.sun.corba.ee.spi.resolver.Resolver ;

/** InitialServerRequestDispatcher is a specialized version of a ServerRequestDispatcher 
 * that provides an initialization method.  This delegate is used 
 * to implement bootstrapping of initial object references.
 */
public interface InitialServerRequestDispatcher 
    extends ServerRequestDispatcher
{
    /** Plug in the resolver that this InitialServerRequestDispatcher should
     * use in order to lookup or list initial name to object reference
     * bindings.
     * @param resolver that this should use
     */
    void init( Resolver resolver ) ;
}

