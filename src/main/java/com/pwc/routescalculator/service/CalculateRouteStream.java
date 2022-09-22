package com.pwc.routescalculator.service;

import static com.pwc.routescalculator.model.CountryBorderObject.buildCountryBorderObject;

import com.pwc.routescalculator.exception.PathNotFound;
import com.pwc.routescalculator.model.CountryBorderObject;
import com.pwc.routescalculator.repository.CountriesRepository;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateRouteStream {

  @Value("${app.cache.size:0}")
  private Integer cacheSize;

  private Map<String, Set<String>> countryBoarders;

  private final CountriesRepository repository;

  @PostConstruct
  public void fillCountryBorders() {
    countryBoarders = repository.getCountryBorders();
  }

  public List<String> getRoute(final String origin, final String destiny) {
    Objects.requireNonNull(origin, "Origin cannot be null");
    Objects.requireNonNull(destiny, "Destiny cannot be null");

    final List<String> crossingBorders = new LinkedList<>();
    findRoute(origin, destiny, crossingBorders);
    return crossingBorders;
  }

  private void findRoute(String origin, String destiny, List<String> crossingBorders) {
    Set<CountryBorderObject> nextList = buildCountryBorderObject(countryBoarders, origin);
    Iterator<CountryBorderObject> currentList = nextList.iterator();
    Iterator<CountryBorderObject> tmpList = nextList.iterator();

    while (currentList.hasNext()) {
      CountryBorderObject item = currentList.next();
      if (item.getCountry().equals(destiny) || exists(destiny, item.getCountry())) {
        crossingBorders.addAll(item.getPath());
        crossingBorders.add(destiny);
        break;
      }

      if (!currentList.hasNext()) {
        if (tmpList.hasNext()) {
          currentList = buildCountryBorderObject(countryBoarders, tmpList.next()).iterator();
        } else {
          final Set<CountryBorderObject> next =
              nextList.stream()
                  .map(cb -> buildCountryBorderObject(countryBoarders, cb))
                  .flatMap(Collection::stream)
                  .collect(Collectors.toSet());

          nextList = next;
          tmpList = next.stream().iterator();
          currentList = tmpList;
        }
      }

      if (nextList.size() > cacheSize) {
        throw new PathNotFound("Path not found");
      }
    }
  }

  private boolean exists(String destiny, String border) {
    final Set<String> borders = countryBoarders.get(border);
    return borders.contains(destiny);
  }
}
