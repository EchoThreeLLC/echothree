// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EscapedWriter
        extends FilterWriter {
    /** Creates new EscapedXMLWriter */
    /** Creates a new instance of EscapedWriter */
    public EscapedWriter(Writer out) {
        super(out);
    }

    @Override
    public void write (char[] cbuf, int off, int len) throws IOException {
        for(var i = 0; i < len; i++) {
            write (cbuf[off + i]);
        }
    }


    @Override
    public void write (String str, int off, int len) throws IOException {
        for(var i = 0; i < len; i++) {
            write (str.charAt (off + i));
        }
    }
}
