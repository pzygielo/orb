/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates.
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

package org.glassfish.rmic.asm;

import org.glassfish.rmic.tools.java.*;

/**
 * This represents a class for RMIC to process. It is built from a class file using ASM.
 */
class AsmClass extends ClassDefinition {

    private final AsmClassFactory factory;

    AsmClass(AsmClassFactory factory, String name, int modifiers, ClassDeclaration declaration, ClassDeclaration superClassDeclaration,
             ClassDeclaration[] interfaceDeclarations) {
        super(name, 0, declaration, modifiers, null, null);
        this.factory = factory;
        superClass = superClassDeclaration;
        interfaces = interfaceDeclarations;
    }

    @Override
    public void loadNested(Environment env) {
        try {
            Identifier outerClass = factory.getOuterClassName(getName());
            if (outerClass != null) {
                this.outerClass = env.getClassDefinition(outerClass);
            }
        } catch (ClassNotFound ignore) {
        }
    }

    private boolean basicCheckDone = false;
    private boolean basicChecking = false;

    // This code is copied from BinaryClass.java which ensures that inherited method 
    // information is gathered. Consider promoting this to the super class.
    protected void basicCheck(Environment env) throws ClassNotFound {
        if (tracing) {
            env.dtEnter("AsmClass.basicCheck: " + getName());
        }

        if (basicChecking || basicCheckDone) {
            if (tracing) {
                env.dtExit("AsmClass.basicCheck: OK " + getName());
            }
            return;
        }

        if (tracing) {
            env.dtEvent("AsmClass.basicCheck: CHECKING " + getName());
        }
        basicChecking = true;

        super.basicCheck(env);

        // Collect inheritance information.
        if (doInheritanceChecks) {
            collectInheritedMethods(env);
        }

        basicCheckDone = true;
        basicChecking = false;
        if (tracing) {
            env.dtExit("AsmClass.basicCheck: " + getName());
        }
    }

}
