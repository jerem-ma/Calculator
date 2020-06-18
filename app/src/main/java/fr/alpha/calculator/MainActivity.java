package fr.alpha.calculator;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * This method will call getResources() and return what is returned
	 */
	public Resources forwardResources(){
		return getResources();
	}
}
