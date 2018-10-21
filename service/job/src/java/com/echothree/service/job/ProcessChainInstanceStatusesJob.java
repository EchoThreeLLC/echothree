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

package com.echothree.service.job;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.model.control.job.common.Jobs;
import com.echothree.util.common.service.job.BaseScheduledJob;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;

@Singleton
public class ProcessChainInstanceStatusesJob
        extends BaseScheduledJob {
    
    /** Creates a new instance of ProcessChainInstanceStatusesJob */
    public ProcessChainInstanceStatusesJob() {
        super(Jobs.PROCESS_CHAIN_INSTANCE_STATUSES.name());
    }

    @Override
    @Schedule(second = "25", minute = "*", hour = "*", persistent = false)
    public void executeJob()
            throws NamingException {
        super.executeJob();
    }

    @Override
    public void execute()
            throws NamingException {
        ChainUtil.getHome().processChainInstanceStatuses(userVisitPK);
    }
    
}
