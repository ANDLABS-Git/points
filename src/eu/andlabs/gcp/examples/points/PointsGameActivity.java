package eu.andlabs.gcp.examples.points;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import eu.andlabs.gcp.examples.points.PointsGameActivity.Circle;
import eu.andlabs.studiolounge.gcp.Lounge;
import eu.andlabs.studiolounge.gcp.Lounge.GameMsgListener;


public class PointsGameActivity extends Activity {

    private View mView;
    private Lounge mLounge;
    private HashMap<String, Circle> mPlayers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPlayers = new HashMap<String, Circle>();


        mView = new View(this) {
            
            Paint paint = new Paint();
            
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                for (Circle circle : mPlayers.values()) {
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(circle.x, circle.y, 23, paint);
                }
            }};
        setContentView(mView);
    }
    
    @Override
    protected void onStart() {
        Log.d("Lounge", "POINTS on START");
        super.onStart();
        mLounge = new Lounge(this);
        mLounge.register(new GameMsgListener() {
            
            @Override
            public void onMessageRecieved(Bundle msg) {
                String player = msg.getString("who");
                Circle circle = mPlayers.get(player);
                if (circle == null) {
                    circle = new Circle();
                    mPlayers.put(player, circle);
                }
                circle.x = Long.valueOf(msg.getString("x"));
                circle.y = Long.valueOf(msg.getString("y"));
                circle.color = msg.getString("color");
                mView.invalidate();
            }
        });
        
    }

    int cnt;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cnt++;
        if (cnt % 3 == 0) {
            Bundle b = new Bundle();
            b.putLong("x", (long) event.getX());
            b.putLong("y", (long) event.getY());
            b.putString("color", "#6e28bd");
            mLounge.sendGameMessage(b);
        }
        if (cnt % 10 == 0) Log.d("Lounge", "Number of Move Messages: " + cnt);
        return true;
    }

    static class Circle {
        long x;
        long y;
        String color;
    }
    
    @Override
    protected void onStop() {
        Log.d("Lounge", "POINTS on STOP");
        unbindService(mLounge);
        super.onDestroy();
    }
}
