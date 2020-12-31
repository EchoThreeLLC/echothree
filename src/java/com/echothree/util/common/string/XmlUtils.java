// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.util.common.string;

import org.apache.xerces.xni.QName;

public class XmlUtils {
    
    private XmlUtils() {
        super();
    }
    
    private static class XmlUtilsHolder {
        static XmlUtils instance = new XmlUtils();
    }
    
    public static XmlUtils getInstance() {
        return XmlUtilsHolder.instance;
    }
    
    public QName[] toQNames(final String[] tags) {
        final QName[] qnames = new QName[tags.length];
        for(int i = 0; i < tags.length; ++i) {
            qnames[i] = new QName(null, tags[i], null, null);
        }

        return qnames;
    }

}
