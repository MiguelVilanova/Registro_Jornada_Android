package com.example.registro_jornada_android;

import java.util.regex.Pattern;

public class Utils {

    //comprobar email
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null){
            return false;
        }

        return pat.matcher(email).matches();
    }
}
