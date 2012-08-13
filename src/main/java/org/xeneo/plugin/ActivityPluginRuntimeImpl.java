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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.xeneo.core.activity.ActivityRepository;
import org.xeneo.core.plugin.PluginConfiguration;
import org.xeneo.core.plugin.PluginException;
import org.xeneo.core.plugin.activity.ActivityPlugin;
import org.xeneo.core.plugin.activity.ActivityPluginRuntime;

/**
 *
 * @author Stefan Huber
 */
public class ActivityPluginRuntimeImpl implements ActivityPluginRuntime {

    private static Logger logger = LoggerFactory.getLogger(ActivityPluginRuntimeImpl.class);
    private Map<String, ScheduledFuture> tasks = new HashMap<String, ScheduledFuture>();
    private PluginInstantiator pi;
    private ActivityRepository am;
    private TaskScheduler scheduler;

    public void setPluginInstantiator(PluginInstantiator pi) {
        this.pi = pi;
    }
    
    public void setActivityRepository(ActivityRepository am) {
        this.am = am;
    }
    
    public void setTaskScheduler(TaskScheduler ts) {
        this.scheduler = ts;
    }

    public void startActivityPlugin(PluginConfiguration pc) {
        String instanceid = getInstanceId(pc.getPluginURI(), pc.getOwnerURI());

        // if exists cancel...
        cancelTask(instanceid);

        try {
            ActivityPlugin ap = pi.createPluginInstance(pc, ActivityPlugin.class);
            ap.setPluginConfiguration(pc);
            ap.setActivityRepository(am);

            ap.init();
            tasks.put(instanceid, scheduler.scheduleWithFixedDelay(ap, 30000L));

        } catch (PluginException ex) {
            logger.error("Plugin couldn't be intantiated: " + ex.getMessage());
        }
    }

    /*
     * Cancel and Remove Task from Scheduler iff the @param instanceid exists as
     * a task already.
     */
    protected void cancelTask(String instanceid) {
        if (tasks.containsKey(instanceid)) {
            logger.info("Try to cancel task with instance id: " + instanceid);
            ScheduledFuture sf = tasks.get(instanceid);
            sf.cancel(false);
            tasks.remove(instanceid);
        }
    }

    private String getInstanceId(String pluginURI, String ownerURI) {
        return DigestUtils.md5Hex(pluginURI + ownerURI);
    }
}
