package com.matthewtole.androidrise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
	
	public void playClicked(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
