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
public class UsernameWasTakenException extends RuntimeException {

    public UsernameWasTakenException(String username) {
        super(username + " was taken");
    }

}
