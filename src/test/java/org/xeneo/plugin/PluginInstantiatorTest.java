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


import org.junit.*;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeneo.core.plugin.PluginConfiguration;
import org.xeneo.core.plugin.PluginException;
import org.xeneo.core.plugin.activity.ActivityPlugin;

/**
 *
 * @author Stefan
 */
public class PluginInstantiatorTest {
    
    public PluginInstantiatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    PluginInstantiator pi = new PluginInstantiator();
    private static Logger logger = LoggerFactory.getLogger(PluginInstantiatorTest.class);
    
    
    @Test
    public void testInstantiation() {
        String className = "at.stefanhuber.xeneo.plugin.SimpleTestPlugin";
        
        PluginConfiguration pc = new PluginConfiguration();
        pc.setPluginClass(className);
        
        try {
            ActivityPlugin ap = pi.createPluginInstance(pc, ActivityPlugin.class);
            
            assertEquals(ap.getClass().getCanonicalName(),className);           
            
        } catch (PluginException ex)  {
            logger.error("The Plugin Instantition failed reason: " + ex.getMessage());            
        }
    
    }
}
