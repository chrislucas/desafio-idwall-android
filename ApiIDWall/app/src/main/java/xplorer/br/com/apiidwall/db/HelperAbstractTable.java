package xplorer.br.com.apiidwall.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public abstract class HelperAbstractTable {

    private ImplSQLiteOpenHelper databaseHelper;
    private Context context;

    public HelperAbstractTable(Context context) throws IOException {
        this.databaseHelper = ImplSQLiteOpenHelper.getInstance(context);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public ImplSQLiteOpenHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public Cursor getCursor(SQLiteDatabase db, String sql, String[] args) {
        Cursor cursor = null;
        if(db != null) {
            try {
                cursor = db.rawQuery(sql, args);
            } catch (Exception e) {
                Log.e("EXCP_GET_CURSOR", e.getMessage());
            }
        }
        return cursor;
    }

    public Cursor getCursor(SQLiteDatabase db, String sql) {
        return getCursor(db, sql, null);
    }

    public abstract boolean createTable();
}
