/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.sample.springmicroservice.auth;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author test
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController {

    @GetMapping("")
    public Map<String, Object> user(Principal user) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
            .getAuthorities()));
        return map;
    }
}
