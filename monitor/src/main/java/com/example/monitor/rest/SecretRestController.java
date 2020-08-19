package com.example.monitor.rest;

import com.example.monitor.model.SecretData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret")
public class SecretRestController {

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public SecretData secret() {
        return new SecretData("CLASSIFIED");
    }
}
