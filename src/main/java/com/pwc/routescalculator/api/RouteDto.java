package com.pwc.routescalculator.api;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDto {

  private List<String> route;

}
