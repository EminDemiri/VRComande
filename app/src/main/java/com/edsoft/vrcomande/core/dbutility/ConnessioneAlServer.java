package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import java.sql.SQLException;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class ConnessioneAlServer {

    public static final String TABELLA = "connessioneserver";
    public static final String[] COLONNE =
            {
                    "ipserver", "portaserver", "autoupdatedb"
            };
    public static final String IPSERVER = "ipserver";
    public static final String PORTASERVER = "portaserver";
    public static final String AUTOUPDATEDB = "autoupdatedb";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt, boolean paramBoolean) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("ipserer", paramString);
        localContentValues.put("portaserver", Integer.valueOf(paramInt));
        localContentValues.put("autoupdatedb", Boolean.valueOf(paramBoolean));
        return paramSQLiteDatabase.update("connessioneserver", localContentValues, null, null) > 0;
    }

    public static boolean deleteAllServer(SQLiteDatabase paramSQLiteDatabase) {
        return paramSQLiteDatabase.delete("connessioneserver", null, null) > 0;
    }

    public static Cursor getAllServer(SQLiteDatabase paramSQLiteDatabase) {
        return paramSQLiteDatabase.query("connessioneserver", COLONNE, null, null, null, null, null);
    }

    public static Cursor getServer(SQLiteDatabase paramSQLiteDatabase, String paramString) throws SQLException {
        return paramSQLiteDatabase.query(true, "connessioneserver", COLONNE, "ipserver=" + paramString, null, null, null, null, null);
        // if (paramSQLiteDatabase != null) {
        //     paramSQLiteDatabase.moveToFirst();
        //  }
    }

    public static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt, boolean paramBoolean) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("ipserver", paramString);
        localContentValues.put("portaserver", Integer.valueOf(paramInt));
        localContentValues.put("autoupdatedb", Boolean.valueOf(paramBoolean));
        return paramSQLiteDatabase.insert("connessioneserver", null, localContentValues) > 0L;
    }

    public static void insertCfgServer(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt, boolean paramBoolean)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString, paramInt, paramBoolean)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0)
            {
                inserisci(paramSQLiteDatabase, paramString, paramInt, paramBoolean);
            }
            return;
        }
    }

}