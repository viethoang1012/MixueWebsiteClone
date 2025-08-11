package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Location;
import com.gdu_springboot.spring_boot_demo.dao.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }
    
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
    
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}