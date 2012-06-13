/*
 * Copyright 2012 XENEO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xeneo.plugin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xeneo.core.activity.ActivityManager;
import static org.easymock.EasyMock.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeneo.core.plugin.PluginConfiguration;

/**
 *
 * @author Stefan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-config.xml")
public class ActivityPluginRuntimeTest {

    private static Logger logger = LoggerFactory.getLogger(ActivityPluginRuntimeTest.class);
    @Autowired
    private ActivityPluginRuntimeImpl runtime;
    private ActivityManager mock;
    private String className = "at.stefanhuber.xeneo.plugin.SimpleTestPlugin";

    private int n = 20;
    
    @Before
    public void setUp() {
        mock = createMock(ActivityManager.class);
        runtime.setActivityManager(mock);


    }

    @Test
    public void testStartMultipleDifferentConfigurations() {

        // mock.isExistingActivity("");        
        // replay(mock);

        for (int i = 0; i < n; i++) {

            PluginConfiguration pc1 = new PluginConfiguration();
            pc1.setPluginClass(className);
            pc1.setPluginURI("http://myplugin.com/" + i);
            pc1.setOwnerURI("http://owner.com/" + i);

            runtime.startActivityPlugin(pc1);
        }


        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
    }
}
