package com.flashcards.solveit;

import com.utilities.DbUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;

public class MainActivity extends Activity {
	public static final String tag = "print";
	DbUtils dbUtil;
	ImageView play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (ImageView)findViewById(R.id.playGame);
		
//		dbUtil = new DbUtils(this);
//		dbUtil.open();
//		dbUtil.dropTable();
//		dbUtil.createTable();
//		dbUtil.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		moveTaskToBack(true);
		finish();
	}
	
	public void onClick(View view){
		switch(view.getId()){
			case R.id.playGame:
				Intent categories = new Intent(this, Categories.class);
				startActivity(categories);
				break;
			case R.id.highScore:
				Intent highScore = new Intent(this, Scoreboard.class);
				startActivity(highScore);
				break;
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
	}
	
}
