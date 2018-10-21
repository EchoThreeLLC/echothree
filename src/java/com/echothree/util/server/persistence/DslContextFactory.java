// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.util.server.persistence;

import com.echothree.util.common.exception.PersistenceDatabaseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DslContextFactory {
    
    private static final Log log = LogFactory.getLog(DslContextFactory.class);
    
    private static final String DS = "java:/EchoThreeDS";
    private static final String NTDS = "java:/EchoThreeNTDS";
    
    private static final DslContextFactory instance = new DslContextFactory();
    
    private final DataSource ds;
    private final DataSource ntds;
    
    protected DslContextFactory() {
        Context jndiContext;
        
        try {
            jndiContext = new InitialContext();
        } catch (NamingException ne) {
            throw new PersistenceDatabaseException(ne);
        }
        
        try {
            ds = (DataSource)jndiContext.lookup(DS);
        } catch (NamingException ne) {
            throw new PersistenceDatabaseException(ne);
        }
        
        try {
            ntds = (DataSource)jndiContext.lookup(NTDS);
        } catch (NamingException ne) {
            throw new PersistenceDatabaseException(ne);
        }
    }
    
    public static DslContextFactory getInstance() {
        return instance;
    }
    
    public DSLContext getDslContext() {
        DSLContext dslContent = DSL.using(ds, SQLDialect.MYSQL);
        
        if(PersistenceDebugFlags.LogConnections)
            log.info("getDslContext() returning " + dslContent);
        return dslContent;
    }
    
    public DSLContext getNTDslContext() {
        DSLContext dslContent = DSL.using(ntds, SQLDialect.MYSQL);
        
        if(PersistenceDebugFlags.LogConnections)
            log.info("getNTDslContext() returning " + dslContent);
        return dslContent;
    }
    
}
