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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xeneo.core.plugin.PluginRepository;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.xeneo.core.plugin.PluginConfiguration;
import org.xeneo.core.plugin.PluginProperty;
import org.xeneo.core.plugin.PluginPropertyType;
import org.xeneo.core.plugin.PluginType;

/**
 *
 * @author Stefan Huber
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-config.xml")
public class PluginConfiguratorTest {

    @Autowired
    private PluginConfiguratorImpl configurator;
    private PluginRepository mock;

    @Before
    public void setUp() {
        mock = createMock(PluginRepository.class);
        configurator.setPluginRepository(mock);
    }
    
    private String className = "at.stefanhuber.xeneo.plugin.SimpleTestPlugin";
    
    @Test
    public void testInit() {
        PluginConfiguration pc = new PluginConfiguration();
        pc.setTitle("Dropbox Plugin");
        pc.setPluginURI("http://plugin.xeneo.org/dropbox-activity-plugin");
        
        PluginProperty[] pps = new PluginProperty[3];
        for (int i = 0; i < 3;i++) {
            pps[i] = new PluginProperty();
            pps[i].setName("param" + i);
            pps[i].setType(PluginPropertyType.URI);
        }        
        pc.setProperties(pps);
        
        mock.addPlugin(pc);
        
        replay(mock);
        
        configurator.init();
        
        verify(mock);       
    }
    
    @Test
    public void testPluginConfiguration() {
        PluginConfiguration pc = new PluginConfiguration();
        pc.setPluginClass(className);
        pc.setPluginURI("http://someplugin.org/1");
        pc.setOwnerURI("http://someone.com/1");
        pc.setTitle("test title");
        pc.setPluginType(PluginType.ACTIVITY_PLUGIN);
        
        mock.configurePlugin(pc);
        
        replay(mock);
        
        configurator.configurePlugin(pc);
        
        verify(mock);
    }
}
