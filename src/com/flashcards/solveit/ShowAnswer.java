package com.flashcards.solveit;

import com.utilities.DbUtils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class ShowAnswer extends Activity {
	TextView msg;
	Button btnCat,btnScore,btnHome;
	EditText value;
	ImageView status;
	
	String userName="";
	int userScore=0;
	int[] numScore;
	int lastScore;
	
	DbUtils dbUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_answer);
		
		msg = (TextView)findViewById(R.id.txt1);
		status = (ImageView)findViewById(R.id.imgCW);
		btnCat = (Button)findViewById(R.id.btnCat);
		btnHome = (Button)findViewById(R.id.btnHome);
		btnScore = (Button)findViewById(R.id.btnScore);
		
		btnCat.setVisibility(8);
		btnHome.setVisibility(8);
		btnScore.setVisibility(8);
		
		userScore = Gameplay.startGame.getUserScore();
		
		if(Gameplay.startGame.userAnswer){
			correctAnswer();
		} else {
			wrongAnswer();
			Gameplay.newGame=false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_answer, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}
	
	public void correctAnswer(){
		msg.setText("Your answer is correct!\nScore: "+userScore+"\nTap the screen to continue.");
		status.setImageResource(R.drawable.correct);
	}
	
	public void wrongAnswer(){
		msg.setText("Game Over!\nScore: "+userScore);
		btnCat.setVisibility(0);
		btnScore.setVisibility(0);
		btnHome.setVisibility(0);
		testUserScore();
	}
	
	public void testUserScore(){
		dbUtil = new DbUtils(this);
		dbUtil.open();
		dbUtil.createTable();
		dbUtil.search();
		numScore = dbUtil.getNumScore();
		lastScore = dbUtil.topScorers-1;
		if(dbUtil.noResults){
			createDialogBox();
		} else {
			if(userScore==numScore[lastScore]){
				
			} else if(userScore>numScore[lastScore] & userScore!=numScore[lastScore]) {
				createDialogBox();
			}			
		}
	}
	
	public void createDialogBox(){
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View promptView = layoutInflater.inflate(R.layout.prompts, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptView);
		
		value = (EditText) promptView.findViewById(R.id.userInput);
		
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								userName = value.getText().toString();
								newHighScore();
							}
						});
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.setTitle("High Score");
		alertD.setMessage("Enter Name:");
		alertD.show();
	}
	
	public void newHighScore(){
		if(dbUtil.insert(userName, userScore)!=-1){
			Toast.makeText(getApplicationContext(), "Your score was added successfully.",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), "Error! Please try again.",
					   Toast.LENGTH_LONG).show();
			createDialogBox();
		}
		dbUtil.close();
	}
	
	public void onClick(View view){
		if(Gameplay.startGame.userAnswer){
			switch(view.getId()){
			case R.id.statLay:
			case R.id.notifLay:
				Intent game = new Intent(this, Gameplay.class);
				startActivity(game);
				break;
			}
		} else {
			switch(view.getId()){
			case R.id.btnCat:
				Intent openCategories = new Intent(this, Categories.class);
				startActivity(openCategories);
				break;
			case R.id.btnScore:
				Intent openScore = new Intent(this, Scoreboard.class);
				startActivity(openScore);
				break;
			case R.id.btnHome:
				Intent openHome = new Intent(this, MainActivity.class);
				startActivity(openHome);
				break;
			}
		}
	}
	
}
