/*
 * Knot.x - Reactive microservice assembler - Templating Engine Verticle
 *
 * Copyright (C) 2016 Cognifide Limited
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
package com.cognifide.knotx.engine;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.json.JsonObject;

public class TemplateEngineConfiguration {

  private String address;

  private List<ServiceMetadata> services;

  private boolean templateDebug;

  private JsonObject clientOptions;

  public TemplateEngineConfiguration(JsonObject config) {
    address = config.getString("address");
    services = config.getJsonArray("services").stream()
        .map(item -> (JsonObject) item)
        .map(item -> {
          ServiceMetadata metadata = new ServiceMetadata();
          metadata.name = item.getString("name");
          metadata.address = item.getString("address");
          metadata.config = item.getJsonObject("config");
          return metadata;
        }).collect(Collectors.toList());
    templateDebug = config.getBoolean("template.debug", false);
    clientOptions = config.getJsonObject("client.options", new JsonObject());
  }

  public String getAddress() {
    return address;
  }

  public List<ServiceMetadata> getServices() {
    return services;
  }

  public boolean templateDebug() {
    return templateDebug;
  }

  public JsonObject getClientOptions() {
    return clientOptions;
  }

  public static class ServiceMetadata {

    private String name;
    private String address;
    private JsonObject config;

    @Override
    public boolean equals(Object obj) {
      if (obj != null && obj instanceof ServiceMetadata) {
        final ServiceMetadata other = (ServiceMetadata) obj;
        return new EqualsBuilder()
            .append(name, other.getName())
            .append(address, other.getAddress())
            .append(config, other.getConfig()).isEquals();
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder()
          .append(name)
          .append(address)
          .append(config)
          .toHashCode();
    }

    public String getName() {
      return name;
    }

    public String getAddress() {
      return address;
    }

    public JsonObject getConfig() {
      return config;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public void setConfig(JsonObject config) {
      this.config = config;
    }
  }
}
