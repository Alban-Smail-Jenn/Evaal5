/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccessBackofficeDAO;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AccessBackoffice;

/**
 *
 * @author Formation
 */
public class WelcomeController {

    private AccessBackofficeDAO accessBackofficeDAO;
    private String errorMessageContent;
    private String errorMessageTitle;
    private int numberTry;
    private int MAX_TRY = 3;
    private boolean hasChanged;

    public WelcomeController(AccessBackofficeDAO accessBackofficeDAO) {
        this.accessBackofficeDAO = accessBackofficeDAO;
        this.errorMessageContent = "";
        this.errorMessageTitle = "Login | Password";
        this.numberTry = 0;
        this.hasChanged = false;

    }

    public boolean checkLoginPassword(String login, String password) {
        boolean checkLoginPassword = true;
        // login and password is empty
        if (login.isEmpty() && password.isEmpty()) {
            checkLoginPassword = false;
            this.errorMessageContent = "Merci de saisir un identifiant et un mot de passe";
        }// TextFieldLogin empty 
        else if (login.isEmpty()) {
            checkLoginPassword = false;
            this.errorMessageContent = "Merci de saisir un identifiant";
        } // PasswordField empty 
        else if (password.isEmpty()) {
            checkLoginPassword = false;
            this.errorMessageContent = "Merci de saisir un mot de passe";
        
        } else {
            // Find login in AccessBackoffice
            AccessBackoffice loginAccessBackoffice = this.accessBackofficeDAO.find(login);
            
            // Invalid Login
            if (!this.accessBackofficeDAO.isValid(loginAccessBackoffice)) {
                checkLoginPassword = false;
                this.errorMessageContent = "Erreur identifiant ou mot de passe incorrect";
            }
            // Blocked session
            else if (loginAccessBackoffice.isIsBlocked()) {
                checkLoginPassword = false;
                this.errorMessageContent = "Echec de connexion, veuillez contacter votre administrateur au 08.36.65.65.65 !!!";
                this.numberTry = MAX_TRY;

            }
            // Invalid login || password  
            else if (!loginAccessBackoffice.getPassword().equals(this.getMD5Hash(password))) {
                checkLoginPassword = false;
                this.errorMessageContent = "Erreur identifiant ou Mot de passe incorrect";
            } else {
                this.hasChanged = loginAccessBackoffice.isHasChanged();
            }
            
        }   
        // Numbers users's tries 
        if (!checkLoginPassword) {
            this.numberTry++;
        }
       
        return checkLoginPassword;
    }

    public boolean checkTryNumber() {
        boolean chekTryNumber = true;
        if (this.numberTry == MAX_TRY) {
            chekTryNumber = false;
            this.errorMessageContent = "Erreur, identifiant ou mot de passe "
                    + "incorrect, vous avez atteint le nombre maximal de "
                    + "tentatives, merci de contacter votre administrateur au "
                    + "08.36.65.65.65";
        }
        return chekTryNumber;
    }
    
    // Called method for views
    public String getErrorMessageContent() {
        return errorMessageContent;
    }

    public String getErrorMessageTitle() {
        return errorMessageTitle;
    }
    
    public boolean getHasChanged(){       
         return this.hasChanged;
    }
 
        
    // Hashing
    public static String getMD5Hash(String passwordClear) {

        String result = passwordClear;
        if (passwordClear != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
                md.update(passwordClear.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) { // 40 for SHA-1
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        return result;
    }
}
    
