/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.ext.discovery;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.ext.discovery.DiscoveryOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.discovery.DiscoveryOptions} original class using Vert.x codegen.
 */
public class DiscoveryOptionsConverter {

  public static void fromJson(JsonObject json, DiscoveryOptions obj) {
    if (json.getValue("announceAddress") instanceof String) {
      obj.setAnnounceAddress((String)json.getValue("announceAddress"));
    }
    if (json.getValue("backendConfiguration") instanceof JsonObject) {
      obj.setBackendConfiguration(((JsonObject)json.getValue("backendConfiguration")).copy());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("usageAddress") instanceof String) {
      obj.setUsageAddress((String)json.getValue("usageAddress"));
    }
  }

  public static void toJson(DiscoveryOptions obj, JsonObject json) {
    if (obj.getAnnounceAddress() != null) {
      json.put("announceAddress", obj.getAnnounceAddress());
    }
    if (obj.getBackendConfiguration() != null) {
      json.put("backendConfiguration", obj.getBackendConfiguration());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getUsageAddress() != null) {
      json.put("usageAddress", obj.getUsageAddress());
    }
  }
}