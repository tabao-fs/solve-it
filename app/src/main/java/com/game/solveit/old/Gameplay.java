package com.flashcards.solveit;


import java.util.Arrays;
import java.util.Collections;

import com.utilities.Arithmetics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class Gameplay extends Activity {
	Integer[] choices; 
	Integer[] questions;
	String operator="";
	
	TextView num1;
	TextView num2;
	TextView txtOperator;
	Button choice1;
	Button choice2;
	Button choice3;
	
	public static boolean newGame=false;
	
	public static Arithmetics startGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		
		if(newGame){
			getOperator();
			newGame=false;
		}
		setGame();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gameplay, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}
	
	
	public void getOperator(){
		Bundle oper = getIntent().getExtras();
		this.operator = oper.getString("operation");
		startGame = new Arithmetics(this.operator);
	}
	
	public void setGame(){
		startGame.fillAndShuffle();
		startGame.genRanQuestions();
		startGame.genRanChoices();
		
		this.choices = startGame.getChoices();
		this.questions = startGame.getQuestions();
		this.operator = startGame.getOperator();
		
		Collections.shuffle(Arrays.asList(this.choices));
		
		this.num1 = (TextView)findViewById(R.id.num1);
		this.num2 = (TextView)findViewById(R.id.num2);
		this.txtOperator = (TextView)findViewById(R.id.operator);
		this.choice1 = (Button)findViewById(R.id.choice1);
		this.choice2 = (Button)findViewById(R.id.choice2);
		this.choice3 = (Button)findViewById(R.id.choice3);
		
		this.num1.setText(String.valueOf(this.questions[startGame.r1]));
		this.num2.setText(String.valueOf(this.questions[startGame.r2]));
		this.txtOperator.setText(this.operator);
		this.choice1.setText(String.valueOf(this.choices[0]));
		this.choice2.setText(String.valueOf(this.choices[1]));
		this.choice3.setText(String.valueOf(this.choices[2]));
	}
	
	public void onClick(View view){
		switch(view.getId()){
			case R.id.choice1:
				startGame.testUserAns(this.choices[0]);
				break;
			case R.id.choice2:
				startGame.testUserAns(this.choices[1]);
				break;
			case R.id.choice3:
				startGame.testUserAns(this.choices[2]);
				break;
		}
		Intent showAns = new Intent(this, ShowAnswer.class);
		startActivity(showAns);
	}
	
}
