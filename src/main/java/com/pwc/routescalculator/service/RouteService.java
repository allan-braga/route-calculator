package com.pwc.routescalculator.service;

import java.util.List;

public interface RouteService {

  List<String> calculateRoute(String origin, String destiny);
}
