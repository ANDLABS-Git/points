package eu.andlabs.gcp.examples.points;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
	}
	
	
	public void enterLobby(View v) {
		
		Intent i = new Intent();
		i.setPackage("eu.andlabs.studiolounge");
		i.setClassName(this,"eu.andlabs.studiolounge.LoungeMainActivity" );
		startActivity(i);
		
	}
}
