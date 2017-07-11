package anurag.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class MainActivity2 extends AppCompatActivity {
    public static final String pos="1";
    int iduse;
    EditText date;
    EditText time;
    long edate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i=getIntent();
        final EditText title=(EditText) findViewById(R.id.title);
        final EditText notes=(EditText)findViewById(R.id.notes);
        time=(EditText) findViewById(R.id.time);
        date=(EditText) findViewById(R.id.date);
        final int myid =i.getIntExtra(MainActivity2.pos,-1);
        iduse=myid;
        if(myid==-1) {
            FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();

                    DetailsOpenHelper detailsOpenHelper =new DetailsOpenHelper(MainActivity2.this);
                    SQLiteDatabase db = detailsOpenHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DetailsOpenHelper.EXPENSE_TITLE, title.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_NOTES, notes.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_DATE, date.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_TIME, time.getText().toString());
                    db.insert(DetailsOpenHelper.EXPENSE_TABLE_NAME, null, cv);

                    setAlarm();
                    setResult(1, intent);
                    finish();
                }
            });
        }
        else
        {
            DetailsOpenHelper detailsOpenHelper =new DetailsOpenHelper(MainActivity2.this);
            SQLiteDatabase sqLiteDatabase = detailsOpenHelper.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.query(DetailsOpenHelper.EXPENSE_TABLE_NAME,null,DetailsOpenHelper.EXPENSE_ID+ "=" +myid,null,null,null,null);
            cursor.moveToNext();
            title.setText(cursor.getString(cursor.getColumnIndex(DetailsOpenHelper.EXPENSE_TITLE)));
            notes.setText(cursor.getString(cursor.getColumnIndex(DetailsOpenHelper.EXPENSE_NOTES)));
            date.setText(cursor.getString(cursor.getColumnIndex(DetailsOpenHelper.EXPENSE_DATE)));
            time.setText(cursor.getString(cursor.getColumnIndex(DetailsOpenHelper.EXPENSE_TIME)));
            FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    DetailsOpenHelper detailsOpenHelper = new DetailsOpenHelper(MainActivity2.this);
                    SQLiteDatabase db = detailsOpenHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DetailsOpenHelper.EXPENSE_TITLE, title.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_NOTES, notes.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_DATE, date.getText().toString());
                    cv.put(DetailsOpenHelper.EXPENSE_TIME, time.getText().toString());
                    db.update(DetailsOpenHelper.EXPENSE_TABLE_NAME,cv,DetailsOpenHelper.EXPENSE_ID + "=" + myid,null);
                    intent.putExtra(DetailsOpenHelper.EXPENSE_ID,myid);
                    setAlarm();
                    setResult(2, intent);
                    finish();
                }
            });
        }
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker timePicker=new TimePicker(MainActivity2.this);
                int hourOfDay=timePicker.getHour();
                int minute=timePicker.getMinute();
                boolean is24HourView=timePicker.is24HourView();
                showTimePicker(MainActivity2.this,hourOfDay, minute,is24HourView);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                int day=calendar.get(Calendar.DATE);
                showDatePicker(MainActivity2.this,year,month,day);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void setAlarm()
    {
        AlarmManager am=(AlarmManager) MainActivity2.this.getSystemService(ALARM_SERVICE);
        Intent i=new Intent(MainActivity2.this,MyReceiver.class);
        PendingIntent pendingIntent=(PendingIntent) PendingIntent.getBroadcast(MainActivity2.this,1,i,0);
        Calendar cal=Calendar.getInstance();
        String[] use=time.getText().toString().split(":");
        String[] dateuse=date.getText().toString().split("/");

        Log.i("myuse",dateuse[0]);
        Log.i("myuse",dateuse[1]);
        Log.i("myuse",dateuse[2]);
        cal.set(Calendar.MONTH,Integer.parseInt(dateuse[1])-1);
        cal.set(Calendar.YEAR,Integer.parseInt(dateuse[0]));
        cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateuse[2]));
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(use[0]));
        cal.set(Calendar.MINUTE,Integer.parseInt(use[1]));
        am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }
    public void showTimePicker(Context context,int hourOfDay,
                               int minute,
                               boolean is24HourView)
    {
        TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                time.setText(Integer.toString(i)+":"+ Integer.toString(i1));
            }
        }, hourOfDay, minute, is24HourView);
        timePickerDialog.show();
    }
    public void showDatePicker(Context context, final int year, final int month, final int day)
    {
        DatePickerDialog datePickerDialog=new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,day);
                edate=calendar.getTime().getTime();
                Log.i("epoch",Long.toString(edate));
                date.setText(i + "/" + (i1 + 1) + "/" + i2);
            }
        },year,month,day);
        datePickerDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity2menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.remove)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Remove");
            builder.setMessage("Are You Sure You Want To Remove");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DetailsOpenHelper detailsOpenHelper =new DetailsOpenHelper(MainActivity2.this);
                    SQLiteDatabase sqLiteDatabase = detailsOpenHelper.getReadableDatabase();
                    sqLiteDatabase.delete(DetailsOpenHelper.EXPENSE_TABLE_NAME,DetailsOpenHelper.EXPENSE_ID+ "=" +iduse,null);
                    setResult(3);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
