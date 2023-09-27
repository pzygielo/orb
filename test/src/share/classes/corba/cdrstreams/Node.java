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

package corba.cdrstreams;

import java.io.Serializable;
import java.util.Vector;

public class Node implements Serializable {
    public String value;
    public Vector links;

    public Node() {
        value = "";
        links = new Vector();
    }

    public Node(String value, Vector links) {
        this.value = value;
        this.links = links;
    }

    private boolean valueCompare(Node node1, Node node2) {
        return node1.value.equals(node2.value);
    }

    // Light equals method
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Node)) {
            return false;
        }

        Node rt = (Node) obj;

        if (!valueCompare(this, rt)) {
            return false;
        }

        if (this.links.size() != rt.links.size()) {
            return false;
        }

        for (int i = 0; i < links.size(); i++) {
            Node linkl = (Node) this.links.get(i);
            Node linkr = (Node) rt.links.get(i);

            if (!valueCompare(linkl, linkr)) {
                return false;
            }
            if (linkl.links.size() != linkr.links.size()) {
                return false;
            }
        }

        return true;
    }
}

