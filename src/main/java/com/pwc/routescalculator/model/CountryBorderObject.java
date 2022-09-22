package com.pwc.routescalculator.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryBorderObject {

  private Set<String> path;
  private String country;

  public static Set<CountryBorderObject> buildCountryBorderObject(
      Map<String, Set<String>> countryBoarders, CountryBorderObject borderObject) {
    return countryBoarders.get(borderObject.getCountry()).stream()
        .map(
            ct -> {
              Set<String> path = new LinkedHashSet<>(borderObject.getPath());
              path.add(ct);
              return CountryBorderObject.builder().path(path).country(ct).build();
            })
        .collect(Collectors.toSet());
  }

  public static Set<CountryBorderObject> buildCountryBorderObject(
      Map<String, Set<String>> countryBoarders, String country) {
    return countryBoarders.get(country).stream()
        .map(
            ct -> {
              Set<String> path = new LinkedHashSet<>();
              path.add(country);
              path.add(ct);
              return CountryBorderObject.builder().path(path).country(ct).build();
            })
        .collect(Collectors.toSet());
  }
}
