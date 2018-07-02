package xplorer.br.com.apiidwall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.utils.ReaderPropertiesFile;

public class HelperUserTable extends HelperAbstractTable {

    public static final String TABLE_NAME = "users";
    public static final String [] FIELDS = {
            "email"
            ,"token"
            ,"create_at"
            ,"last_login"
    };

    public HelperUserTable(Context context) throws IOException {
        super(context);
    }

    @Override
    public boolean createTable() {
        boolean create = false;
        try {
            Context context = getContext();
            ImplSQLiteOpenHelper instance     = getDatabaseHelper();
            SQLiteDatabase db  = instance.getWritableDatabase();
            /**
             * abrir o arquivo que contem as tabelas dessa aplicacao
             * */
            Properties properties = ReaderPropertiesFile.get(context, "db/create_tables.properties");
            /**
             * PEGAR a propriedade correspondente pelo nome da tabela
             * */
            String sql = properties.getProperty(TABLE_NAME);
            /**
             * executar a query contida no arquivo properties cuja propriedade esta vinculada a chave TABLE_NAME
             * */
            db.execSQL(sql);
            create = true;
        }
        catch (SQLException sqle) {
            Log.e("SQL_EXCP_HELPER_USER", sqle.getMessage());
        }
        return create;
    }

    public long update(User user, String whereArgs, String [] args) throws Exception {
        long affected = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELDS[0], user.getEmail());
        contentValues.put(FIELDS[1], user.getToken());
        contentValues.put(FIELDS[2], user.getCreatedAt());
        contentValues.put(FIELDS[3], user.getUpdatedAt());
        SQLiteDatabase db = null;
        try {
            db = getDatabaseHelper().getWritableDatabase();
            if (db == null)
                throw new Exception("Não foi possivel recuperar instância para acessar o BD");
            affected = db.update(TABLE_NAME, contentValues, whereArgs, args);
        }
        catch (SQLException sqlex) {
            Log.e("UPDATE_USER", sqlex.getMessage());
        }
        finally {
            if(db != null) {
                db.close();
            }
        }
        return affected;
    }

    public boolean exists(User user) throws Exception {
        ImplSQLiteOpenHelper instance = getDatabaseHelper();
        if (instance == null)
            throw new Exception("Não foi possivel recuperar instância para acessar o BD");
        SQLiteDatabase db = instance.getReadableDatabase();
        String query  = String.format("SELECT COUNT(*) FROM %s WHERE %s=? AND %s=?"
                , TABLE_NAME, FIELDS[0], FIELDS[1]);
        String args []    = {user.getEmail(), user.getToken()};
        Cursor cursor     = getCursor(db, query, args);
        boolean answer    = false;
        if(cursor != null && cursor.moveToFirst()) {
            int n = cursor.getInt(0);
            answer = (n > 0);  // >= eh desnecessario mas
        }
        return answer;
    }

    public long insert(User user) {
        long id = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELDS[0], user.getEmail());
        contentValues.put(FIELDS[1], user.getToken());
        contentValues.put(FIELDS[2], user.getCreatedAt());
        contentValues.put(FIELDS[3], user.getUpdatedAt());
        ImplSQLiteOpenHelper instance = getDatabaseHelper();
        SQLiteDatabase db = instance.getWritableDatabase();
        try {
            id = db.insertOrThrow(TABLE_NAME, null, contentValues);
        }
        catch (SQLException sqlex) {
            Log.e("EXCP_INSERT_USER", sqlex.getMessage());
        }
        finally {
            db.close();
        }
        return id;
    }


}
