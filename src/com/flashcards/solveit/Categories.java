package com.flashcards.solveit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;

public class Categories extends Activity {
	String operator="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		Intent home = new Intent(this, MainActivity.class);
		startActivity(home);
	}
	
	public void onClick(View view){
		switch(view.getId()){
			case R.id.cAdd:
				operator="+";
				break;
			case R.id.cSub:
				operator="-";
				break;
			case R.id.cMul:
				operator="x";
				break;
			case R.id.cDiv:
				operator="/";
				break;
		}
		Gameplay.newGame=true;
		Intent gamePlay = new Intent(this, Gameplay.class);
		gamePlay.putExtra("operation", operator);
		startActivity(gamePlay);
	}
	
}
