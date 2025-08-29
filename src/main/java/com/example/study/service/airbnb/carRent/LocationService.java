package com.example.study.service.airbnb.carRent;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.res.airbnb.carRent.LocationRes;

import java.util.List;

public interface LocationService {

    EventMessage<List<LocationRes>> findLocations();

    EventMessage<LocationRes>findLocationsById(Integer locationsId);
}
