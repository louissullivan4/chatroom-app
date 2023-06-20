package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//Source: Robert Sedgewick and Kevin Wayne, 2022, https://introcs.cs.princeton.edu/java/44st/Location.java.html
@Entity
public class Location {
    private @Id
    @GeneratedValue Long id;
    private @Column String name;
    private @Column double longitude;
    private @Column double latitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    public Location() {}

    public double distanceTo(Location that) {
//        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double STATUTE_MILES_PER_KILOMETER = 1.852;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        // return as kilometers
        return STATUTE_MILES_PER_KILOMETER * nauticalMiles;
    }

    // return string representation of this point
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }
}

