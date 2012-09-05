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
import eu.andlabs.studiolounge.gcp.Lounge;
import eu.andlabs.studiolounge.gcp.Lounge.GameMsgListener;


public class PointsGameActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final HashMap<String, Circle> players = new HashMap<String, Circle>();


        final View view = new View(this) {
            
            Paint paint = new Paint();
            
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                for (Circle circle : players.values()) {
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(circle.x, circle.y, 23, paint);
                }
            }};
        setContentView(view);



        Lounge.getInstance(this).register(new GameMsgListener() {
            
            @Override
            public void onMessageRecieved(Bundle msg) {
                String player = msg.getString("who");
                Circle circle = players.get(player);
                if (circle == null) {
                    circle = new Circle();
                    players.put(player, circle);
                }
                circle.x = Long.valueOf(msg.getString("x"));
                circle.y = Long.valueOf(msg.getString("y"));
                circle.color = msg.getString("color");
                view.invalidate();
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
            Lounge.getInstance(this).sendGameMessage(b);
        }
        if (cnt % 10 == 0) Log.d("Lounge", "Number of Move Messages: " + cnt);
        return true;
    }

    static class Circle {
        long x;
        long y;
        String color;
    }
}
