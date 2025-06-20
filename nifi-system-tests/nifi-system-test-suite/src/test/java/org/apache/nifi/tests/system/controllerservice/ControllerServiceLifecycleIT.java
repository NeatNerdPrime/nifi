/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.tests.system.controllerservice;

import org.apache.nifi.tests.system.NiFiSystemIT;
import org.apache.nifi.toolkit.client.NiFiClientException;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerServiceLifecycleIT extends NiFiSystemIT {
    @Test
    public void testControllerServiceFailingToEnableAllowsOthersToEnable() throws NiFiClientException, IOException {
        for (int i = 0; i < 12; i++) {
            ControllerServiceEntity failureService = getClientUtil().createControllerService("LifecycleFailureService");
            getClientUtil().updateControllerServiceProperties(failureService, Collections.singletonMap("Enable Failure Count", "1000"));
        }

        final List<String> countServiceIds = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            ControllerServiceEntity countService = getClientUtil().createControllerService("StandardCountService");
            countServiceIds.add(countService.getId());
        }

        getClientUtil().enableControllerServices("root", false);
        getClientUtil().waitForControllerServicesEnabled("root", countServiceIds);
    }

    @Test
    public void testControllerServiceEnableFailureCausesRetry() throws NiFiClientException, IOException {
        ControllerServiceEntity service = getClientUtil().createControllerService("LifecycleFailureService");
        getClientUtil().updateControllerServiceProperties(service, Collections.singletonMap("Enable Failure Count", "1"));

        getClientUtil().enableControllerServices("root", false);
        getClientUtil().waitForControllerServicesEnabled("root");
    }

}
