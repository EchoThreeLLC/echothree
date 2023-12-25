// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.test.ping;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.AccountingService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AccountingServicePingTest
        extends TestCase {
    private AccountingService accountingService;
    
    /** Creates a new instance of AccountingServiceTest */
    public AccountingServicePingTest(String name) {
        super(name);
    }
    
    public static void main(String[] args) {
        TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTest(new AccountingServicePingTest("testPingAccountingService"));
        
        return suite;
    }
    
    @Override
    protected void setUp() {
        try {
            accountingService = AccountingUtil.getHome();
            assertNotNull(accountingService);
        } catch (Exception e) {
            fail("setup Exception: " + printException(e));
        }
    }
    
    @Override
    protected void tearDown() {
        try {
            accountingService = null;
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
    }
    
    public void testPingAccountingService() {
        setUp();
        try {
            accountingService.ping();
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
        tearDown();
    }
        
    private String printException(Exception e) {
        return e.getClass().getName() + ", " + e.getMessage();
    }
    
}
