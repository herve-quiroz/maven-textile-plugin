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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.textile.TextileDialect;

/**
 * Utility methods related to Textile formatting.
 * 
 * @author Herve Quiroz
 */
public final class Textile
{
    private Textile()
    {
        // No instantiation
    }

    public static void format(final File sourceFile, final File resultFile) throws IOException
    {
        final Writer writer = new FileWriter(resultFile);
        final MarkupParser parser = new MarkupParser(new TextileDialect(), new HtmlDocumentBuilder(writer));
        parser.parse(new FileReader(sourceFile));
    }
}
