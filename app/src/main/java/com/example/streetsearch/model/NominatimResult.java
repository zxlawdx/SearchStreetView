package com.example.streetsearch.model;

import java.util.Map;

public class NominatimResult {
    private String place_id;
    private String display_name;
    private String lat;
    private String lon;
    private Map<String, String> address;

    public String getPlace_id() { return this.place_id;}
    public Map<String, String> getAdress() { return this.address;}
    public String getDisplay_name() { return this.display_name;}
    public String getLat() { return this.lat;}
    public String getLon() { return this.lon;}

    public String getShortName(){
        if(address == null) return display_name;
        String city = address.getOrDefault("city", address.getOrDefault("town", address.getOrDefault("village", address.get("hamlet"))));
        String state = address.get("state");
        if(city != null && state != null) return city + ", " + state;
        if(city != null) return city;

        return display_name;
    }

    @Override
    public String toString() {
        return display_name;
    }
}
