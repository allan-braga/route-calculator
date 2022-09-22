package com.pwc.routescalculator.model;

import java.util.Map;
import lombok.Data;

@Data
public class CountryNameNative {

  private Map<String, CountryName> nativeNames;
}
