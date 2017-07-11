package anurag.ui;

/**
 * Created by anurag on 09-07-2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DetailsOpenHelper extends SQLiteOpenHelper { //singlaton class

    public static final String EXPENSE_TABLE_NAME="Expense";
    public static final String EXPENSE_ID="_id";
    public static final String EXPENSE_TITLE="Title";
    public static final String EXPENSE_NOTES="Notes";
    public static final String EXPENSE_DATE="Date";
    public static final String EXPENSE_TIME="Time";
    public static final String TAG="openHelper";

    public DetailsOpenHelper(Context context)
    {
        super(context,"Details.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table " + EXPENSE_TABLE_NAME + " ( " + EXPENSE_ID + " integer primary key autoincrement, " + EXPENSE_TITLE +
                " text, " + EXPENSE_NOTES + " text, " + EXPENSE_DATE + " text, " + EXPENSE_TIME + " text);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG,"OnUpgrade");
    }
}
