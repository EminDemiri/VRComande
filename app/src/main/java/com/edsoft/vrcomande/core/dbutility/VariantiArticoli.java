package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class VariantiArticoli {
    public static final String ALFAVARIANTE = "alfavariante";
    public static final String CODICE = "codice";
    public static final String[] COLONNE = { "codice", "alfavariante", "prezzovariante", "variantepertutti" };
    public static final String PREZZOVARIANTE = "prezzovariante";
    public static final String TABELLA = "variantiarticoli";
    public static final String VARIANTEPERTUTTI = "variantepertutti";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, double paramDouble, boolean paramBoolean)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfavariante", paramString2);
        localContentValues.put("prezzovariante", Double.valueOf(paramDouble));
        localContentValues.put("variantepertutti", Boolean.valueOf(paramBoolean));
        return paramSQLiteDatabase.update("variantiarticoli", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static boolean deleteVariante(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("variantiarticoli", "codice='" + paramString + "'", null) > 0;
    }

    public static Cursor getAllVarianti(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("variantiarticoli", COLONNE, null, null, null, null, null);
    }

    public static Cursor getVariante(SQLiteDatabase paramSQLiteDatabase, String paramString)
            throws SQLException
    {
        return paramSQLiteDatabase.query(true, "variantiarticoli", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    public static Cursor getVariantiPerTutti(SQLiteDatabase paramSQLiteDatabase)
            throws SQLException
    {
        return paramSQLiteDatabase.query(true, "variantiarticoli", COLONNE, "variantepertutti = 1", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, double paramDouble, boolean paramBoolean)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfavariante", paramString2);
        localContentValues.put("prezzovariante", Double.valueOf(paramDouble));
        localContentValues.put("variantepertutti", Boolean.valueOf(paramBoolean));
        return paramSQLiteDatabase.insert("variantiarticoli", null, localContentValues) > 0L;
    }

    public static void insertVariante(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, double paramDouble, boolean paramBoolean)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString1, paramString2, paramDouble, paramBoolean)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0) {
                inserisci(paramSQLiteDatabase, paramString1, paramString2, paramDouble, paramBoolean);
            }
            return;
        }
    }
}
