package eu.andlabs.gcp.examples.points;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import eu.andlabs.studiolounge.gcp.Lounge;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    }

    public void enterLobby(View v) {
        Lounge.startLoungeActivity(this);
    }
}
