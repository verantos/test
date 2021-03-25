/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.sample.springmicroservice.auth;

/**
 *
 * @author test
 */
public class SignupConflictException extends RuntimeException {

    public SignupConflictException(String message) {
        super(message);
    }

}
