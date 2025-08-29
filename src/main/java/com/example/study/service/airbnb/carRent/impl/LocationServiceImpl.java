package com.example.study.service.airbnb.carRent.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.res.airbnb.carRent.LocationRes;
import com.example.study.entity.airbnb.Location;
import com.example.study.repository.airbnb.LocationRepository;
import com.example.study.service.airbnb.carRent.LocationService;
import com.example.study.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public EventMessage<List<LocationRes>> findLocations() {
        List<Location> locations=  locationRepository.findAll();
        List<LocationRes> locationResList = new ArrayList<>();
        for (Location Location : locations) {
            LocationRes locationRes = new LocationRes();
            BeanUtils.copyProperties(Location, locationRes);
            locationResList.add(locationRes);
        }

        return CommonUtils.setDefaultEventMessage(locationResList);
    }

    public EventMessage<LocationRes>findLocationsById(Integer locationsId) {
        Optional<Location> locationOptional = locationRepository.findById(locationsId);
        if (locationOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }
        Location location = locationOptional.get();
        LocationRes locationRes = new LocationRes();
        BeanUtils.copyProperties(location, locationRes);
        return CommonUtils.setDefaultEventMessage(locationRes);
    }

}
