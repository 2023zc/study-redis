package com.example.MyUtils.CodeUtils;

import java.util.Random;

public class codeUtils {
    public static String getCode(int n){
        Random random=new Random();
        StringBuilder code= new StringBuilder();
        for(int i=0;i<n;i++){
            code.append(random.nextInt(0,10));
        }
        return code.toString();
    }
}
