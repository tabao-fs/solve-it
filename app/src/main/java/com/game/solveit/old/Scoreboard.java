package com.flashcards.solveit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.utilities.DbUtils;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Scoreboard extends Activity {
	
	TextView scores, status;
	
	DbUtils dbUtil;
	
	File sdCard, file;
	
	String path, xmlData;
	
	public static final String FILENAME = "highscore.xml";
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard);
		
		scores = (TextView) findViewById(R.id.scores);
		status = (TextView)findViewById(R.id.status);
		
		dbUtil = new DbUtils(this);
		dbUtil.open();
		showHighScore();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		dbUtil.close();
		Intent openHome = new Intent(this, MainActivity.class);
		startActivity(openHome);
	}
	
	public void showHighScore() {
		scores.setText(dbUtil.display());
	}
	
	public void onClick(View v){
		if(v.getId()==R.id.btnUp){
			setXml();
		}
	}
	
	public void setXml(){
		sdCard = getExternalFilesDir(null);
		
		//view path
		path = sdCard.getAbsolutePath();
		Log.d("print", path);
		
		file = new File(sdCard, FILENAME);
		
		if(checkExternal()){
			createXml();
		}
	}
	
	public void createXml(){
		try {
			xmlData = dbUtil.createXml();
			url = ("http://10.0.2.2:8080/SolveIt/DataHandler");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(xmlData.getBytes());
			fos.close();
			sendXml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendXml(){
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Accept", "text/xml");
            httppost.addHeader("Content-Type", "application/xml");
            
        	StringEntity entity = new StringEntity(xmlData, "UTF-8");
        	entity.setContentType("application/xml");
        	httppost.setEntity(entity);
        	httpClient.execute(httppost);
        
        	Toast.makeText(this, "XML posted succesfully.",Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
        	ex.printStackTrace();
        	status.setText("Check your internet connection.");
        }
	}
	
	public boolean checkExternal(){
		String state = Environment.getExternalStorageState();
		String exState;
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			exState="External Storage is present.";
			Log.d(MainActivity.tag, exState);
			status.setText(exState);
			return true;
		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			exState="External Storage is read-only.";
			Log.d(MainActivity.tag, exState);
			status.setText(exState);
		} else {
			exState="No external storage found.";
			Log.d(MainActivity.tag, exState);
			status.setText(exState);
		}
		return false;
	}
	
}
