package org.example.auth.rest;

import org.example.auth.model.TokenCheckRequest;
import org.example.auth.model.TokenCheckResponse;
import org.example.auth.model.TokenRequest;
import org.example.auth.model.TokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenRestController {
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public TokenResponse getToken(@RequestBody TokenRequest tokenRequest){
        String code = tokenRequest.getCode();
        if("codeForAdmin".equals(code)) {
            return new TokenResponse("tokenForAdmin");
        } else if("codeForUser".equals(code)) {
            return new TokenResponse("tokenForUser");
        }
        return new TokenResponse("tokenForGuest");
    }

    @RequestMapping(value = "/token/check", method = RequestMethod.POST)
    @ResponseBody
    public TokenCheckResponse checkToken(@RequestBody TokenCheckRequest tokenRequest){
        String token = tokenRequest.getToken();
        if("tokenForAdmin".equals(token) || "tokenForUser".equals(token)) {
            return new TokenCheckResponse(true);
        }
        return new TokenCheckResponse(false);
    }
}
