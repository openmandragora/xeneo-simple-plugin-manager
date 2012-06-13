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

    private PluginRepository repository;
    private ActivityPluginRuntime runtime;

    public void setPluginRepository(PluginRepository repository) {
        this.repository = repository;
    }

    public void setActivityPluginRuntime(ActivityPluginRuntime runtime) {
        this.runtime = runtime;
    }

    public List<PluginConfiguration> listAvailablePlugins(String userURI, PluginType[] pluginTypes) {
        return repository.listAvailablePlugins(userURI, pluginTypes);
    }

    public void configurePlugin(PluginConfiguration pc) {

        // add the configuration to the Repository
        repository.configurePlugin(pc);

        if (pc.getPluginType().equals(PluginType.ACTIVITY_PLUGIN)) {
            // start the plugin with respective configuration
            runtime.startActivityPlugin(pc);
        }

    }
}
