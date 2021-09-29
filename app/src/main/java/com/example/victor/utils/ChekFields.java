package com.example.victor.utils;

import com.example.victor.models.Users;


public class ChekFields {


    public static boolean emptyFields(Users subs){

        String $nom = subs.getNom();
        String $prenom = subs.getPrenom();
        String $mail = subs.getMail();
        String $password = subs.getPassword();


        if($nom.equals("") || $prenom.equals("") || $mail.equals("") || $password.equals("")){
            return false;
        }else{
            return true;
        }
    }
}
