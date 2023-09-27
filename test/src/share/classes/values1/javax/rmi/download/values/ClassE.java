/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
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

package javax.rmi.download.values;

public class ClassE extends ClassA {
    private static final long serialVersionUID = 222456789L;
    private int f, g, h;

    public ClassE() {
        f = 6;
        g = 8;
        h = 2;
    }

    public long getTotal() {
        return super.getTotal() + f + g + h;
    }

    public long getOriginalTotal() {
        return super.getOriginalTotal() + 6 + 8 + 2;
    }
} // 19
