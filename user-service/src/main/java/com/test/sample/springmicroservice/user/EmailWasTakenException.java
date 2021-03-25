/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.sample.springmicroservice.user;

/**
 *
 * @author test
 */
public class EmailWasTakenException extends RuntimeException {

    public EmailWasTakenException(String email) {
        super(email + " was taken");
    }

}
