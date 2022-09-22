package com.pwc.routescalculator.service;

import com.pwc.routescalculator.exception.PathNotFound;
import com.pwc.routescalculator.repository.CountriesRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RouteServiceImpl implements RouteService {

  private final CalculateRouteStream calculateRoute;
  private final CountriesRepository repository;

  @Override
  public List<String> calculateRoute(String origin, String destiny) {
    if (!repository.existsCountryInJson(origin) || !repository.existsCountryInJson(destiny)) {
      throw new PathNotFound("");
    }

    return calculateRoute.getRoute(origin, destiny);
  }
}
