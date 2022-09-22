package com.pwc.routescalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {

  private String cca2;
  private String ccn3;
  private String cca3;
  private String cioc;
  private boolean independent;
  private String region;
  private String subregion;
  private boolean landlocked;
  private double area;
  private String flag;
  private Set<String> borders;

  @JsonProperty("native")
  private CountryNameNative nativeName;

}
