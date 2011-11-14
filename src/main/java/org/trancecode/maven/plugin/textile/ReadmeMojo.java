/*
 * Copyright 2011 Herve Quiroz
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.trancecode.maven.plugin.textile;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Plugin Mojo to format the {@code README.textile} file at the root of the
 * project.
 * 
 * @phase compile
 * @goal readme
 * @threadSafe true
 * @author Herve Quiroz
 */
public final class ReadmeMojo extends AbstractMojo
{
    /**
     * Specifies the README file to format.
     * 
     * @parameter expression="${basedir}/README.textile"
     */
    private File sourceFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        try
        {
            if (sourceFile.exists())
            {
                final File actualSourceFile = sourceFile.getCanonicalFile();
                final String resultPath = actualSourceFile.getAbsolutePath().replaceAll("\\.[^/\\.]*$", ".html");
                final File resultFile = new File(resultPath);
                getLog().info(String.format("%s -> %s", actualSourceFile, resultFile));
                Textile.format(sourceFile, resultFile);
            }
        }
        catch (final IOException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
