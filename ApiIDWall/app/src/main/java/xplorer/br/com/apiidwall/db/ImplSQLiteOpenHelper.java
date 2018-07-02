package xplorer.br.com.apiidwall.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import xplorer.br.com.apiidwall.utils.ReaderPropertiesFile;

public class ImplSQLiteOpenHelper extends SQLiteOpenHelper {

    private static SoftReference<ImplSQLiteOpenHelper> weakReferenceInstance;
    private Context context;
    private int version;
    private String filenameToCreateDb, filenameToUpdateDb, fileNameToDropDb, name;

    private ImplSQLiteOpenHelper(Context context, String name
            , SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static ReaderPropertiesFile.LinkedHashProperties readProperties(Context context, String filename) {
        return ReaderPropertiesFile.get(context, filename);
    }

    /**
     * Lendo o arquivo properties sobre os dados do banco de dados
     * */
    private static Properties readPropertiesFileMetadataDatabase(Context context) {
        return ReaderPropertiesFile.get(context, "db/dbdata.properties");
    }

    public synchronized static ImplSQLiteOpenHelper getInstance(Context context) throws IOException{
        if (weakReferenceInstance == null) {
            Properties properties = readPropertiesFileMetadataDatabase(context);
            if (properties == null)
                throw new IOException("Problemas ao ler o arquivo de versao da base de dados");

            else {
                int version = Integer.parseInt(properties.getProperty("version"));
                String name = properties.getProperty("name");
                ImplSQLiteOpenHelper implSQLiteOpenHelper = new ImplSQLiteOpenHelper(context, name, null, version);
                implSQLiteOpenHelper.context = context;
                implSQLiteOpenHelper.version = version;
                implSQLiteOpenHelper.name = name;
                implSQLiteOpenHelper.filenameToCreateDb   = "db/create_tables.properties";
                implSQLiteOpenHelper.filenameToUpdateDb   = "db/update_tables.properties";
                implSQLiteOpenHelper.fileNameToDropDb     = "db/drop_tables.properties";
                weakReferenceInstance = new SoftReference<>(implSQLiteOpenHelper);
            }
        }
        return weakReferenceInstance.get();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db != null) {
            boolean was = executeQueries(db, filenameToCreateDb, "CREATE");
            if(!was) {
                Log.e("EXCP_CREATE_TABLES", "Erro ao criar as tabelas");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(db != null) {
            boolean was = executeQueries(db, fileNameToDropDb, "DELETE");
            if(was) {
                was = executeQueries(db, filenameToUpdateDb, "UPDATE");
                if(!was) {
                    // problema
                    Log.e("EXCP_UPDATE_TABLES"
                            , String.format("Erro ao atualizar as tabelas da versao %d para %d"
                                    , oldVersion, newVersion));
                }
            }
            else {
                // problema
                Log.e("EXCP_DELETE_TABLES", "Erro ao criar as tabelas");
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(db != null) {
            boolean was = executeQueries(db, fileNameToDropDb, "DELETE");
            if(was) {
                was = executeQueries(db, filenameToUpdateDb, "DOWNGRADE");
                if(!was) {
                    // problema
                    Log.e("EXCP_CREATE_TABLES"
                            , String.format("Erro ao criar/desatualizar as tabelas da versao %d para %d"
                                    , oldVersion, newVersion));
                }
            }
            else {
                Log.e("EXCP_DOWNGRADE_TABLES"
                        , String.format("Erro ao desatualizar as tabelas da versao %d para %d"
                                , oldVersion, newVersion));
            }
        }
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }

    public boolean databaseFileExists() {
        if(name != null) {
            File file = context.getDatabasePath(name);
            return file != null && file.exists();
        }
        return false;
    }

    public boolean createIfNotExists() {
        boolean created = false;
        try {
            this.open();
            created = this.databaseFileExists();
            this.close();
        } catch (Exception e) {
            Log.e("EXCP_ON_CHECK_EXISTS", e.getMessage());
        }
        return created;
    }

    private boolean executeQueries(SQLiteDatabase db, String pathPropertyFile, String category) {
        ReaderPropertiesFile.LinkedHashProperties properties = readProperties(context, pathPropertyFile);
        boolean response = false;
        if(properties != null) {
            Set<Map.Entry<Object, Object>> set = properties.entrySet();
            db.beginTransaction();
            try {
                for(Map.Entry<Object, Object> pair : set) {
                    Object key   = pair.getKey();
                    Object value = pair.getValue();
                    String query = value.toString();
                    db.execSQL(query);
                    String table = key.toString();
                    Log.i(category, String.format("%s: %s", table, query));
                }
                // Se deu certo a execucao da query, finaliza a transacao como sucesso
                db.setTransactionSuccessful();
                response = true;
            }
            catch(SQLException sqlexp) {
                String message = String.format("%s\n%s", sqlexp.getMessage(), sqlexp.getCause());
                Log.e("SQLEXCEPTION_CREATE", message);
                response = false;
            }
            finally {
                db.endTransaction();
            }
        }
        return response;
    }

}
