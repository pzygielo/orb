/*
 * Copyright (c) 1998, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
 * Copyright (c) 2019 Payara Services Ltd.
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

package org.glassfish.rmic.iiop;

import org.glassfish.rmic.tools.java.ClassDeclaration;
import org.glassfish.rmic.tools.java.ClassDefinition;
import org.glassfish.rmic.tools.java.ClassNotFound;
import org.glassfish.rmic.tools.java.MemberDefinition;

import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.util.Hashtable;
import java.util.Vector;


/**
 * ValueType represents any non-special class which does inherit from
 * java.io.Serializable and does not inherit from java.rmi.Remote.
 * <p>
 * The static forValue(...) method must be used to obtain an instance, and
 * will return null if the ClassDefinition is non-conforming.
 *
 * @author      Bryan Atsatt
 */
public class ValueType extends ClassType {

    private boolean isCustom;

    //_____________________________________________________________________
    // Public Interfaces
    //_____________________________________________________________________

    /**
     * Create an ValueType object for the given class.
     *
     * If the class is not a properly formed or if some other error occurs, the
     * return value will be null, and errors will have been reported to the
     * supplied BatchEnvironment.
     */
    public static ValueType forValue(ClassDefinition classDef,
                                     ContextStack stack,
                                     boolean quiet) {

        if (stack.anyErrors()) return null;

        // Do we already have it?

        org.glassfish.rmic.tools.java.Type theType = classDef.getType();
        String typeKey = theType.toString();
        Type existing = getType(typeKey,stack);

        if (existing != null) {

            if (!(existing instanceof ValueType)) return null; // False hit.

            // Yep, so return it...

            return (ValueType) existing;
        }

        // Is this java.lang.Class?

        boolean javaLangClass = false;

        if (classDef.getClassDeclaration().getName() == idJavaLangClass) {

            // Yes, so replace classDef with one for
            // javax.rmi.CORBA.ClassDesc...

            javaLangClass = true;
            BatchEnvironment env = stack.getEnv();
            ClassDeclaration decl = env.getClassDeclaration(idClassDesc);
            ClassDefinition def = null;

            try {
                def = decl.getClassDefinition(env);
            } catch (ClassNotFound ex) {
                classNotFound(stack,ex);
                return null;
            }

            classDef = def;
        }

        // Could this be a value?

        if (couldBeValue(stack,classDef)) {

            // Yes, so check it...

            ValueType it = new ValueType(classDef,stack,javaLangClass);
            putType(typeKey,it,stack);
            stack.push(it);

            if (it.initialize(stack,quiet)) {
                stack.pop(true);
                return it;
            } else {
                removeType(typeKey,stack);
                stack.pop(false);
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * Return a string describing this type.
     */
    @Override
    public String getTypeDescription () {
        String result = addExceptionDescription("Value");
        if (isCustom) {
            result = "Custom " + result;
        }
        if (isIDLEntity) {
            result = result + " [IDLEntity]";
        }
        return result;
    }

    /**
     * Return true if this type is a "custom" type (i.e.
     * it implements java.io.Externalizable or has a
     * method with the following signature:
     *
     *  private void writeObject(java.io.ObjectOutputStream out);
     *
     */
    public boolean isCustom () {
        return isCustom;
    }


    //_____________________________________________________________________
    // Subclass/Internal Interfaces
    //_____________________________________________________________________

    /**
     * Create a ValueType instance for the given class.  The resulting
     * object is not yet completely initialized.
     */
    private ValueType(ClassDefinition classDef,
                      ContextStack stack,
                      boolean isMappedJavaLangClass) {
        super(stack,classDef,TYPE_VALUE | TM_CLASS | TM_COMPOUND);
        isCustom = false;

        // If this is the mapped version of java.lang.Class,
        // set the non-IDL names back to java.lang.Class...

        if (isMappedJavaLangClass) {
            setNames(idJavaLangClass,IDL_CLASS_MODULE,IDL_CLASS);
        }
    }

    //_____________________________________________________________________
    // Internal Interfaces
    //_____________________________________________________________________

    /**
     * Initialize this instance.
     */

    private static boolean couldBeValue(ContextStack stack, ClassDefinition classDef) {

        boolean result = false;
        ClassDeclaration classDecl = classDef.getClassDeclaration();
        BatchEnvironment env = stack.getEnv();

        try {
            // Make sure it's not remote...

            if (env.defRemote.implementedBy(env, classDecl)) {
                failedConstraint(10,false,stack,classDef.getName());
            } else {

                // Make sure it's Serializable...

                if (!env.defSerializable.implementedBy(env, classDecl)) {
                    failedConstraint(11,false,stack,classDef.getName());
                } else {
                    result = true;
                }
            }
        } catch (ClassNotFound e) {
            classNotFound(stack,e);
        }

        return result;
    }

    /**
     * Initialize this instance.
     */
    private boolean initialize (ContextStack stack, boolean quiet) {

        ClassDefinition ourDef = getClassDefinition();
        ClassDeclaration ourDecl = getClassDeclaration();

        try {

            // Make sure our parentage is ok...

            if (!initParents(stack)) {
                failedConstraint(12,quiet,stack,getQualifiedName());
                return false;
            }


            // We're ok, so make up our collections...

            Vector<InterfaceType> directInterfaces = new Vector<>();
            Vector<Method> directMethods = new Vector<>();
            Vector<Member> directMembers = new Vector<>();

            // Get interfaces...

            if (addNonRemoteInterfaces(directInterfaces,stack) != null) {

                // Get methods...

                if (addAllMethods(ourDef,directMethods,false,false,stack) != null) {

                    // Update parent class methods
                    if (updateParentClassMethods(ourDef,directMethods,false,stack) != null) {

                    // Get constants and members...

                    if (addAllMembers(directMembers,false,false,stack)) {

                        // We're ok, so pass 'em up...

                        if (!initialize(directInterfaces,directMethods,directMembers,stack,quiet)) {
                            return false;
                        }

                        // Is this class Externalizable?

                        boolean externalizable = false;
                        if (!env.defExternalizable.implementedBy(env, ourDecl)) {

                            // No, so check to see if we have a serialPersistentField
                            // that will modify the members.

                            if (!checkPersistentFields(getClassInstance(),quiet)) {
                                return false;
                            }
                        } else {

                            // Yes.

                            externalizable = true;
                        }

                        // Should this class be considered "custom"? It is if
                        // it is Externalizable OR if it has a method with the
                        // following signature:
                        //
                        //  private void writeObject(java.io.ObjectOutputStream out);
                        //

                        if (externalizable) {
                            isCustom = true;
                        } else {
                            for (MemberDefinition member = ourDef.getFirstMember();
                                 member != null;
                                 member = member.getNextMember()) {

                                if (member.isMethod() &&
                                    !member.isInitializer() &&
                                    member.isPrivate() &&
                                    member.getName().toString().equals("writeObject")) {

                                    // Check return type, arguments and exceptions...

                                    org.glassfish.rmic.tools.java.Type methodType = member.getType();
                                    org.glassfish.rmic.tools.java.Type rtnType = methodType.getReturnType();

                                    if (rtnType == org.glassfish.rmic.tools.java.Type.tVoid) {

                                        // Return type is correct. How about arguments?

                                        org.glassfish.rmic.tools.java.Type[] args = methodType.getArgumentTypes();
                                        if (args.length == 1 &&
                                            args[0].getTypeSignature().equals("Ljava/io/ObjectOutputStream;")) {

                                            // Arguments are correct, so it is a custom
                                            // value type...

                                            isCustom = true;
                                        }
                                    }
                                }
                            }
                        }
                        }

                        return true;
                    }
                }
            }
        } catch (ClassNotFound e) {
            classNotFound(stack,e);
        }

        return false;
    }


    private boolean checkPersistentFields (Class<?> clz, boolean quiet) {

        // Do we have a writeObject method?
        for (Method method : methods) {
            if (method.getName().equals("writeObject") && method.getArguments().length == 1) {
                Type returnType = method.getReturnType();
                Type arg = method.getArguments()[0];
                String id = arg.getQualifiedName();
                if (returnType.isType(TYPE_VOID) &&
                        id.equals("java.io.ObjectOutputStream")) {
                    
                    // Got one, so there's nothing to do...

                    return true;
                }
            }
        }

        // Do we have a valid serialPersistentField array?

        MemberDefinition spfDef = null;

        for (Member member1 : members) {
            if (member1.getName().equals("serialPersistentFields")) {
                Member member = member1;
                Type type = member.getType();
                Type elementType = type.getElementType();
                // We have a member with the correct name. Make sure
                // we have the correct signature...

                if (elementType != null &&
                        elementType.getQualifiedName().equals(
                                "java.io.ObjectStreamField")
                        ) {
                    
                    if (member.isStatic() &&
                            member.isFinal() &&
                            member.isPrivate()) {
                        
                        // We have the correct signature

                        spfDef = member.getMemberDefinition();

                    } else {

                        // Bad signature...

                        failedConstraint(4,quiet,stack,getQualifiedName());
                        return false;
                    }
                }
            }
        }

        // If we do not have a serialPersistentField,
        // there's nothing to do, so return with no error...

        if (spfDef == null) {
            return true;
        }

        // Ok, now we must examine the contents of the array -
        // then validate them...

        Hashtable<String, String> fields = getPersistentFields(clz);
        boolean result = true;

        for (Member member : members) {
            String fieldName = member.getName();
            String fieldType = member.getType().getSignature();
            // Is this field present in the array?

            String type = fields.get(fieldName);
            if (type == null) {
                // No, so mark it transient...
                member.setTransient();
            } else {
                // Yes, does the type match?

                if (type.equals(fieldType)) {

                    // Yes, so remove it from the fields table...

                    fields.remove(fieldName);

                } else {

                    // No, so error...

                    result = false;
                    failedConstraint(2,quiet,stack,fieldName,getQualifiedName());
                }
            }
        }

        // Return result...

        return result;
    }

    /**
     * Get the names and types of all the persistent fields of a Class.
     */
    private Hashtable<String, String> getPersistentFields (Class<?> clz) {
        Hashtable<String, String> result = new Hashtable<>();
        ObjectStreamClass osc = ObjectStreamClass.lookup(clz);
        if (osc != null) {
            ObjectStreamField[] fields = osc.getFields();
            for (ObjectStreamField field : fields) {
                String typeSig;
                String typePrefix = String.valueOf(field.getTypeCode());
                if (field.isPrimitive()) {
                    typeSig = typePrefix;
                } else {
                    if (field.getTypeCode() == '[') {
                        typePrefix = "";
                    }
                    typeSig = typePrefix + field.getType().getName().replace('.', '/');
                    if (typeSig.endsWith(";")) {
                        typeSig = typeSig.substring(0,typeSig.length()-1);
                    }
                }
                result.put(field.getName(), typeSig);
            }
        }
        return result;
    }
}
