package com.example.monitor.rest;

import com.example.monitor.model.MonitorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor")
public class MonitorRestController {
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MonitorData healthCheck() {
        return new MonitorData("healthy");

    }
}
