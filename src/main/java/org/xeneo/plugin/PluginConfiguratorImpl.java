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

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeneo.core.plugin.PluginConfiguration;
import org.xeneo.core.plugin.PluginConfigurator;
import org.xeneo.core.plugin.PluginRepository;
import org.xeneo.core.plugin.PluginType;
import org.xeneo.core.plugin.activity.ActivityPluginRuntime;

/**
 *
 * @author Stefan Huber
 */
public class PluginConfiguratorImpl implements PluginConfigurator {

    private static Logger logger = LoggerFactory.getLogger(PluginConfiguratorImpl.class);
    private PluginRepository repository;
    private ActivityPluginRuntime runtime;
    private List<PluginConfiguration> initPlugins;

    public void init() {
        logger.info("Init Plugins...");
        for (PluginConfiguration pc : initPlugins) {            
            repository.addPlugin(pc);
            logger.info("Plugin with title: " + pc.getTitle() + " added.");
        }
        
        initPlugins = null;
    }

    public void setPluginRepository(PluginRepository repository) {
        this.repository = repository;
    }

    public void setActivityPluginRuntime(ActivityPluginRuntime runtime) {
        this.runtime = runtime;
    }

    public void setPlugins(List<PluginConfiguration> pcs) {
        logger.info("set Plugins for initialization.");
        initPlugins = pcs;
    }

    public List<PluginConfiguration> listAvailablePlugins(String userURI, PluginType[] pluginTypes) {
        return repository.listAvailablePlugins(userURI, pluginTypes);
    }

    public void configurePlugin(PluginConfiguration pc) {

        // add the configuration to the Repository
        repository.configurePlugin(pc);

        if (pc.getPluginType().equals(PluginType.ACTIVITY_PLUGIN)) {
            // start the plugin with respective configuration
            logger.info("Start Activity Plugin: " + pc.getTitle());
            runtime.startActivityPlugin(pc);
        }

    }
}
