package eu.andlabs.gcp.examples.points;

import java.util.HashMap;
import java.util.Map;

import eu.andlabs.studiolounge.gcp.Lounge;
import eu.andlabs.studiolounge.gcp.Lounge.LobbyListener;
import eu.andlabs.studiolounge.gcp.Lounge.GameMsgListener;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;


public class PointsGameActivity extends Activity {

    private Paint paint;
    private Lounge lounge;
    private Map<String, Circle> players;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        lounge = new Lounge(this);
        lounge.register(new LobbyListener() {
            
            @Override
            public void onPlayerLoggedIn(String player) {}
            
            @Override
            public void onPlayerLeft(String player) {}
            
            @Override
            public void onNewHostedGame(String player, String Game) {
                lounge.joinGame(player);
            }
            
            @Override
            public void onPlayerJoined(String player) {
            }
        });
        
        players = new HashMap<String, Circle>();
        lounge.register(new GameMsgListener() {
            
            @Override
            public void onMessageRecieved(Bundle msg) {
                String player = msg.getString("who");
                Circle circle = players.get(player);
                if (circle == null) {
                    circle = new Circle();
                    players.put(player, circle);
                }
                circle.x = msg.getLong("x");
                circle.y = msg.getLong("y");
                circle.color = msg.getString("color");
                view.invalidate();
            }
        });
        
        paint = new Paint();
        
        view = new View(this) {
            
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                for (Circle circle : players.values()) {
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(circle.x, circle.y, 23, paint);
                }
            }};
        setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Bundle b = new Bundle();
        b.putLong("x", (long) event.getX());
        b.putLong("y", (long) event.getY());
        b.putString("color", "#6e28bd");
        lounge.sendGameMessage(b);
        return true;
    }

    static class Circle {
        long x;
        long y;
        String color;
    }
}
