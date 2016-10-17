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
package com.cognifide.knotx.engine.placeholders;

import com.cognifide.knotx.dataobjects.RenderRequest;
import com.cognifide.knotx.engine.service.ServiceEntry;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UriTransformer {

  private static List<PlaceholderSubstitutor> placeholderSubstitutors =
      Arrays.asList(new PlaceholderSubstitutor[]{new RequestPlaceholderSubstitutor(),
          new UriPlaceholderSubstitutor()});

  private UriTransformer() {
    // util
  }

//  public static String getServiceUri(RenderRequest request, ServiceEntry serviceEntry) {
//    String serviceUri = serviceEntry.getServiceUri();
//    List<String> placeholders = getPlaceholders(serviceUri);
//
//    for (String placeholder : placeholders) {
//      serviceUri = serviceUri.replace("{" + placeholder + "}",
//          getPlaceholderValue(request, placeholder));
//    }
//
//    return serviceUri;
//  }

  protected static List<String> getPlaceholders(String serviceUri) {
    return Arrays.asList(serviceUri.split("\\{")).stream()
        .filter(str -> str.contains("}"))
        .map(str -> StringUtils.substringBefore(str, "}"))
        .collect(Collectors.toList());
  }

  private static String getPlaceholderValue(RenderRequest request, String placeholder) {
    return placeholderSubstitutors.stream()
        .map(substitutor -> substitutor.getValue(request, placeholder))
        .filter(str -> str != null)
        .findFirst()
        .orElse("");
  }
}
