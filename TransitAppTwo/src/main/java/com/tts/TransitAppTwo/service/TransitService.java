package com.tts.TransitAppTwo.service;
import com.tts.TransitAppTwo.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransitService {
    @Value("${transit_url}")
    public String transitUrl;

    @Value("${geocoding_url}")
   public String geocodingUrl;

    @Value("${distance_url}")
    public String distanceUrl;

    @Value("${google_api_key}")
    public String googleApiKey;

    private List<Bus> getBuses(){
        RestTemplate restTemplate = new RestTemplate();
        Bus[] buses = restTemplate.getForObject(transitUrl,Bus[].class );
        return Arrays.asList(buses);
    }

    private Location getCoordinates(String description){
        description = description.replace(" ","+");
        String url = geocodingUrl + description + "+GA&Key=" + googleApiKey;
        RestTemplate restTemplate = new RestTemplate();
        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);
        return response.results.get(0).geometry.location;
    }
    private double getDistance(Location origin, Location destination){
        String url = distanceUrl + "origins=" + origin.lat+ "," + origin.lng
        + "&destinations=" + destination.lat + "," + destination.lng
                + "&Key" + googleApiKey;
        RestTemplate restTemplate = new RestTemplate();
        DistanceResponse response = restTemplate.getForObject(url, DistanceResponse.class);
        return response.rows.get(0).elements.get(0).distance.value * 0.000621371;
    }

    public List<Bus> getNearbyBuses(BusRequest request){
        List<Bus> allBuses = this.getBuses();
        Location personLocation = this.getCoordinates(request.address+" " +request.city);
        List<Bus> nearbyBuses = new ArrayList<>();
        for(Bus bus : allBuses){
            Location busLocation = new Location();
            busLocation.lat = bus.LATITUDE;
            busLocation.lng = bus.LONGITUDE;
            double latDistance = Double.parseDouble(busLocation.lat) - Double.parseDouble(personLocation.lat);
            double lngDistance = Double.parseDouble(busLocation.lng) - Double.parseDouble(personLocation.lng);
            if(Math.abs(latDistance) <= 0.02 && Math.abs(lngDistance) <=0.02){
               double distance= getDistance(busLocation, personLocation);
               if(distance<= 1){
                   bus.distance = (double) Math.round(distance*100) / 100;
                   nearbyBuses.add(bus);

               }
            }
        }
        Collections.sort(nearbyBuses, new BusComparator());
        return nearbyBuses;
    }
}
