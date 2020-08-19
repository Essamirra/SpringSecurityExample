package org.example.auth.rest;

import org.example.auth.model.AuthException;
import org.example.auth.model.LoginRequest;
import org.example.auth.model.LoginResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginRestController {

    private static final Map<String,String> USERS = new HashMap<>();
    private static final Map<String,String> CODES = new HashMap<>();
    static {
        USERS.put("admin", "adminPass");
        USERS.put("user", "userPass");
        CODES.put("admin", "codeForAdmin");
        CODES.put("user", "codeForUser");
        CODES.put("guest", "codeForGuest");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request) {
        String login = request.getLogin();
        String actualPassword = request.getPassword();
        if(StringUtils.isEmpty(login) || StringUtils.isEmpty(actualPassword) ) {
            throw new AuthException("Error happened");
        }
        String expectedPassword = USERS.get(login);
        if(expectedPassword == null) {
            return new LoginResponse(CODES.get("guest"));
        }
        if(actualPassword.equals(expectedPassword)) {
            return new LoginResponse(CODES.get(login));
        }
        throw new AuthException("Error happened");
    }
}
