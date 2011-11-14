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
import java.net.URI;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;

/**
 * Plugin Mojo to format Textile files into documents.
 * 
 * @phase compile
 * @goal format
 * @threadSafe true
 * @author Herve Quiroz
 */
public final class FormatMojo extends AbstractMojo
{
    /**
     * Specifies the directory where the generated files will be put.
     * 
     * @parameter expression="${project.build.directory}/textile"
     */
    private File outputDirectory;

    /**
     * Specifies the directory where the source files are located.
     * 
     * @parameter expression="${project.build.sourceDirectory}/../textile"
     */
    private File sourceDirectory;

    /**
     * List of paths and filters to be included as source documents.
     * 
     * @parameter expression="*.textile"
     */
    private String includes;

    /**
     * List of paths and filters to be excluded from source documents.
     * 
     * @parameter expression=""
     */
    private String excludes;

    /**
     * Pattern to name result documents.
     * 
     * @parameter expression="${basename}.html"
     */
    private String resultNamePattern;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        try
        {
            getLog().info("source directory: " + sourceDirectory.getCanonicalFile());
            final URI sourceDirectoryUri = sourceDirectory.getCanonicalFile().toURI();
            getLog().debug("  ... sourceDirectoryUri = " + sourceDirectoryUri);
            getLog().info("output directory: " + outputDirectory.getCanonicalFile());
            outputDirectory.mkdirs();
            final URI outputDirectoryUri = outputDirectory.getCanonicalFile().toURI();
            getLog().debug("  ... outputDirectoryUri = " + outputDirectoryUri);
            if (!sourceDirectory.exists())
            {
                return;
            }
            @SuppressWarnings("unchecked")
            final List<File> sourceFiles = FileUtils.getFiles(sourceDirectory.getCanonicalFile(), includes, excludes);
            for (final File sourceFile : sourceFiles)
            {
                getLog().debug("sourceFile = " + sourceFile);
                final String sourcePath = sourceDirectoryUri.relativize(sourceFile.toURI()).getPath();
                getLog().debug("  ... sourcePath = " + sourcePath);
                final String basename = sourcePath.replaceAll("\\.[^/\\.]*$", "");
                getLog().debug("  ... basename = " + basename);
                final String resultPath = resultNamePattern.replace("${basename}", basename);
                getLog().debug("  ... resultPath = " + resultPath);
                final URI resultDocumentUri = outputDirectory.getCanonicalFile().toURI().resolve(resultPath);
                getLog().debug("  ... resultDocumentUri = " + resultDocumentUri);
                final File resultFile = new File(resultDocumentUri);
                getLog().info(String.format("%s -> %s", sourceFile, resultFile));
                Textile.format(sourceFile, resultFile);
            }
        }
        catch (final IOException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
