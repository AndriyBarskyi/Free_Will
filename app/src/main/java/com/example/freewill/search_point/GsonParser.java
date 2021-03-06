package com.example.freewill.search_point;

import android.app.Activity;
import android.content.Context;

import com.example.freewill.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.*;

public class GsonParser extends Activity {
    public Root parse() {
        Gson gson = new Gson();

        Context ctx = this.getBaseContext();

        try (InputStream inputStream = ctx.getResources().openRawResource(R.raw.data)) {
            Reader reader = new InputStreamReader(inputStream);
            return gson.fromJson(reader, Root.class);
        } catch (Exception e) {
            System.out.println("Parsing error " + e);
        }

        return null;
    }

    public GsonParser(Context ctx) {
        this.attachBaseContext(ctx);
    }
}


class Root {
    public ArrayList<Line> getLines() {
        return lines;
    }

    private ArrayList<Line> lines;
    private final Map<String, Integer> displayFromId = new HashMap<>(); // name:id


    public Map<String, Integer> getDisplayFromId() {
        if (displayFromId.isEmpty()) {
            initDisplay();
        }
        return displayFromId;
    }

    public String getKeyByValue(int value) {
        getDisplayFromId();
        for (Map.Entry<String, Integer> d : displayFromId.entrySet()) {
            if (d.getValue() == value) {
                return d.getKey();
            }
        }
        return "";
    }


    private void initDisplay() {
        displayFromId.put("237", 15);
        displayFromId.put("238", 13);
        displayFromId.put("239", 11);
        displayFromId.put("240", 10);
        displayFromId.put("241", 8);
        displayFromId.put("241а", 2);
        displayFromId.put("242", 4);
        displayFromId.put("243", 0);
        displayFromId.put("244", 6);
        displayFromId.put("211а", 18);
        displayFromId.put("211", 20);
        displayFromId.put("217а", 41);
        displayFromId.put("217б", 28);
        displayFromId.put("245", 37);
        displayFromId.put("221", 26);
        displayFromId.put("222", 29);
        displayFromId.put("223", 31);
        displayFromId.put("224", 33);
        displayFromId.put("225", 35);
        displayFromId.put("226", 38);
        displayFromId.put("двір", 21);

        displayFromId.put("01", 3);
        displayFromId.put("02", 1);
        displayFromId.put("03", 5);
        displayFromId.put("04", 7);
        displayFromId.put("05", 17);
        displayFromId.put("06", 9);
        displayFromId.put("07", 19);
        displayFromId.put("08", 12);
        displayFromId.put("09", 22);
        displayFromId.put("010", 14);
        displayFromId.put("011", 40);
        displayFromId.put("012", 16);
        displayFromId.put("013", 23);
        displayFromId.put("014", 24);
        displayFromId.put("015", 25);
        displayFromId.put("016", 27);
        displayFromId.put("017", 30);
        displayFromId.put("018", 32);
        displayFromId.put("019", 34);
        displayFromId.put("020", 36);
        displayFromId.put("021", 39);
    }

    public Point getPointById(String id) {
        for (Line l : lines) {
            if (l.getP1().getId().equals(id)) {
                return l.getP1();
            }
            if (l.getP2().getId().equals(id)) {
                return l.getP2();
            }
        }
        return new Point();
    }

    public boolean isAudience(String id) {
        getDisplayFromId();
        return displayFromId.containsKey(id);
    }

}

class Point {
    private String id;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("P\t X: %d, Y: %d\n", getX(), getY());
    }
}

class Line {
    private Point p1;
    private Point p2;

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public int weight() {
        return (int) Math.round(Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2)));
    }

    @Override
    public String toString() {
        return String.format("P1\t X: %d, Y: %d\nP2\t X: %d, Y: %d", getP1().getX(), getP1().getY(), getP2().getX(), getP2().getY());
    }
}
