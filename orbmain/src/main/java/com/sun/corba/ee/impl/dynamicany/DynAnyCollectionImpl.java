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

package com.sun.corba.ee.impl.dynamicany;

import com.sun.corba.ee.spi.orb.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

abstract class DynAnyCollectionImpl extends DynAnyConstructedImpl {
    private static final long serialVersionUID = -4420130353899323070L;

    //
    // Instance variables
    //

    // Keep in sync with DynAny[] components at all times.
    Any[] anys = null;

    //
    // Constructors
    //

    protected DynAnyCollectionImpl(ORB orb, Any any, boolean copyValue) {
        super(orb, any, copyValue);
    }

    protected DynAnyCollectionImpl(ORB orb, TypeCode typeCode) {
        super(orb, typeCode);
    }

    //
    // Utility methods
    //

    protected void createDefaultComponentAt(int i, TypeCode contentType) {
        try {
            components[i] = DynAnyUtil.createMostDerivedDynAny(contentType, orb);
        } catch (InconsistentTypeCode itc) { // impossible
        }
        // get a hold of the default initialized Any without copying
        anys[i] = getAny(components[i]);
    }

    protected TypeCode getContentType() {
        try {
            return any.type().content_type();
        } catch (BadKind badKind) { // impossible
            return null;
        }
    }

    // This method has a different meaning for sequence and array:
    // For sequence value of 0 indicates an unbounded sequence,
    // values > 0 indicate a bounded sequence.
    // For array any value indicates the boundary.
    protected int getBound() {
        try {
            return any.type().length();
        } catch (BadKind badKind) { // impossible
            return 0;
        }
    }

    //
    // DynAny interface methods
    //

    // _REVISIT_ More efficient copy operation

    //
    // Collection methods
    //

    public org.omg.CORBA.Any[] get_elements() {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed();
        }
        return (checkInitComponents() ? anys : null);
    }

    protected abstract void checkValue(Object[] value)
            throws org.omg.DynamicAny.DynAnyPackage.InvalidValue;

    // Initializes the elements of the ordered collection.
    // If value does not contain the same number of elements as the array dimension,
    // the operation raises InvalidValue.
    // If one or more elements have a type that is inconsistent with the collections TypeCode,
    // the operation raises TypeMismatch.
    // This operation does not change the current position.
    public void set_elements(org.omg.CORBA.Any[] value)
            throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
            org.omg.DynamicAny.DynAnyPackage.InvalidValue {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed();
        }
        checkValue(value);

        components = new DynAny[value.length];
        anys = value;

        // We know that this is of kind tk_sequence or tk_array
        TypeCode expectedTypeCode = getContentType();
        for (int i = 0; i < value.length; i++) {
            if (value[i] != null) {
                if (!value[i].type().equal(expectedTypeCode)) {
                    clearData();
                    // _REVISIT_ More info
                    throw new TypeMismatch();
                }
                try {
                    // Creates the appropriate subtype without copying the Any
                    components[i] = DynAnyUtil.createMostDerivedDynAny(value[i], orb, false);
                    //System.out.println(this + " created component " + components[i]);
                } catch (InconsistentTypeCode itc) {
                    throw new InvalidValue();
                }
            } else {
                clearData();
                // _REVISIT_ More info
                throw new InvalidValue();
            }
        }
        index = (value.length == 0 ? NO_INDEX : 0);
        // Other representations are invalidated by this operation
        representations = REPRESENTATION_COMPONENTS;
    }

    public org.omg.DynamicAny.DynAny[] get_elements_as_dyn_any() {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed();
        }
        return (checkInitComponents() ? components : null);
    }

    // Same semantics as set_elements(Any[])
    public void set_elements_as_dyn_any(org.omg.DynamicAny.DynAny[] value)
            throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
            org.omg.DynamicAny.DynAnyPackage.InvalidValue {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed();
        }
        checkValue(value);

        components = (value == null ? emptyComponents : value);
        if (value != null) {
            anys = new Any[value.length];

            // We know that this is of kind tk_sequence or tk_array
            TypeCode expectedTypeCode = getContentType();
            for (int i = 0; i < value.length; i++) {
                if (value[i] != null) {
                    if (!value[i].type().equal(expectedTypeCode)) {
                        clearData();
                        // _REVISIT_ More info
                        throw new TypeMismatch();
                    }
                    anys[i] = getAny(value[i]);
                } else {
                    clearData();
                    // _REVISIT_ More info
                    throw new InvalidValue();
                }
            }
            index = (value.length == 0 ? NO_INDEX : 0);
        }
        // Other representations are invalidated by this operation
        representations = REPRESENTATION_COMPONENTS;
    }
}
