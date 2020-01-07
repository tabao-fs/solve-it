package com.utilities;

import java.util.Arrays;
import java.util.Collections;

import com.flashcards.solveit.MainActivity;

import android.util.Log;

public class Arithmetics {
    String[] operatorList = {"+","-","x","/"};
    String operator="";
    int numChoices=3;
    int numQuestions=2;
    Integer[] choices = new Integer[numChoices];
    int ans = 0, random = 3, numLimit=9, testNumDiv;
    int userScore=0;
    double numDiv;
    
    Integer[] question;
    public int r1=0, r2=1;
    
    boolean testDiv=true, testChoices=true;
    public boolean userAnswer=false;
    
    public Arithmetics(){
        
    }
    
    public Arithmetics(String operator){
        this.operator=operator;
        this.userScore=0;
    }
    
    public void fillAndShuffle(){
        question = new Integer[numLimit];
        for (int i = 0; i < question.length; i++) {
            question[i] = i+1;
        }
        Collections.shuffle(Arrays.asList(question));
    }
    
    public void genRanQuestions(){
        testDiv=true;
        while(testDiv){
            randomizeQuestions();
            if(question[r1]<=question[r2]){
                randomizeQuestions();
            } else {
                divTest();
            }
        }
    }
    
    public void divTest(){
        while(testDiv){
            try {
                testNumDiv = question[r1]%question[r2];
            } catch (ArithmeticException e) {
                genRanQuestions();
            }
            if(testNumDiv==0){
                testDiv=false;
                Log.d(MainActivity.tag, "questions: "+question[r1]+" "+question[r2]);
            } else {
                genRanQuestions();
            }
        }
    }
    
    public void randomizeQuestions(){
        r1 = (int)(Math.random()*(numLimit-1));
        r2 = (int)(Math.random()*(numLimit-1));
        Collections.shuffle(Arrays.asList(question));
    }
    
    public void genRanChoices(){
    	if(this.operatorList[0].equals(this.operator)){	
    		ans = choices[2] = question[r1]+question[r2];
    	}
    	if(this.operatorList[1].equals(this.operator)){
    		ans = choices[2] = question[r1]-question[r2];
    	}
    	if(this.operatorList[2].equals(this.operator)){
    		ans = choices[2] = question[r1]*question[r2];
    	}
    	if(this.operatorList[3].equals(this.operator)){
    		ans = choices[2] = question[r1]/question[r2];
        }
    	ranChoices();
        Log.d(MainActivity.tag, "Choices: "+choices[0]+" "+choices[1]+" "+choices[2]);
    }
    
    public void ranChoices(){
        randomizeChoices();
        testChoices=true;
    	while(testChoices){
            if(choices[0]==ans | choices[1]==ans){
                randomizeChoices();
            } else if(choices[0]==choices[1]){
                randomizeChoices();
            } else {
                testChoices=false;
            }
        }
    }
    
    public void randomizeChoices(){
        choices[0]=ans+((int)(Math.random()*random));
    	choices[1]=ans+((int)(Math.random()*random));
    }
    
    public void testUserAns(int userAns){
        if(userAns==ans){
            ++userScore;
            this.userAnswer=true;
        } else {
            this.userAnswer=false;
        }
    }
    
    public int getUserScore(){
    	return userScore;
    }
    
    public String getOperator(){
    	return operator;
    }
    
    public Integer[] getQuestions(){
    	return question;
    }
    
    public Integer[] getChoices(){
    	return choices;
    }
}