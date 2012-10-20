package eu.andlabs.gcp.examples.points;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import eu.andlabs.studiolounge.LoungeActivity;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    }

    public void enterLobby(View v) {
        startActivity(new Intent(this, LoungeActivity.class));
    }
}
