package com.example.study.controller.airbnb.carRent;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.res.airbnb.carRent.LocationRes;
import com.example.study.service.airbnb.carRent.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carRent")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/locations")
    public EventMessage<List<LocationRes>> findLocations() {
        return locationService.findLocations();
    }

    @GetMapping("/locations/{locationsId}")
    public EventMessage<LocationRes>findLocationsById(@PathVariable Integer locationsId) {
        return locationService.findLocationsById(locationsId);
    }
}
