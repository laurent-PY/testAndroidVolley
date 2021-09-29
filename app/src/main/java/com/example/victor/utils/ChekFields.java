package com.example.victor.utils;

import com.example.victor.models.Users;

public class ChekFields {


    public static boolean emptyFields(Users subs){

        String $nom = subs.getNom();
        String $prenom = subs.getPrenom();
        String $mail = subs.getMail();
        String $password = subs.getPassword();

        String [] fields = { $nom, $prenom, $mail, $password };


        if(fields.equals("")){

            return false;

        }else{
            return true;
        }
    }
}
