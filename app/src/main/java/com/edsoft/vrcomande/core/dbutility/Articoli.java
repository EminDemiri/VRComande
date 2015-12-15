package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class Articoli {

    public static final String TABELLA = "articoli";
    public static final String[] COLONNE = {
            "codice", "descrizione", "prezzo", "iva", "reparto", "posizione"
    };
    public static final String CODICE = "codice";
    public static final String ALFAARTICOLO = "descrizione";
    public static final String PREZZODIVENDITA = "prezzo";
    public static final String ALIQUOTAIVA = "iva";
    public static final String CODICEREPARTO = "reparto";
    public static final String POSIZIONE = "posizione";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, int paramInt)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("descrizione", paramString2);
        localContentValues.put("prezzo", Double.valueOf(paramDouble1));
        localContentValues.put("iva", Double.valueOf(paramDouble2));
        localContentValues.put("reparto", paramString3);
        localContentValues.put("posizione", Integer.valueOf(paramInt));
        return paramSQLiteDatabase.update("articoli", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteArticolo(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("articoli", "codice=" + paramString, null) > 0;
    }

    public static Cursor getAllArticoli(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("articoli", COLONNE, null, null, null, null, "posizione");
    }

    public static Cursor getAllArticoli(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.query("articoli", COLONNE, "reparto='" + paramString + "'", null, null, null, "posizione");
    }

    public static Cursor getAllArticolo(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.query(true, "articoli", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLitaDatabase, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, int paramInt)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("descrizione", paramString2);
        localContentValues.put("prezzo", Double.valueOf(paramDouble1));
        localContentValues.put("iva", Double.valueOf(paramDouble2));
        localContentValues.put("reparto", paramString3);
        localContentValues.put("posizione", Integer.valueOf(paramInt));
        return paramSQLitaDatabase.insert("articoli", null, localContentValues) > 0L;
    }

    public static void insertArticoli(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, int paramInt)
    {
        if (!aggiorna(paramSQLiteDatabase,paramString1,paramString2,paramString3,paramDouble1,paramDouble2,paramInt)){
        }
        for (int i = 1;; i=0)
        {
            if (i != 0)
            {
                inserisci(paramSQLiteDatabase,paramString1,paramString2,paramString3,paramDouble1,paramDouble2,paramInt);
            }
            return;
        }
    }

}
