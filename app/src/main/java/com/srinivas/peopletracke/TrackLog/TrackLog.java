package com.srinivas.peopletracke.TrackLog;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.srinivas.peopletracke.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TrackLog extends Activity {
    RecyclerView tracklog_rv;
    ArrayList<Track> tracks;
    TrackAdapter trackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_log);

        tracklog_rv = findViewById(R.id.tracklog_rv);
        tracklog_rv.setLayoutManager(new LinearLayoutManager(TrackLog.this));
        getcheckins_from_local();

    }

    public void getcheckins_from_local() {

        tracks = new ArrayList<Track>();
        tracks.clear();
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String cdt_date = sdf.format(cd.getTime());

        Log.d("displaycount", cdt_date.toString());
        SQLiteDatabase db;
        db = openOrCreateDatabase("RMAT", Context.MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT * FROM checktime ORDER BY cdt DESC", null);
        Log.d("overallstring", c.toString());
        String ccc = String.valueOf(c.getCount());
        // Toast.makeText(getBaseContext(),"installation "+ccc.toString(),Toast.LENGTH_SHORT).show();
        Log.d("displaycount", ccc);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {

                tracks.add(new Track(c.getString(c.getColumnIndex("latitude")), c.getString(c.getColumnIndex("longitude")),
                        c.getString(c.getColumnIndex("cdt")), c.getString(c.getColumnIndex("status")),
                        c.getString(c.getColumnIndex("deviceid"))));

               /* if (Validations.hasActiveInternetConnection(Messages.this)) {
                    Log.d("mesage", c.getString(c.getColumnIndex("msg")));
                    Log.d("localdb", "internet connection");
                    if (c.getString(c.getColumnIndex("status")).equals("local")) {
                        Log.d("mystatus_local", "Locall status");
                        sendMessage(c.getString(c.getColumnIndex("latitude")), c.getString(c.getColumnIndex("longitude")),
                                c.getString(c.getColumnIndex("cdt")),c.getString(c.getColumnIndex("status")),
                                c.getString(c.getColumnIndex("deviceid")), true);
                    }
                }*/
                c.moveToNext();
            }
        }
        db.close();

        trackAdapter = new TrackAdapter(tracks, R.layout.tracksingle, getApplicationContext());
        tracklog_rv.setAdapter(trackAdapter);
        trackAdapter.notifyDataSetChanged();
        //finish();
    }


}
