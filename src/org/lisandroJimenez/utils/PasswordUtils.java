/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Usuario
 */
public class PasswordUtils {
    private static PasswordUtils instance;
    
    private PasswordUtils(){
    
    }

    public static PasswordUtils getInstance() {
        if(instance == null){
            instance = new PasswordUtils();
        }
        return instance;
    }
    
    public String encryptedPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    
    public Boolean checkPassword(String password, String encryptedPass){
        return BCrypt.checkpw(password, encryptedPass);
    }
    
}
