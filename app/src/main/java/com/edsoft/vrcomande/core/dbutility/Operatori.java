package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class Operatori {
    public static final String ALFAOPERATORE = "alfaoperatore";
    public static final String CODICE = "codice";
    public static final String[] COLONNE = { "codice", "alfaoperatore", "login", "password" };
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String TABELLA = "operatori";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfaoperatore", paramString2);
        localContentValues.put("login", paramString3);
        localContentValues.put("password", paramString4);
        return paramSQLiteDatabase.update("operatori", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteOperatore(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("operatori", "codice=" + paramString, null) > 0;
    }

    public static Cursor getAllOperatori(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("operatori", COLONNE, null, null, null, null, "alfaoperatore");
    }

    public static Cursor getOperatore(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.query(true, "operatori", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    public static Cursor getOperatore(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        return paramSQLiteDatabase.query(true, "operatori", COLONNE, "login='" + paramString1 + "'" + " AND " + "password" + "=" + "'" + paramString2 + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfaoperatore", paramString2);
        localContentValues.put("login", paramString3);
        localContentValues.put("password", paramString4);
        return paramSQLiteDatabase.insert("operatori", null, localContentValues) > 0L;
    }

    public static void insertOperatore(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString1, paramString2, paramString3, paramString4)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0) {
                inserisci(paramSQLiteDatabase, paramString1, paramString2, paramString3, paramString4);
            }
            return;
        }
    }
}
