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

package com.sun.corba.ee.impl.naming.pcosnaming;

import java.io.File;

import org.omg.CORBA.Policy;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.*;

import com.sun.corba.ee.spi.orb.ORB;

import com.sun.corba.ee.spi.misc.ORBConstants;

/**
 * @author Hemanth Puttaswamy
 * @version 1.3, 00/04/06
 * @since JDK1.2
 */

public class NameService {
    private NamingContext rootContext = null;
    private POA nsPOA = null;
    private ServantManagerImpl contextMgr;
    private ORB theorb;

    /**
     * Create NameService which starts the Root Naming Context in Persistent CosNaming
     *
     * @param orb an ORB object.
     * @param logDir a File
     * @throws java.lang.Exception a Java exception.
     */
    public NameService(ORB orb, File logDir)
            throws Exception {
        theorb = orb;

        // Moved this to the creation of the ORB that is passed into this
        // constructor.
        // 
        // This is required for creating Persistent Servants under this ORB
        // Right now the Persistent NameService and ORBD are launched together
        // Find out a better way of doing this, Since ORBD is an important
        // process which should not be killed because of some external process
        // orb.setPersistentServerId( (int) 1000 );

        // get and activate the root naming POA
        POA rootPOA = (POA) orb.resolve_initial_references(
                ORBConstants.ROOT_POA_NAME);
        rootPOA.the_POAManager().activate();

        // create a new POA for persistent Naming Contexts
        // With Non-Retain policy, So that every time Servant Manager
        // will be contacted when the reference is made for the context
        // The id assignment is made by the NameServer, The Naming Context
        // id's will be in the format NC<Index>
        int i = 0;
        Policy[] poaPolicy = new Policy[4];
        poaPolicy[i++] = rootPOA.create_lifespan_policy(
                LifespanPolicyValue.PERSISTENT);
        poaPolicy[i++] = rootPOA.create_request_processing_policy(
                RequestProcessingPolicyValue.USE_SERVANT_MANAGER);
        poaPolicy[i++] = rootPOA.create_id_assignment_policy(
                IdAssignmentPolicyValue.USER_ID);
        poaPolicy[i++] = rootPOA.create_servant_retention_policy(
                ServantRetentionPolicyValue.NON_RETAIN);

        nsPOA = rootPOA.create_POA("NameService", null, poaPolicy);
        nsPOA.the_POAManager().activate();

        // create and set the servant manager
        contextMgr = new
                ServantManagerImpl(orb, logDir, this);

        // The RootObject key will be NC0
        String rootKey = contextMgr.getRootObjectKey();
        // initialize the root Naming Context
        NamingContextImpl nc =
                new NamingContextImpl(orb, rootKey, this, contextMgr);
        nc = contextMgr.addContext(rootKey, nc);
        nc.setServantManagerImpl(contextMgr);
        nc.setORB(orb);
        nc.setRootNameService(this);

        nsPOA.set_servant_manager(contextMgr);
        rootContext = NamingContextHelper.narrow(
                nsPOA.create_reference_with_id(rootKey.getBytes(),
                                               NamingContextHelper.id()));
    }

    /**
     * This method returns the Root Naming Context
     *
     * @return root naming context
     */
    public NamingContext initialNamingContext() {
        return rootContext;
    }

    /**
     * This method returns nsPOA which is the only POA that we use for
     * Persistent Naming Contexts.
     */
    POA getNSPOA() {
        return nsPOA;
    }

    /**
     * This method  creates a NewContext, This will be internally invoked from
     * NamingContextImpl. It is not a public API. NewContext is in this class
     * because a persitent reference has to be created with Persistent NameService
     * POA.
     *
     * @return new naming context
     */
    public NamingContext newContext() {
        try {
            // Get the new Naming Context Key from
            // the ServantManager
            String newKey = contextMgr.getNewObjectKey();
            // Create the new Naming context and create the Persistent
            // reference
            NamingContextImpl theContext = new NamingContextImpl(theorb,
                                                                 newKey, this, contextMgr);
            NamingContextImpl tempContext = contextMgr.addContext(newKey,
                                                                  theContext);
            if (tempContext != null) {
                theContext = tempContext;
            }
            // If the context is read from the File, The following three entries
            // will be null. So a fresh setup may be required.
            theContext.setServantManagerImpl(contextMgr);
            theContext.setORB(theorb);
            theContext.setRootNameService(this);
            NamingContext theNewContext =
                    NamingContextHelper.narrow(
                            nsPOA.create_reference_with_id(newKey.getBytes(),
                                                           NamingContextHelper.id()));
            return theNewContext;
        } catch (org.omg.CORBA.SystemException e) {
            throw e;
        } catch (java.lang.Exception e) {
            //throw e;
        }

        return null;
    }

    /**
     * getObjectReferenceFromKey returns the Object reference from the objectkey
     * using POA.create_reference_with_id method.
     *
     * @param Object Key as String
     * @returns reference an CORBA.Object.
     */
    org.omg.CORBA.Object getObjectReferenceFromKey(String key) {
        org.omg.CORBA.Object theObject = null;
        try {
            theObject = nsPOA.create_reference_with_id(key.getBytes(), NamingContextHelper.id());
        } catch (Exception e) {
            theObject = null;
        }

        return theObject;
    }

    /**
     * getObjectKey gets the Object Key from the reference using POA.reference_to_id method
     *
     * @param reference an CORBA.Object.
     * @returns Object Key as String
     */
    String getObjectKey(org.omg.CORBA.Object reference) {
        byte theId[];
        try {
            theId = nsPOA.reference_to_id(reference);
        } catch (org.omg.PortableServer.POAPackage.WrongAdapter e) {
            return null;
        } catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        String theKey = new String(theId);
        return theKey;
    }
}
