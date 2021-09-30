package com.example.victor.utils;

import android.util.Patterns;

import com.example.victor.models.Users;



import java.util.regex.Pattern;


public class ChekFields {

    public static String nom_volley_check(Users subs){
        String msg_nom = "";
        String nom = subs.getNom();
        String ortho = "^[a-zA-Z -]*$";
        if(nom.isEmpty()){
            msg_nom = "Un nom doit être saisi.";
        }else if(!Pattern.matches(ortho, nom)){
            msg_nom = "Format de Nom incorrect";
        }
        return msg_nom;
    }
    public static String prenom_volley_check(Users subs){
        String msg_prenom = "";
        String nom = subs.getPrenom();
        String ortho = "^[a-zA-Z-]*$";
        if(nom.isEmpty()){
            msg_prenom = "Un prénom doit être saisi.";
        }else if(!Pattern.matches(ortho, nom)){
            msg_prenom = "Format de Prénom incorrect";
        }
        return msg_prenom;
    }
    public static String mail_volley_check(Users subs){
        String msg_mail = "";
        String mail = subs.getMail();
        if(mail.isEmpty()){
            msg_mail = "Un Mail doit être saisi.";
        }else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            msg_mail = "Format de mail incorrect.";
        }
        return msg_mail;
    }

    public static String password_volley_check(Users subs){
        String msg_password ="";
        String password = subs.getPassword();
        String regPassword = "^(?=.*\\d)(?=.*[A-Za-z])[A-Za-z0-9]{1,8}$";
        if(password.isEmpty()){
            msg_password = "Le mot de passe doit être saisi.";
        }else if (!Pattern.matches(regPassword, password)){
            msg_password = "8 caractères, 1 lettre, et un chiffre. ";
        }
        return msg_password;
    }
}
