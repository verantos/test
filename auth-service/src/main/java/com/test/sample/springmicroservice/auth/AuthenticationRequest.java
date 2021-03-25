/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.sample.springmicroservice.auth;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author test
 */
@Data
public class AuthenticationRequest implements Serializable{
    private String username;
    private String password;
}
