/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 * Copyright (c) 1997-1999 IBM Corp. All rights reserved.
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

package com.sun.tools.corba.ee.idl.toJavaPortable;

// NOTES:
// -cast() does not support longlong types yet.
// -Deal with typedef changes.
// -Scoped names for the discriminator are ignored at the moment.
// -F46082.51<daz> Remove -stateful feature; javaStatefulName() obsolete.
// -D61056   <klr> Use Util.helperName

import com.sun.tools.corba.ee.idl.*;
import com.sun.tools.corba.ee.idl.constExpr.EvaluationException;
import com.sun.tools.corba.ee.idl.constExpr.Expression;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 **/
public class UnionGen implements com.sun.tools.corba.ee.idl.UnionGen, com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator {
    /**
     * Public zero-argument constructor.
     **/
    public UnionGen() {
    } // ctor

    @Override
    public void generate(Hashtable symbolTable, UnionEntry u, PrintWriter s) {
        this.symbolTable = symbolTable;
        this.u = u;
        init();

        openStream();
        if (stream == null) {
            return;
        }
        generateHelper();
        generateHolder();
        writeHeading();
        writeBody();
        writeClosing();
        closeStream();
        generateContainedTypes();
    } // generate

    /**
     * Initialize members unique to this generator.
     **/
    protected void init() {
        utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        unionIsEnum = utype instanceof EnumEntry;
    } // init

    /**
     *
     **/
    protected void openStream() {
        stream = com.sun.tools.corba.ee.idl.toJavaPortable.Util.stream(u, ".java");
    } // openStream

    /**
     *
     **/
    protected void generateHelper() {
        ((Factories) com.sun.tools.corba.ee.idl.toJavaPortable.Compile.compiler.factories()).helper().generate(symbolTable, u);
    } // generateHelper

    /**
     *
     **/
    protected void generateHolder() {
        ((Factories) com.sun.tools.corba.ee.idl.toJavaPortable.Compile.compiler.factories()).holder().generate(symbolTable, u);
    } // generateHolder

    /**
     *
     **/
    protected void writeHeading() {
        // If the discriminator is an enum, assign the typePackage string.
        if (unionIsEnum) {
            typePackage = com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaQualifiedName(utype) + '.';
        } else {
            typePackage = "";
        }

        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writePackage(stream, u);
        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writeProlog(stream, ((GenFileStream) stream).name());

        String className = u.name();
        stream.println("public final class " + u.name() + " implements org.omg.CORBA.portable.IDLEntity");
        stream.println("{");
    } // writeHeading

    /**
     *
     **/
    protected void writeBody() {
        // Write branches and populate quality arrays
        int size = u.branches().size() + 1;
        Enumeration e = u.branches().elements();
        int i = 0;
        while (e.hasMoreElements()) {
            UnionBranch branch = (UnionBranch) e.nextElement();
            com.sun.tools.corba.ee.idl.toJavaPortable.Util.fillInfo(branch.typedef);
            // <f46082.51> Remove -stateful feature; javaStatefulName() obsolete.
            //stream.println ("  private " + Util.javaStatefulName (branch.typedef) + " ___" + branch.typedef.name () + ";");
            stream.println("  private " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(branch.typedef) + " ___" + branch.typedef.name() + ";");
            ++i;
        }
        stream.println("  private " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) + " __discriminator;");
        stream.println("  private boolean __uninitialized = true;");

        // Write ctor
        stream.println();
        stream.println("  public " + u.name() + " ()");
        stream.println("  {");
        stream.println("  }");

