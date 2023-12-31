package com.unitedcoder.hackerrank;

import java.util.Scanner;

public class DataTypes {
    public static void main(String[] args) {
        //https://www.hackerrank.com/challenges/java-datatypes/problem?isFullScreen=true
        Scanner scan= new Scanner(System.in);
        int t=scan.nextInt();
        for(int i=0; i<t; i++){
            try{
                long a=scan.nextLong();
                System.out.println(a+" can be fitted in:");
                if(a>=Byte.MIN_VALUE && a<= Byte.MAX_VALUE){
                    System.out.println("* byte");
                }
                if(a>=Short.MIN_VALUE && a<= Short.MAX_VALUE){
                    System.out.println("* short");
                }
                if(a>=Integer.MIN_VALUE && a<= Integer.MAX_VALUE){
                    System.out.println("* int");
                }
                if(a>=Long.MIN_VALUE && a<= Long.MAX_VALUE){
                    System.out.println("* long");
                }
            }
            catch(Exception e) {
                System.out.println(scan.next()+" can't be fitted anywhere.");
            }
        }
    }
}
