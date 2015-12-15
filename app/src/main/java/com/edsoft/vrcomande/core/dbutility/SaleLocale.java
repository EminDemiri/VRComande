package com.edsoft.vrcomande.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.edsoft.vrcomande.core.networkutility.SaleXML;

import java.util.ArrayList;
import java.lang.Exception;

/**
 * Created by EDSoft on 09/12/2015.
 */

public class SaleLocale {
    public static final String ALFASALA = "alfasala";
    public static final String CODICE = "codice";
    public static final String[] COLONNE = { "codice", "alfasala", "numerotavoli", "deltatavoli" };
    public static final String DELTATAVOLI = "deltatavoli";
    public static final String NUMEROTAVOLI = "numerotavoli";
    public static final String TABELLA = "sale";

    public static boolean aggiorna(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfasala", paramString2);
        localContentValues.put("numerotavoli", Integer.valueOf(paramInt1));
        localContentValues.put("deltatavoli", Integer.valueOf(paramInt2));
        return paramSQLiteDatabase.update("sale", localContentValues, "codice=" + paramString1, null) > 0;
    }

    public static void bulkInsertSale(SQLiteDatabase paramSQLiteDatabase, ArrayList<SaleXML> paramArrayList)
    {
        paramSQLiteDatabase.beginTransaction();
        try
        {
            SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO sale (codice, alfasala, numerotavoli, deltatavoli) VALUES (?, ?, ?, ?)");
            int i = 0;
            while (i < paramArrayList.size())
            {
                localSQLiteStatement.bindString(1, ((SaleXML)paramArrayList.get(i)).getCodSala());
                localSQLiteStatement.bindString(2, ((SaleXML)paramArrayList.get(i)).getAlfaSala());
                localSQLiteStatement.bindString(3, Integer.toString(((SaleXML)paramArrayList.get(i)).getNumeroTavoli()));
                localSQLiteStatement.bindString(4, Integer.toString(((SaleXML)paramArrayList.get(i)).getDeltaTavoli()));
                localSQLiteStatement.execute();
                localSQLiteStatement.clearBindings();
                i += 1;
            }
            paramSQLiteDatabase.setTransactionSuccessful();
            return;
        }
        catch (Exception paramArrayList)
        {
            if (paramArrayList.getMessage() == null) {}
            for (paramArrayList = "bulkInsert failed";; paramArrayList = paramArrayList.getMessage())
            {
                Log.e("bulkInsert:", paramArrayList);
                return;
            }
        }
        finally
        {
            paramSQLiteDatabase.endTransaction();
        }
    }

    public static boolean deleteSala(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.delete("sale", "codice=" + paramString, null) > 0;
    }

    public static Cursor getAllSale(SQLiteDatabase paramSQLiteDatabase)
    {
        return paramSQLiteDatabase.query("sale", COLONNE, null, null, null, null, null);
    }

    public static Cursor getSala(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.query(true, "sale", COLONNE, "codice='" + paramString + "'", null, null, null, null, null);
    }

    public static Cursor getSalaByDescr(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        return paramSQLiteDatabase.query(true, "sale", COLONNE, "alfasala='" + paramString + "'", null, null, null, null, null);
    }

    private static boolean inserisci(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("codice", paramString1);
        localContentValues.put("alfasala", paramString2);
        localContentValues.put("numerotavoli", Integer.valueOf(paramInt1));
        localContentValues.put("deltatavoli", Integer.valueOf(paramInt2));
        return paramSQLiteDatabase.insert("sale", null, localContentValues) > 0L;
    }

    public static void insertSala(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        if (!aggiorna(paramSQLiteDatabase, paramString1, paramString2, paramInt1, paramInt2)) {}
        for (int i = 1;; i = 0)
        {
            if (i != 0) {
                inserisci(paramSQLiteDatabase, paramString1, paramString2, paramInt1, paramInt2);
            }
            return;
        }
    }
}