        // Write discriminator
        stream.println();
        stream.println("  public " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) + " " + safeName(u, "discriminator") + " ()");
        stream.println("  {");
        stream.println("    if (__uninitialized)");
        stream.println("      throw new org.omg.CORBA.BAD_OPERATION ();");
        stream.println("    return __discriminator;");
        stream.println("  }");

        // Write for each branch:
        // - setter
        // - getter
        // - private verifyXXX
        e = u.branches().elements();
        i = 0;
        while (e.hasMoreElements()) {
            UnionBranch branch = (UnionBranch) e.nextElement();
            writeBranchMethods(stream, u, branch, i++);
        }
        if (u.defaultBranch() == null && !coversAll(u)) {
            stream.println();
            stream.println("  public void _default ()");
            stream.println("  {");
            stream.println("    __discriminator = " + defaultDiscriminator(u) + ';');
            stream.println("    __uninitialized = false;");
            stream.println("  }");

            stream.println();
            stream.println("  public void _default (" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) +
                                   " discriminator)");
            stream.println("  {");
            stream.println("    verifyDefault( discriminator ) ;");
            stream.println("    __discriminator = discriminator ;");
            stream.println("    __uninitialized = false;");
            stream.println("  }");

            writeVerifyDefault();
        }
        stream.println();
    } // writeBody

    /**
     *
     **/
    protected void writeClosing() {
        stream.println("} // class " + u.name());
    } // writeClosing

    /**
     *
     **/
    protected void closeStream() {
        stream.close();
    } // closeStream

    /**
     *
     **/
    protected void generateContainedTypes() {
        Enumeration e = u.contained().elements();
        while (e.hasMoreElements()) {
            SymtabEntry entry = (SymtabEntry) e.nextElement();

            // Don't generate contained entries if they are sequences.
            // Sequences are unnamed and since they translate to arrays,
            // no classes are generated for them, not even holders in this
            // case since they cannot be accessed outside of this union.
            if (!(entry instanceof SequenceEntry)) {
                entry.generate(symbolTable, stream);
            }
        }
    } // generateContainedTypes

    private void writeVerifyDefault() {
        Vector labels = vectorizeLabels(u.branches(), true);

        // #hd# bugfix for http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4504275 from
        // https://www.programcreek.com/java-api-examples/?code=jboss/openjdk-orb/openjdk-orb-master/src/share/classes/com/sun/tools/corba/se/idl/toJavaPortable/UnionGen.java
        if (Util.javaName(utype).equals("boolean")) {
            stream.println("");
            stream.println("  private void verifyDefault (boolean discriminator)");
            stream.println("  {");
            if (labels.contains("true")) {
                stream.println("    if ( discriminator )");
            } else {
                stream.println("    if ( !discriminator )");
            }
            stream.println("        throw new org.omg.CORBA.BAD_OPERATION();");
            stream.println("  }");
            return;
        }

        stream.println("");
        stream.println("  private void verifyDefault( " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) +
                               " value )");
        stream.println("  {");

        if (unionIsEnum) {
            stream.println("    switch (value.value()) {");
        } else {
            stream.println("    switch (value) {");
        }

        Enumeration e = labels.elements();
        while (e.hasMoreElements()) {
            String str = (String) (e.nextElement());
            stream.println("      case " + str + ":");
        }

        stream.println("        throw new org.omg.CORBA.BAD_OPERATION() ;");
        stream.println("");
        stream.println("      default:");
        stream.println("        return;");
        stream.println("    }");
        stream.println("  }");
    }

    private String defaultDiscriminator(UnionEntry u) {
        Vector labels = vectorizeLabels(u.branches(), false);
        String ret = null;
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        if (utype instanceof PrimitiveEntry && utype.name().equals("boolean")) {
            // If it got this far, then:
            // - there is only one branch;
            // - that branch has only one label.
            if (labels.contains("true")) {
                ret = "false";
            } else {
                ret = "true";
            }
        } else if (utype.name().equals("char")) {
            // This doesn't handle '\u0030' == '0'.  Unions are so
            // seldom used.  I don't have time to make this perfect.
            int def = 0;
            String string = "'\\u0000'";
            while (def != 0xFFFF && labels.contains(string)) {
                if (++def / 0x10 == 0) {
                    string = "'\\u000" + def + "'";
                } else if (def / 0x100 == 0) {
                    string = "\\u00" + def + "'";
                } else if (def / 0x1000 == 0) {
                    string = "\\u0" + def + "'";
                } else {
                    string = "\\u" + def + "'";
                }
            }
            ret = string;
        } else if (utype instanceof EnumEntry) {
            Enumeration e = labels.elements();
            EnumEntry enumEntry = (EnumEntry) utype;
            Vector enumList = (Vector) enumEntry.elements().clone();
            // cull out those elements in the enumeration list that are
            // in the cases of this union
            while (e.hasMoreElements()) {
                enumList.removeElement(e.nextElement());
            }
            // If all of the enum elements are covered in this union and
            // there is a default statement, just pick one of the
            // elements for the default.  If there are enum elements
            // which are NOT covered by the cases, pick one as the
            // default.
            if (enumList.size() == 0) {
                ret = typePackage + (String) enumEntry.elements().lastElement();
            } else {
                ret = typePackage + (String) enumList.firstElement();
            }
        } else if (utype.name().equals("octet")) {
            short def = Byte.MIN_VALUE;
            while (def != Byte.MAX_VALUE && labels.contains(Integer.toString(def))) {
                ++def;
            }
            ret = Integer.toString(def);
        } else if (utype.name().equals("short")) {
            short def = Short.MIN_VALUE;
            while (def != Short.MAX_VALUE && labels.contains(Integer.toString(def))) {
                ++def;
            }
            ret = Integer.toString(def);
        } else if (utype.name().equals("long")) {
            int def = Integer.MIN_VALUE;
            while (def != Integer.MAX_VALUE && labels.contains(Integer.toString(def))) {
                ++def;
            }
            ret = Integer.toString(def);
        } else if (utype.name().equals("long long")) {
            long def = Long.MIN_VALUE;
            while (def != Long.MAX_VALUE && labels.contains(Long.toString(def))) {
                ++def;
            }
            ret = Long.toString(def);
        } else if (utype.name().equals("unsigned short")) {
            short def = 0;
            while (def != Short.MAX_VALUE && labels.contains(Integer.toString(def))) {
                ++def;
            }
            ret = Integer.toString(def);
        } else if (utype.name().equals("unsigned long")) {
            int def = 0;
            while (def != Integer.MAX_VALUE && labels.contains(Integer.toString(def))) {
                ++def;
            }
            ret = Integer.toString(def);
        } else if (utype.name().equals("unsigned long long")) {
            long def = 0;
            while (def != Long.MAX_VALUE && labels.contains(Long.toString(def))) {
                ++def;
            }
            ret = Long.toString(def);
        }

        return ret;
    } // defaultDiscriminator

    /**
     *
     **/
    private Vector vectorizeLabels(Vector branchVector, boolean useIntsForEnums) {
        Vector mergedLabels = new Vector();
        Enumeration branches = branchVector.elements();
        while (branches.hasMoreElements()) {
            UnionBranch branch = (UnionBranch) branches.nextElement();
            Enumeration labels = branch.labels.elements();
            while (labels.hasMoreElements()) {
                Expression expr = (Expression) labels.nextElement();
                String str;

                if (unionIsEnum) {
                    if (useIntsForEnums) {
                        str = typePackage + "_" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(expr);
                    } else {
                        str = typePackage + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(expr);
                    }
                } else {
                    str = com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(expr);
                }

                mergedLabels.addElement(str);
            }
        }
        return mergedLabels;
    } // vectorizeLabels

    /**
     *
     **/
    private String safeName(UnionEntry u, String name) {
        Enumeration e = u.branches().elements();
        while (e.hasMoreElements()) {
            if (((UnionBranch) e.nextElement()).typedef.name().equals(name)) {
                name = '_' + name;
                break;
            }
        }
        return name;
    } // safeName

    /**
     *
     **/
    private boolean coversAll(UnionEntry u) {
        // This assumes that it is not possible to cover types other than
        // boolean and enums.  This is not quite correct, but since octet
        // is not a valid discriminator type, it's not too bad in practice.
        // It may also be possible to cover a char type, but we won't worry
        // about that either.
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());

        boolean coversAll = false;
        if (utype.name().equals("boolean")) {
            if (u.branches().size() == 2) {
                coversAll = true;
            }
        } else if (utype instanceof EnumEntry) {
            Vector labels = vectorizeLabels(u.branches(), true);
            if (labels.size() == ((EnumEntry) utype).elements().size()) {
                coversAll = true;
            }
        }

        return coversAll;
    } // coversAll

    /**
     *
     **/
    private void writeBranchMethods(PrintWriter stream, UnionEntry u, UnionBranch branch, int i) {
        // Write getter
        stream.println();
        // <f46082.51> Remove -stateful feature; javaStatefulName() obsolete.
        //stream.println ("  public " + Util.javaStatefulName (branch.typedef) + " " + branch.typedef.name () + " ()");
        stream.println("  public " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(branch.typedef) + " " + branch.typedef.name() + " ()");
        stream.println("  {");
        stream.println("    if (__uninitialized)");
        stream.println("      throw new org.omg.CORBA.BAD_OPERATION ();");
        stream.println("    verify" + branch.typedef.name() + " (__discriminator);");
        stream.println("    return ___" + branch.typedef.name() + ";");
        stream.println("  }");

        // Write setter(s)
        stream.println();
        // <f46082.51> Remove -stateful feature; javaStatefulName() obsolete.
        //stream.println ("  public void " + branch.typedef.name () + " (" + Util.javaStatefulName (branch.typedef) + " value)");
        stream.println("  public void " + branch.typedef.name() + " (" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(branch.typedef) + " value)");
        stream.println("  {");
        if (branch.labels.size() == 0) {
            // This is a default branch
            stream.println("    __discriminator = " + defaultDiscriminator(u) + ";");
        } else {
            // This is a non-default branch
            if (unionIsEnum) {
                stream.println("    __discriminator = " + typePackage + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(
                        (Expression) branch.labels.firstElement()) + ";");
            } else {
                stream.println("    __discriminator = " + cast((Expression) branch.labels.firstElement(), u.type()) + ";");
            }
        }
        stream.println("    ___" + branch.typedef.name() + " = value;");
        stream.println("    __uninitialized = false;");
        stream.println("  }");

        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());

        // If there are multiple labels for one branch, write the
        // setter that takes a discriminator.
        if (branch.labels.size() > 0 || branch.isDefault) {
            stream.println();
            // <f46082.51> Remove -stateful feature; javaStatefulName() obsolete.
            //stream.println ("  public void " + branch.typedef.name () + " (" + Util.javaName (utype) + " discriminator, " + Util.javaStatefulName (branch.typedef) + " value)");
            stream.println("  public void " + branch.typedef.name() + " (" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) + " discriminator, "
                                   + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(branch.typedef) + " value)");
            stream.println("  {");
            stream.println("    verify" + branch.typedef.name() + " (discriminator);");
            stream.println("    __discriminator = discriminator;");
            stream.println("    ___" + branch.typedef.name() + " = value;");
            stream.println("    __uninitialized = false;");
            stream.println("  }");
        }

        // Write verifyXXX
        stream.println();
        stream.println(
                "  private void verify" + branch.typedef.name() + " (" + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) + " discriminator)");
        stream.println("  {");

        boolean onlyOne = true;

        if (branch.isDefault && u.branches().size() == 1)
            ;// If all that is in this union is a default branch,
            // all discriminators are legal.  Don't print any
            // body to this method in that case.
        else {
            // Otherwise this code is executed and a body is printed.
            stream.print("    if (");
            if (branch.isDefault) {
                Enumeration eBranches = u.branches().elements();
                while (eBranches.hasMoreElements()) {
                    UnionBranch b = (UnionBranch) eBranches.nextElement();
                    if (b != branch) {
                        Enumeration eLabels = b.labels.elements();
                        while (eLabels.hasMoreElements()) {
                            Expression label = (Expression) eLabels.nextElement();
                            if (!onlyOne) {
                                stream.print(" || ");
                            }
                            if (unionIsEnum) {
                                stream.print("discriminator == " + typePackage + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label));
                            } else {
                                stream.print("discriminator == " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label));
                            }
                            onlyOne = false;
                        }
                    }
                }
            } else {
                Enumeration e = branch.labels.elements();
                while (e.hasMoreElements()) {
                    Expression label = (Expression) e.nextElement();
                    if (!onlyOne) {
                        stream.print(" && ");
                    }
                    if (unionIsEnum) {
                        stream.print("discriminator != " + typePackage + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label));
                    } else {
                        stream.print("discriminator != " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label));
                    }
                    onlyOne = false;
                }
            }
            stream.println(")");
            stream.println("      throw new org.omg.CORBA.BAD_OPERATION ();");
        }
        stream.println("  }");
    } // writeBranchMethods

    ///////////////
    // From JavaGenerator

    /**
     *
     **/

    // Computes the total number of labels in the union, which is the sum
    // of the number of labels in each branch of the union.  Note that the
    // label for the default branch has size 0, but still counts in the total
    // size.
    private int unionLabelSize(UnionEntry un) {
        int size = 0;
        Vector branches = un.branches();
        for (int i = 0; i < branches.size(); ++i) {
            UnionBranch branch = (UnionBranch) (branches.get(i));
            int branchSize = branch.labels.size();
            size += ((branchSize == 0) ? 1 : branchSize);
        }
        return size;
    }

    public int helperType(int index, String indent, com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets tcoffsets,
                          String name, SymtabEntry entry, PrintWriter stream) {
        com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets innerOffsets = new com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets();
        UnionEntry u = (UnionEntry) entry;
        String discTypeCode = "_disTypeCode" + index;
        String membersName = "_members" + index;

        // Build discriminator tc
        stream.println(indent + "org.omg.CORBA.TypeCode " + discTypeCode + ';');
        index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) u.type().generator()).type(index + 1, indent,
                                                                                                      innerOffsets, discTypeCode, u.type(), stream);
        tcoffsets.bumpCurrentOffset(innerOffsets.currentOffset());

        stream.println(indent + "org.omg.CORBA.UnionMember[] " + membersName +
                               " = new org.omg.CORBA.UnionMember [" + unionLabelSize(u) + "];");
        String tcOfMembers = "_tcOf" + membersName;
        String anyOfMembers = "_anyOf" + membersName;
        stream.println(indent + "org.omg.CORBA.TypeCode " + tcOfMembers + ';');
        stream.println(indent + "org.omg.CORBA.Any " + anyOfMembers + ';');

        innerOffsets = new com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets();
        innerOffsets.set(entry);
        int offsetForUnion = innerOffsets.currentOffset();
        for (int i = 0; i < u.branches().size(); ++i) {
            UnionBranch branch = (UnionBranch) u.branches().elementAt(i);
            TypedefEntry member = branch.typedef;
            Vector labels = branch.labels;
            String memberName = com.sun.tools.corba.ee.idl.toJavaPortable.Util.stripLeadingUnderscores(member.name());

            if (labels.isEmpty()) {
                stream.println();
                stream.println(indent + "// Branch for " + memberName + " (Default case)");
                SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
                stream.println(indent + anyOfMembers + " = org.omg.CORBA.ORB.init ().create_any ();");
                // For default member, label is the zero octet (per CORBA spec.)
                stream.println(indent + anyOfMembers + ".insert_octet ((byte)0); // default member label");

                // Build typecode
                innerOffsets.bumpCurrentOffset(4); // label value
                index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) member.generator()).type(index, indent, innerOffsets, tcOfMembers, member,
                                                                                                            stream);
                int offsetSoFar = innerOffsets.currentOffset();
                innerOffsets = new com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets();
                innerOffsets.set(entry);
                innerOffsets.bumpCurrentOffset(offsetSoFar - offsetForUnion);

                // Build union member
                stream.println(indent + membersName + '[' + i + "] = new org.omg.CORBA.UnionMember (");
                stream.println(indent + "  \"" + memberName + "\",");
                stream.println(indent + "  " + anyOfMembers + ',');
                stream.println(indent + "  " + tcOfMembers + ',');
                stream.println(indent + "  null);");
            } else {
                Enumeration<Expression> enumeration = labels.elements();
                while (enumeration.hasMoreElements()) {
                    Expression expr = enumeration.nextElement();
                    String elem = com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(expr);

                    stream.println();
                    stream.println(indent + "// Branch for " + memberName +
                                           " (case label " + elem + ")");

                    SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());

                    // Build any
                    stream.println(indent + anyOfMembers + " = org.omg.CORBA.ORB.init ().create_any ();");

                    if (utype instanceof PrimitiveEntry) {
                        stream.println(indent + anyOfMembers + ".insert_" +
                                               com.sun.tools.corba.ee.idl.toJavaPortable.Util.collapseName(utype.name()) + " (("
                                               + com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype) +
                                               ')' + elem + ");");
                    } else { // it must be enum
                        String enumClass = com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaName(utype);
                        stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(utype, false) + ".insert (" +
                                               anyOfMembers + ", " + enumClass + '.' + elem + ");"); // <d61056>
                    }

                    // Build typecode
                    innerOffsets.bumpCurrentOffset(4); // label value
                    index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) member.generator()).type(index, indent, innerOffsets, tcOfMembers,
                                                                                                                member, stream);
                    int offsetSoFar = innerOffsets.currentOffset();
                    innerOffsets = new com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets();
                    innerOffsets.set(entry);
                    innerOffsets.bumpCurrentOffset(offsetSoFar - offsetForUnion);

                    // Build union member
                    stream.println(indent + membersName + '[' + i + "] = new org.omg.CORBA.UnionMember (");
                    stream.println(indent + "  \"" + memberName + "\",");
                    stream.println(indent + "  " + anyOfMembers + ',');
                    stream.println(indent + "  " + tcOfMembers + ',');
                    stream.println(indent + "  null);");
                }
            }
        }

        tcoffsets.bumpCurrentOffset(innerOffsets.currentOffset());

        // Build create_union_tc
        stream.println(indent + name + " = org.omg.CORBA.ORB.init ().create_union_tc (" +
                               com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(u, true) + ".id (), \"" + entry.name() + "\", " +
                               discTypeCode + ", " + membersName + ");");
        return index;
    } // helperType

    @Override
    public int type(int index, String indent, com.sun.tools.corba.ee.idl.toJavaPortable.TCOffsets tcoffsets, String name,
                    SymtabEntry entry, PrintWriter stream) {
        stream.println(indent + name + " = " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(entry, true) + ".type ();");
        return index;
    }

    @Override
    public void helperRead(String entryName, SymtabEntry entry, PrintWriter stream) {
        stream.println("    " + entryName + " value = new " + entryName + " ();");
        read(0, "    ", "value", entry, stream);
        stream.println("    return value;");
    }

    @Override
    public void helperWrite(SymtabEntry entry, PrintWriter stream) {
        write(0, "    ", "value", entry, stream);
    }

    @Override
    public int read(int index, String indent, String name,
                    SymtabEntry entry, PrintWriter stream) {
        UnionEntry u = (UnionEntry) entry;
        String disName = "_dis" + index++;
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writeInitializer(indent, disName, "", utype, stream);

        if (utype instanceof PrimitiveEntry) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) utype.generator()).read(index, indent, disName, utype, stream);
        } else {
            stream.println(indent + disName + " = " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(utype, true) + ".read (istream);");
        }

        if (utype.name().equals("boolean")) {
            index = readBoolean(disName, index, indent, name, u, stream);
        } else {
            index = readNonBoolean(disName, index, indent, name, u, stream);
        }

        return index;
    }

    private int readBoolean(String disName, int index, String indent,
                            String name, UnionEntry u, PrintWriter stream) {
        UnionBranch firstBranch = (UnionBranch) u.branches().firstElement();
        UnionBranch secondBranch;

        if (u.branches().size() == 2) {
            secondBranch = (UnionBranch) u.branches().lastElement();
        } else {
            secondBranch = null;
        }

        boolean firstBranchIsTrue = false;
        boolean noCases = false;
        try {
            if (u.branches().size() == 1 &&
                    (u.defaultBranch() != null || firstBranch.labels.size() == 2)) {
                noCases = true;
            } else {
                Expression expr = (firstBranch.labels.firstElement());
                Boolean bool = (Boolean) (expr.evaluate());
                firstBranchIsTrue = bool;
            }
        } catch (EvaluationException ex) {
            // no action
        }

        if (noCases) {
            // There is only a default label.  Since there are no cases,
            // there is no need for if...else branches.
            index = readBranch(index, indent, firstBranch.typedef.name(), "", firstBranch.typedef, stream);
        } else {
            // If first branch is false, swap branches
            if (!firstBranchIsTrue) {
                UnionBranch tmp = firstBranch;
                firstBranch = secondBranch;
                secondBranch = tmp;
            }

            stream.println(indent + "if (" + disName + ')');

            if (firstBranch == null) {
                stream.println(indent + "  value._default(" + disName + ");");
            } else {
                stream.println(indent + '{');
                index = readBranch(index, indent + "  ", firstBranch.typedef.name(),
                                   disName, firstBranch.typedef, stream);
                stream.println(indent + '}');
            }

            stream.println(indent + "else");

            if (secondBranch == null) {
                stream.println(indent + "  value._default(" + disName + ");");
            } else {
                stream.println(indent + '{');
                index = readBranch(index, indent + "  ", secondBranch.typedef.name(),
                                   disName, secondBranch.typedef, stream);
                stream.println(indent + '}');
            }
        }

        return index;
    }

    private int readNonBoolean(String disName, int index, String indent,
                               String name, UnionEntry u, PrintWriter stream) {
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());

        if (utype instanceof EnumEntry) {
            stream.println(indent + "switch (" + disName + ".value ())");
        } else {
            stream.println(indent + "switch (" + disName + ')');
        }

        stream.println(indent + '{');
        String typePackage = com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaQualifiedName(utype) + '.';

        Enumeration e = u.branches().elements();
        while (e.hasMoreElements()) {
            UnionBranch branch = (UnionBranch) e.nextElement();
            Enumeration<Expression> labels = branch.labels.elements();

            while (labels.hasMoreElements()) {
                Expression label = labels.nextElement();

                if (utype instanceof EnumEntry) {
                    String key = com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label);
                    stream.println(indent + "  case " + typePackage + '_' + key + ':');
                } else {
                    stream.println(indent + "  case " + cast(label, utype) + ':');
                }
            }

            if (!branch.typedef.equals(u.defaultBranch())) {
                index = readBranch(index, indent + "    ", branch.typedef.name(),
                                   branch.labels.size() > 1 ? disName : "",
                                   branch.typedef, stream);
                stream.println(indent + "    break;");
            }
        }

        // We need a default branch unless all of the case of the discriminator type
        // are listed in the case branches.
        if (!coversAll(u)) {
            stream.println(indent + "  default:");

            if (u.defaultBranch() == null) {
                // If the union does not have a default branch, we still need to initialize
                // the discriminator.
                stream.println(indent + "    value._default( " + disName + " ) ;");
            } else {
                index = readBranch(index, indent + "    ", u.defaultBranch().name(), disName,
                                   u.defaultBranch(), stream);
            }

            stream.println(indent + "    break;");
        }

        stream.println(indent + '}');

        return index;
    }

    private int readBranch(int index, String indent, String name, String disName, TypedefEntry entry, PrintWriter stream) {
        SymtabEntry type = entry.type();
        com.sun.tools.corba.ee.idl.toJavaPortable.Util.writeInitializer(indent, '_' + name, "", entry, stream);

        if (!entry.arrayInfo().isEmpty() ||
                type instanceof SequenceEntry ||
                type instanceof PrimitiveEntry ||
                type instanceof StringEntry) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) entry.generator()).read(index, indent, '_' + name, entry, stream);
        } else {
            stream.println(indent + '_' + name + " = " + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(type, true) + ".read (istream);");
        }

        stream.print(indent + "value." + name + " (");
        if (disName == "") {
            stream.println("_" + name + ");");
        } else {
            stream.println(disName + ", " + "_" + name + ");");
        }

        return index;
    }

    /**
     *
     **/
    public int write(int index, String indent, String name, SymtabEntry entry, PrintWriter stream) {
        UnionEntry u = (UnionEntry) entry;
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        if (utype instanceof PrimitiveEntry) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) utype.generator()).write(index, indent, name + ".discriminator ()", utype,
                                                                                                        stream);
        } else {
            stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(utype, true) + ".write (ostream, " + name
                                   + ".discriminator ());"); // <d61056>
        }
        if (utype.name().equals("boolean")) {
            index = writeBoolean(name + ".discriminator ()", index, indent, name, u, stream);
        } else {
            index = writeNonBoolean(name + ".discriminator ()", index, indent, name, u, stream);
        }
        return index;
    } // write

    /**
     *
     **/
    private int writeBoolean(String disName, int index, String indent, String name, UnionEntry u, PrintWriter stream) {
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        UnionBranch firstBranch = (UnionBranch) u.branches().firstElement();
        UnionBranch secondBranch;
        if (u.branches().size() == 2) {
            secondBranch = (UnionBranch) u.branches().lastElement();
        } else {
            secondBranch = null;
        }
        boolean firstBranchIsTrue = false;
        boolean noCases = false;
        try {
            if (u.branches().size() == 1 && (u.defaultBranch() != null || firstBranch.labels.size() == 2)) {
                noCases = true;
            } else {
                firstBranchIsTrue = ((Boolean) ((Expression) firstBranch.labels.firstElement()).evaluate()).booleanValue();
            }
        } catch (EvaluationException ex) {
        }

        if (noCases) {
            // There is only a default label.  Since there are no cases,
            // there is no need for if...else branches.
            index = writeBranch(index, indent, name, firstBranch.typedef, stream);
        } else {
            // If first branch is false, swap branches
            if (!firstBranchIsTrue) {
                UnionBranch tmp = firstBranch;
                firstBranch = secondBranch;
                secondBranch = tmp;
            }
            if (firstBranch != null && secondBranch != null) {
                stream.println(indent + "if (" + disName + ')');
                stream.println(indent + '{');
                index = writeBranch(index, indent + "  ", name, firstBranch.typedef, stream);
                stream.println(indent + '}');
                stream.println(indent + "else");
                stream.println(indent + '{');
                index = writeBranch(index, indent + "  ", name, secondBranch.typedef, stream);
                stream.println(indent + '}');
            } else if (firstBranch != null) {
                stream.println(indent + "if (" + disName + ')');
                stream.println(indent + '{');
                index = writeBranch(index, indent + "  ", name, firstBranch.typedef, stream);
                stream.println(indent + '}');
            } else {
                stream.println(indent + "if (!" + disName + ')');
                stream.println(indent + '{');
                index = writeBranch(index, indent + "  ", name, secondBranch.typedef, stream);
                stream.println(indent + '}');
            }
        }
        return index;
    } // writeBoolean

    /**
     *
     **/
    private int writeNonBoolean(String disName, int index, String indent, String name, UnionEntry u, PrintWriter stream) {
        SymtabEntry utype = com.sun.tools.corba.ee.idl.toJavaPortable.Util.typeOf(u.type());
        if (utype instanceof EnumEntry) {
            stream.println(indent + "switch (" + name + ".discriminator ().value ())");
        } else {
            stream.println(indent + "switch (" + name + ".discriminator ())");
        }
        stream.println(indent + "{");
        String typePackage = com.sun.tools.corba.ee.idl.toJavaPortable.Util.javaQualifiedName(utype) + '.';
        Enumeration e = u.branches().elements();
        while (e.hasMoreElements()) {
            UnionBranch branch = (UnionBranch) e.nextElement();
            Enumeration labels = branch.labels.elements();
            while (labels.hasMoreElements()) {
                Expression label = (Expression) labels.nextElement();
                if (utype instanceof EnumEntry) {
                    String key = com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(label);
                    stream.println(indent + "  case " + typePackage + '_' + key + ":");
                } else {
                    stream.println(indent + "  case " + cast(label, utype) + ':');
                }
            }
            if (!branch.typedef.equals(u.defaultBranch())) {
                index = writeBranch(index, indent + "    ", name, branch.typedef, stream);
                stream.println(indent + "    break;");
            }
        }
        if (u.defaultBranch() != null) {
            stream.println(indent + "  default:");
            index = writeBranch(index, indent + "    ", name, u.defaultBranch(), stream);
            stream.println(indent + "    break;");
        }
        stream.println(indent + "}");
        return index;
    } // writeNonBoolean

    /**
     *
     **/
    private int writeBranch(int index, String indent, String name, TypedefEntry entry, PrintWriter stream) {
        SymtabEntry type = entry.type();
        if (!entry.arrayInfo().isEmpty() || type instanceof SequenceEntry || type instanceof PrimitiveEntry || type instanceof StringEntry) {
            index = ((com.sun.tools.corba.ee.idl.toJavaPortable.JavaGenerator) entry.generator()).write(index, indent, name + '.' + entry.name() + " ()", entry,
                                                                                                        stream);
        } else {
            stream.println(indent + com.sun.tools.corba.ee.idl.toJavaPortable.Util.helperName(type, true) + ".write (ostream, " + name + '.' + entry.name()
                                   + " ());"); // <d61056>
        }
        return index;
    } // writeBranch

    // From JavaGenerator
    ///////////////

    /**
     *
     **/
    private String cast(Expression expr, SymtabEntry type) {
        String ret = com.sun.tools.corba.ee.idl.toJavaPortable.Util.parseExpression(expr);
        if (type.name().indexOf("short") >= 0) {
            if (expr.value() instanceof Long) {
                long value = ((Long) expr.value()).longValue();
                if (value > Short.MAX_VALUE) {
                    ret = "(short)(" + ret + ')';
                }
            } else if (expr.value() instanceof Integer) {
                int value = ((Integer) expr.value()).intValue();
                if (value > Short.MAX_VALUE) {
                    ret = "(short)(" + ret + ')';
                }
            }
        } else if (type.name().contains("long")) {
            if (expr.value() instanceof Long) {
                long value = ((Long) expr.value());
                // value == Integer.MIN_VALUE because if the number is
                // Integer.MIN_VALUE, then it will have the 'L' suffix and
                // the cast will be necessary.
                if (value > Integer.MAX_VALUE || value == Integer.MIN_VALUE) {
                    ret = "(int)(" + ret + ')';
                }
            } else if (expr.value() instanceof Integer) {
                int value = ((Integer) expr.value());
                // value == Integer.MIN_VALUE because if the number is
                // Integer.MIN_VALUE, then it will have the 'L' suffix and
                // the cast will be necessary.
                if (value > Integer.MAX_VALUE || value == Integer.MIN_VALUE) {
                    ret = "(int)(" + ret + ')';
                }
            }
        }
        return ret;
    } // cast

    protected Hashtable symbolTable = null;
    protected UnionEntry u = null;
    protected PrintWriter stream = null;
    protected SymtabEntry utype = null;
    protected boolean unionIsEnum;
    protected String typePackage = "";
} // class UnionGen
