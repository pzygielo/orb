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

package pi.policyfactory;

import java.util.*;
import java.io.*;

import org.omg.CORBA.*;

/**
 * This the PolicyFactory to create PolicyThousandPlus policy object.
 */
public class PolicyFactoryThousandPlus extends LocalObject
        implements org.omg.PortableInterceptor.PolicyFactory {
    public Policy create_policy(int type, Any val) {
        System.out.println("PolicyFactoryOne.create_policy called...");
        System.out.flush();
        if (type == 1000) {
            return new PolicyThousand();
        } else if (type == 10000) {
            return new PolicyTenThousand();
        }
        return null;
    }
}
  
