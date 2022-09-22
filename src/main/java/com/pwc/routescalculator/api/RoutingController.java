package com.pwc.routescalculator.api;

import com.pwc.routescalculator.service.RouteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
public class RoutingController {

  private final RouteService routeService;

  @GetMapping("/{origin}/{destination}")
  public Mono<RouteDto> getRoute(@PathVariable("origin") String origin,
      @PathVariable("destination") String destination) {
    return Mono.just(RouteDto.builder().route(routeService.calculateRoute(origin, destination)).build());
  }
}
