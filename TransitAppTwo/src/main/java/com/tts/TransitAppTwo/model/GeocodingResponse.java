package com.tts.TransitAppTwo.model;

import lombok.Data;

import java.util.List;
@Data
public class GeocodingResponse {
    public List<Geocoding> results;

}
