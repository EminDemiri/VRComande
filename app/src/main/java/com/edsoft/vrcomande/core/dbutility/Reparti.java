package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class Reparti {
    public static final String ALFAREPARTO = "descrizione";
    public static final String CODICE = "codice";
    public static final String[] COLONNE = { "codice", "descrizione", "posizione" };
    public static final String POSIZIONE = "posizione";
    public static final String TABELLA = "reparti";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("descrizione", paramString2);
        localContentValues.put("posizione", Integer.valueOf(paramInt));
        return paramSQLiteDatabase.update("reparti", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteReparto(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("reparti", "codice=" + paramString, null) > 0;
    }

    public static Cursor getAllReparti(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("reparti", COLONNE, null, null, null, null, "posizione");
    }

    public static Cursor getReparto(SQLiteDatabase paramSQLiteDatabase, String paramString)
            throws SQLException
    {
        return paramSQLiteDatabase.query(true, "reparti", COLONNE, "codice=" + paramString, null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("descrizione", paramString2);
        localContentValues.put("posizione", Integer.valueOf(paramInt));
        return paramSQLiteDatabase.insert("reparti", null, localContentValues) > 0L;
    }

    public static void insertReparto(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString1, paramString2, paramInt)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0) {
                inserisci(paramSQLiteDatabase, paramString1, paramString2, paramInt);
            }
            return;
        }
    }
}
