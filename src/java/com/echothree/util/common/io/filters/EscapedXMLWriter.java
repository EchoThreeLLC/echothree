// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.common.io.filters;

import java.io.IOException;
import java.io.Writer;

public class EscapedXMLWriter
        extends EscapedWriter {
    /** Creates new EscapedXMLWriter */
    /** Creates a new instance of EscapedXMLWriter */
    public EscapedXMLWriter(Writer out) {
        super(out);
    }

    @Override
    public void write (int c) throws IOException {
        switch(c) {
            case '&':
                out.write("&amp;");
                break;
            case '<':
                out.write("&lt;");
                break;
            case '>':
                out.write("&gt;");
                break;
            case '"':
                out.write("&quot;");
                break;
            case '\'':
                out.write("&apos;");
                break;
            default:
                out.write(c);
                break;
        }
    }
}
