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

package corba.framework;

import java.util.Vector;
import java.util.Properties;

/**
 * Abstraction of a compiler, used to implement IDLJ, RMIC, and Javac
 * wrappers.
 */
public abstract class Compiler {
    /**
     * Compile the given files according to the other parameters.
     *
     * @param files Array of files to compile (assumes full paths)
     * @param arguments Arguments to the compiler
     * @param outputDirectory Directory in which to place generated files
     * @param reportDirectory Directory in which to place dump files of
     * the compiler's stdout and stderr
     * @throws Exception Error occured
     * (probably bad exit value)
     */
    public abstract void compile(String files[],
                                 Vector arguments,
                                 String outputDirectory,
                                 String reportDirectory) throws Exception;

    /**
     * Perform the compile in a separate process.  It's easier to do it
     * that way since the compiler's output streams can be dumped to files.
     * This waits for completion or a maximum timeout (defined in Options)
     *
     * @param className Name of the class of the compiler
     * @param progArgs Arguments to the compiler (including file names)
     * @param outputDirectory Directory in which to place generated files
     * @param reportDirectory Directory in which to place IO dumps
     * @param compilerName Identifying name of the compiler for the IO
     * files (to create "javac.err.txt", etc)
     * @throws Exception Exception  Error occured (probably bad exit value)
     */
    protected void compileExternally(String className,
                                     String[] progArgs,
                                     String outputDirectory,
                                     String reportDirectory,
                                     String compilerName) throws Exception {
        // Make certain the directories exist
        // Note: this must be done here as well as in the test harness
        // in case a test (like corba.codebase) changes the output directory
        // in the test itself!
        CORBAUtil.mkdir(outputDirectory);
        CORBAUtil.mkdir(reportDirectory);

        FileOutputDecorator exec
                = new FileOutputDecorator(new ExternalExec(false));

        Properties props = new Properties();
        int emmaPort = EmmaControl.setCoverageProperties(props);
        exec.initialize(className,
                        compilerName,
                        props,
                        null,
                        progArgs,
                        reportDirectory + compilerName + ".out.txt",
                        reportDirectory + compilerName + ".err.txt",
                        null,
                        emmaPort);

        exec.start();
        int result = 1;

        try {

            result = exec.waitFor(Options.getMaximumTimeout());

        } catch (Exception e) {
            exec.stop();
            throw e;
        }

        if (result != Controller.SUCCESS) {
            throw new Exception(compilerName
                                        + " compile failed with result: "
                                        + result);
        }

        exec.stop();
    }
}
