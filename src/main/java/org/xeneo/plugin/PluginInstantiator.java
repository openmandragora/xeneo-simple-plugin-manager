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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeneo.core.plugin.Plugin;
import org.xeneo.core.plugin.PluginConfiguration;
import org.xeneo.core.plugin.PluginException;

/**
 *
 * @author Stefan Huber
 */
public class PluginInstantiator {

    private static Logger logger = LoggerFactory.getLogger(PluginInstantiator.class);

    public <P extends Plugin> P createPluginInstance(PluginConfiguration pc, Class<P> t) throws PluginException {

        try {
            Class plugin = Class.forName(pc.getPluginClass());
            Object o = plugin.newInstance();
            
            if (t.isInstance(o)) {
                return (P) o;
            }

        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new PluginException("The Plugin Class ("+ pc.getPluginClass() + ") could not be found.");
        } catch (InstantiationException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        }

        throw new PluginException("The Plugin with the URI: " + pc.getPluginURI() + " couldn't be instantiated.");
    }
}
