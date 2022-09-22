package com.pwc.routescalculator.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.routescalculator.model.Country;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CountriesRepository {

  private final ObjectMapper mapper;
  private Set<Country> countries = new HashSet<>();
  Map<String, Set<String>> borders = new HashMap<>();

  @PostConstruct
  public void loadCountries() {
    log.info("Loading countries");
    TypeReference<Set<Country>> typeReference = new TypeReference<>() {};
    InputStream inputStream = TypeReference.class.getResourceAsStream("/countries.json");
    try {
      countries = mapper.readValue(inputStream, typeReference);
      borders = countries.stream().collect(Collectors.toMap(Country::getCca3, Country::getBorders));
      log.info("Countries loaded");
    } catch (IOException e) {
      log.error("Unable to load countries: ", e);
    }
  }

  public boolean existsCountryInJson(final String country) {
    return borders.containsKey(country);
  }

  public Map<String, Set<String>> getCountryBorders() {
    return borders;
  }

  public Set<Country> getCountries() {
    return countries;
  }
}
