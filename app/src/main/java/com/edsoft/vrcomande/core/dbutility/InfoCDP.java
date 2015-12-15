package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class InfoCDP {

    public static final String ALFACDP = "alfacdp";
    public static final String CODICE = "codice";
    public static final String[] COLONNE =
            {
                    "codice", "alfacdp"
            };
    public static final String TABELLA = "CentriDiProduzione";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfacdp", paramString2);
        return paramSQLiteDatabase.update("CentriDiProduzione", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteCDP(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("CentriDiProduzione", "codice='" + paramString + "'", null) > 0;
    }

    public static Cursor getAllCDP(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("CentriDiProduzione", COLONNE, null, null, null, null, null);
    }

    public static Cursor getCDP(SQLiteDatabase paramSQLiteDatabase, String paramString)
            throws SQLDataException
    {
        return paramSQLiteDatabase.query(true,"CentriDiProduzione", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfacdp", paramString2);
        return paramSQLiteDatabase.insert("CentriDiProduzione", null, localContentValues) > 0L;
    }

    public static void insertCDP(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString1,paramString2)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0)
            {
                inserisci(paramSQLiteDatabase,paramString1,paramString2);
            }
            return;
        }
    }

}
