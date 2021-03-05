package com.tts.TransitAppTwo.controller;
import com.tts.TransitAppTwo.model.Bus;
import com.tts.TransitAppTwo.model.BusRequest;
import com.tts.TransitAppTwo.service.TransitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TransitController {

    @Autowired
    private TransitService apiService;

    @GetMapping(value = "/buses")
    public String getBusesPage(Model model){
        model.addAttribute("request", new BusRequest());
        return "index";
    }
    @PostMapping(value = "/buses")
    public String getNearbyBuses(BusRequest request, Model model){
        List<Bus> buses = apiService.getNearbyBuses(request);
        model.addAttribute("buses" , buses);
        model.addAttribute("request", request);
        return "index";
    }
}
