package com.tts.TransitAppTwo.model;

import lombok.Data;

import java.util.Comparator;

@Data
public class BusComparator implements Comparator<Bus> {
    @Override
    public  int compare(Bus o1, Bus o2){
        if(o1.distance < o2.distance) return -1;
        if(o1.distance > o2.distance) return 1;
        return 0;
    }

}
