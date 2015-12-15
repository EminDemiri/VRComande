package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class MsgForCDP {

    public static final String CDP = "cdp";
    public static final String CODICE = "codice";
    public static final String[] COLONNE =
            {
                    "codice","testo","cdp"
            };
    public static final String TABELLA = "MsgForCDP";
    public static final String TESTO = "testo";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("testo", paramString2);
        localContentValues.put("cdp",paramString3);
        return paramSQLiteDatabase.update("MsgForCDP", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteMsgForCDP(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("MsgForCDP", "codice='" + paramString + "'", null) > 0;
    }

    public static Cursor getAllMsgForCDP(SQLiteDatabase paramSQLiteDatebase)
    {
        return paramSQLiteDatebase.query("MsgForCDP", COLONNE, null, null, null, null, null);
    }

    public static Cursor getMsgForCDP(SQLiteDatabase paramSQLiteDatabase, String paramString)
            throws SQLDataException
    {
        return paramSQLiteDatabase.query(true, "MsgForCDP", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("testo", paramString2);
        localContentValues.put("cdp", paramString3);
        return paramSQLiteDatabase.insert("MsgForCDP", null, localContentValues) > 0L;
    }

    public static void insertMsgForCDP(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3)
    {
        if (!aggiorna(paramSQLiteDatabase,paramString1,paramString2,paramString3)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0)
            {
                inserisci(paramSQLiteDatabase,paramString1,paramString2,paramString3);
            }
            return;
        }
    }

}
