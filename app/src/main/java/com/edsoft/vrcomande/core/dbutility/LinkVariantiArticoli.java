package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class LinkVariantiArticoli {

    public static final String CODICE_ARTICOLO = "codice_articolo";
    public static final String CODICE_VARIANTE = "codice_variante";
    public static final String[] COLONNE =
            {
                    "id", "codice_variante", "codice_articolo"
            };
    public static final String ID = "id";
    public static final String TABELLA = "LinkVariantiArticoli";

    public static boolean deleteVariante(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("LinkVariantiArticoli", "codice_variante=" + paramString, null) > 0;
    }

    public static Cursor getAllArtLink(SQLiteDatabase paramSQLiteDatabase, String paraString)
    {
        return paramSQLiteDatabase.query("LinkVariantiArticoli", COLONNE, "codice_artico='" + paraString + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice_variante", paramString1);
        localContentValues.put("codice_articolo", paramString2);
        return paramSQLiteDatabase.insert("LinkVariantiArticoli", null, localContentValues) > 0L;
    }

    public static void insertVariante(SQLiteDatabase paramSQLiteDatabase, String paramString1 , String paramString2)
    {
        inserisci(paramSQLiteDatabase, paramString1, paramString2);
    }
}
