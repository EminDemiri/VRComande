package com.edsoft.vrcomande.core.dbutility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.io.PrintStream;
import java.util.ArrayList;
import java.lang.String;

import com.edsoft.vrcomande.core.networkutility.ArticoliXML;
import com.edsoft.vrcomande.core.networkutility.InfoCdpXML;
import com.edsoft.vrcomande.core.networkutility.MsgForCdpXML;
import com.edsoft.vrcomande.core.networkutility.OperatoriXML;
import com.edsoft.vrcomande.core.networkutility.RepartiXML;
import com.edsoft.vrcomande.core.networkutility.SaleXML;
import com.edsoft.vrcomande.core.networkutility.VariantiXML;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class DBHelper
        extends SQLiteOpenHelper
{
    private static final String CREATE_TABLE_ARTICOLI = "create table articoli (codice TEXT primary key, descrizione TEXT,prezzo NUMERIC,iva NUMERIC,reparto TEXT NOT NULL, posizione INTEGER NOT NULL CONSTRAINT reparto REFERENCES reparti(codice) ON DELETE CASCADE);";
    private static final String CREATE_TABLE_INFOCDP = "create table CentriDiProduzione (codice TEXT primary key, alfacdp TEXT NOT NULL);";
    private static final String CREATE_TABLE_LINK_VAR_ART = "create table LinkVariantiArticoli (id INTEGER PRIMARY KEY, codice_variante TEXT NOT NULL CONSTRAINT fk_variante_codice REFERENCES variantiarticoli(codice) ON  DELETE CASCADE,codice_articolo TEXT NOT NULL CONSTRAINT fk_articolo_codice REFERENCES articoli(codice) ON  DELETE CASCADE);";
    private static final String CREATE_TABLE_MSGFORCDP = "create table MsgForCDP (codice TEXT primary key, testo TEXT NOT NULL, cdp TEXT NOT NULL);";
    private static final String CREATE_TABLE_OPERATORI = "create table operatori (codice TEXT primary key, alfaoperatore TEXT NOT NULL,login TEXT NOT NULL,password TEXT NOT NULL );";
    private static final String CREATE_TABLE_REPARTI = "create table reparti (codice TEXT primary key, descrizione TEXT NOT NULL, posizione INTEGER NOT NULL );";
    private static final String CREATE_TABLE_SALE = "create table sale (codice TEXT primary key, alfasala TEXT NOT NULL,numerotavoli INTEGER NOT NULL,deltatavoli INTEGER NOT NULL );";
    private static final String CREATE_TABLE_SERVER = "create table connessioneserver (ipserver TEXT primary key, portaserver INTEGER NOT NULL, autoupdatedb INTEGER NOT NULL );";
    private static final String CREATE_TABLE_VARIANTI = "create table variantiarticoli (codice TEXT primary key, alfavariante TEXT NOT NULL, prezzovariante NUMERIC, variantepertutti INTEGER NOT NULL);";
    private static final String CREATE_TRIGGER_fkd_LinkVariantiArticoli_codice_articolo_articoli_codice = "CREATE TRIGGER fkd_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE DELETE ON articoli FOR EACH ROW BEGIN   SELECT RAISE(ROLLBACK, 'delete on table articoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_articolo_articoli_codice')   WHERE (SELECT codice_articolo FROM LinkVariantiArticoli WHERE codice_articolo = OLD.codice) IS NOT NULL; END;";
    private static final String CREATE_TRIGGER_fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice = "CREATE TRIGGER fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE DELETE ON variantiarticoli FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'delete on table variantiarticoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice_variante FROM LinkVariantiArticoli WHERE codice_variante = OLD.codice) IS NOT NULL; END;";
    private static final String CREATE_TRIGGER_fkdc = "CREATE TRIGGER fkdc_articoli_reparto_reparti_codice BEFORE DELETE ON reparti FOR EACH ROW BEGIN     DELETE FROM articoli WHERE articoli.reparto = OLD.codice; END;";
    private static final String CREATE_TRIGGER_fki = "CREATE TRIGGER fki_articoli_reparto_reparti_codice BEFORE INSERT ON [articoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table articoli violates foreign key constraint fki_articoli_reparto_reparti_codice')   WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL;  END;";
    private static final String CREATE_TRIGGER_fki_LinkVariantiArticoli_codice_articolo_articoli_codice = "CREATE TRIGGER fki_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_articolo_articoli_codice')    WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;";
    private static final String CREATE_TRIGGER_fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice = "CREATE TRIGGER fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;";
    private static final String CREATE_TRIGGER_fku = "CREATE TRIGGER fku_articoli_reparto_reparti_codice BEFORE UPDATE ON [articoli]  FOR EACH ROW BEGIN     SELECT RAISE(ROLLBACK, 'update on table articoli violates foreign key constraint fku_articoli_reparto_reparti_codice')       WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL; END;";
    private static final String CREATE_TRIGGER_fku_LinkVariantiArticoli_codice_articolo_articoli_codice = "CREATE TRIGGER fku_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_articolo_articoli_codice') WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;";
    private static final String CREATE_TRIGGER_fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice = "CREATE TRIGGER fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;";
    public static final String NOME_DB = "dbVRRisto";
    public static final int VERSIONE_DB = 1;

    public DBHelper(Context paramContext)
    {
        super(paramContext, "dbVRRisto", null, 1);
    }

    private int boolToInt(boolean paramBoolean)
    {
        if (paramBoolean) {
            return 1;
        }
        return 0;
    }

    public fresul BulkImportdbArticoli(SQLiteDatabase paramSQLiteDatabase, ArrayList<RepartiXML> paramArrayList, ArrayList<VariantiXML> paramArrayList1)
    {
        fresul localfresul = new fresul(0, "");
        paramSQLiteDatabase.beginTransaction();
        for (;;)
        {
            int i;
            int j;
            try
            {
                paramSQLiteDatabase.execSQL("DELETE FROM LinkVariantiArticoli");
                paramSQLiteDatabase.execSQL("DELETE FROM variantiarticoli");
                SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO variantiarticoli (codice, alfavariante, prezzovariante, variantepertutti) VALUES (?, ?, ?, ?)");
                i = 0;
                Object localObject1;
                Object localObject2;
                if (i < paramArrayList1.size())
                {
                    localObject1 = ((VariantiXML)paramArrayList1.get(i)).getCodVariante();
                    localObject2 = ((VariantiXML)paramArrayList1.get(i)).getAlfaVariante();
                    double d = ((VariantiXML)paramArrayList1.get(i)).getPrezzoVariante();
                    boolean bool = ((VariantiXML)paramArrayList1.get(i)).getVariantePerTutti();
                    localSQLiteStatement.bindString(1, (String)localObject1);
                    localSQLiteStatement.bindString(2, (String)localObject2);
                    localSQLiteStatement.bindDouble(3, d);
                    localSQLiteStatement.bindLong(4, boolToInt(bool));
                    localSQLiteStatement.execute();
                    localSQLiteStatement.clearBindings();
                    i += 1;
                    continue;
                }
                paramSQLiteDatabase.execSQL("DELETE FROM reparti");
                paramSQLiteDatabase.execSQL("DELETE FROM articoli");
                paramArrayList1 = paramSQLiteDatabase.compileStatement("INSERT INTO reparti (codice, descrizione, posizione) VALUES (?, ?, ?)");
                localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO articoli (codice, descrizione, reparto, prezzo, iva, posizione) VALUES (?, ?, ?, ?, ?, ?)");
                Object localObject1 = paramSQLiteDatabase.compileStatement("INSERT INTO LinkVariantiArticoli (codice_articolo, codice_variante) VALUES (?, ?)");
                i = 0;
                if (i < paramArrayList.size())
                {
                    paramArrayList1.bindString(1, ((RepartiXML)paramArrayList.get(i)).getCodRep());
                    paramArrayList1.bindString(2, ((RepartiXML)paramArrayList.get(i)).getAlfaRep());
                    paramArrayList1.bindString(3, Integer.toString(((RepartiXML)paramArrayList.get(i)).getPosizione()));
                    paramArrayList1.execute();
                    paramArrayList1.clearBindings();
                    j = 0;
                    if (j >= ((RepartiXML)paramArrayList.get(i)).getElencoArt().size()) {
                        break label547;
                    }
                    localObject2 = (ArticoliXML)((RepartiXML)paramArrayList.get(i)).getElencoArt().get(j);
                    localSQLiteStatement.bindString(1, ((ArticoliXML)localObject2).getCodArt());
                    localSQLiteStatement.bindString(2, ((ArticoliXML)localObject2).getAlfaArt());
                    localSQLiteStatement.bindString(3, ((RepartiXML)paramArrayList.get(i)).getCodRep());
                    localSQLiteStatement.bindDouble(4, ((ArticoliXML)localObject2).getPrezzoArt());
                    localSQLiteStatement.bindDouble(5, ((ArticoliXML)localObject2).getIvaArt());
                    localSQLiteStatement.bindString(6, Integer.toString(((ArticoliXML)localObject2).getPosizione()));
                    localSQLiteStatement.execute();
                    localSQLiteStatement.clearBindings();
                    int k = 0;
                    if (k < ((ArticoliXML)localObject2).getElencoVarianti().size())
                    {
                        String str1 = ((ArticoliXML)localObject2).getCodArt();
                        String str2 = (String)((ArticoliXML)localObject2).getElencoVarianti().get(k);
                        ((SQLiteStatement)localObject1).bindString(1, str1);
                        ((SQLiteStatement)localObject1).bindString(2, str2);
                        ((SQLiteStatement)localObject1).execute();
                        ((SQLiteStatement)localObject1).clearBindings();
                        k += 1;
                        continue;
                    }
                }
                else
                {
                    paramSQLiteDatabase.setTransactionSuccessful();
                    return localfresul;
                }
            }
            catch (Exception paramArrayList)
            {
                if (paramArrayList.getMessage() == null)
                {
                    paramArrayList = "bulkInsert failed";
                    paramArrayList1 = new fresul(-1, paramArrayList);
                }
                try
                {
                    Log.e("bulkInsert:", paramArrayList);
                    return paramArrayList1;
                }
                finally {}
                paramArrayList = paramArrayList.getMessage();
                continue;
            }
            finally
            {
                paramSQLiteDatabase.endTransaction();
            }
            j += 1;
            continue;
            label547:
            i += 1;
        }
    }

    public fresul BulkImportdbCDP(SQLiteDatabase paramSQLiteDatabase, ArrayList<InfoCdpXML> paramArrayList)
    {
        fresul localfresul = new fresul(0, "");
        paramSQLiteDatabase.beginTransaction();
        try
        {
            paramSQLiteDatabase.execSQL("DELETE FROM CentriDiProduzione");
            SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO CentriDiProduzione (codice, alfacdp) VALUES (?, ?)");
            int i = 0;
            while (i < paramArrayList.size())
            {
                localSQLiteStatement.bindString(1, ((InfoCdpXML)paramArrayList.get(i)).getCodCDP());
                localSQLiteStatement.bindString(2, ((InfoCdpXML)paramArrayList.get(i)).getAlfaCDP());
                localSQLiteStatement.execute();
                localSQLiteStatement.clearBindings();
                i += 1;
            }
            paramSQLiteDatabase.setTransactionSuccessful();
            paramSQLiteDatabase.endTransaction();
            return localfresul;
        }
        catch (Exception paramArrayList)
        {
            paramArrayList = paramArrayList;
            if (paramArrayList.getMessage() == null) {}
            for (paramArrayList = "bulkInsert failed";; paramArrayList = paramArrayList.getMessage())
            {
                localfresul = new fresul(-1, paramArrayList);
                try
                {
                    Log.e("bulkInsert:", paramArrayList);
                    paramSQLiteDatabase.endTransaction();
                    return localfresul;
                }
                finally {}
            }
        }
        finally {}
        paramSQLiteDatabase.endTransaction();
        throw paramArrayList;
    }

    public fresul BulkImportdbMsgForCDP(SQLiteDatabase paramSQLiteDatabase, ArrayList<MsgForCdpXML> paramArrayList)
    {
        fresul localfresul = new fresul(0, "");
        paramSQLiteDatabase.beginTransaction();
        try
        {
            paramSQLiteDatabase.execSQL("DELETE FROM MsgForCDP");
            SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO MsgForCDP (codice, testo, cdp) VALUES (?, ?, ?)");
            int i = 0;
            while (i < paramArrayList.size())
            {
                localSQLiteStatement.bindString(1, ((MsgForCdpXML)paramArrayList.get(i)).getCodMSG());
                localSQLiteStatement.bindString(2, ((MsgForCdpXML)paramArrayList.get(i)).getTestoMSG());
                localSQLiteStatement.bindString(3, ((MsgForCdpXML)paramArrayList.get(i)).getCodCDP());
                localSQLiteStatement.execute();
                localSQLiteStatement.clearBindings();
                i += 1;
            }
            paramSQLiteDatabase.setTransactionSuccessful();
            paramSQLiteDatabase.endTransaction();
            return localfresul;
        }
        catch (Exception paramArrayList)
        {
            paramArrayList = paramArrayList;
            if (paramArrayList.getMessage() == null) {}
            for (paramArrayList = "bulkInsert failed";; paramArrayList = paramArrayList.getMessage())
            {
                localfresul = new fresul(-1, paramArrayList);
                try
                {
                    Log.e("bulkInsert:", paramArrayList);
                    paramSQLiteDatabase.endTransaction();
                    return localfresul;
                }
                finally {}
            }
        }
        finally {}
        paramSQLiteDatabase.endTransaction();
        throw paramArrayList;
    }

    public fresul BulkImportdbOperatori(SQLiteDatabase paramSQLiteDatabase, ArrayList<OperatoriXML> paramArrayList)
    {
        fresul localfresul = new fresul(0, "");
        paramSQLiteDatabase.beginTransaction();
        try
        {
            paramSQLiteDatabase.execSQL("DELETE FROM operatori");
            SQLiteStatement localSQLiteStatement = paramSQLiteDatabase.compileStatement("INSERT INTO operatori (codice, alfaoperatore, login, password) VALUES (?, ?, ?, ?)");
            int i = 0;
            while (i < paramArrayList.size())
            {
                localSQLiteStatement.bindString(1, ((OperatoriXML)paramArrayList.get(i)).getCodOp());
                localSQLiteStatement.bindString(2, ((OperatoriXML)paramArrayList.get(i)).getAlfaOp());
                localSQLiteStatement.bindString(3, ((OperatoriXML)paramArrayList.get(i)).getLoginOp());
                localSQLiteStatement.bindString(4, ((OperatoriXML)paramArrayList.get(i)).getPwdOp());
                localSQLiteStatement.execute();
                localSQLiteStatement.clearBindings();
                i += 1;
            }
            paramSQLiteDatabase.setTransactionSuccessful();
            paramSQLiteDatabase.endTransaction();
            return localfresul;
        }
        catch (Exception paramArrayList)
        {
            paramArrayList = paramArrayList;
            if (paramArrayList.getMessage() == null) {}
            for (paramArrayList = "bulkInsert failed";; paramArrayList = paramArrayList.getMessage())
            {
                localfresul = new fresul(-1, paramArrayList);
                try
                {
                    Log.e("bulkInsert:", paramArrayList);
                    paramSQLiteDatabase.endTransaction();
                    return localfresul;
                }
                finally {}
            }
        }
        finally {}
        paramSQLiteDatabase.endTransaction();
        throw paramArrayList;
    }

    public fresul BulkImportdbSale(SQLiteDatabase paramSQLiteDatabase, ArrayList<SaleXML> paramArrayList)
    {
        fresul localfresul = new fresul(0, "");
        paramSQLiteDatabase.beginTransaction();
        try
        {
            paramSQLiteDatabase.execSQL("DELETE FROM sale");
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
            paramSQLiteDatabase.endTransaction();
            return localfresul;
        }
        catch (Exception paramArrayList)
        {
            paramArrayList = paramArrayList;
            if (paramArrayList.getMessage() == null) {}
            for (paramArrayList = "bulkInsert failed";; paramArrayList = paramArrayList.getMessage())
            {
                localfresul = new fresul(-1, paramArrayList);
                try
                {
                    Log.e("bulkInsert:", paramArrayList);
                    paramSQLiteDatabase.endTransaction();
                    return localfresul;
                }
                finally {}
            }
        }
        finally {}
        paramSQLiteDatabase.endTransaction();
        throw paramArrayList;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        System.err.println("Dentro create tables");
        paramSQLiteDatabase.execSQL("create table operatori (codice TEXT primary key, alfaoperatore TEXT NOT NULL,login TEXT NOT NULL,password TEXT NOT NULL );");
        paramSQLiteDatabase.execSQL("create table connessioneserver (ipserver TEXT primary key, portaserver INTEGER NOT NULL, autoupdatedb INTEGER NOT NULL );");
        paramSQLiteDatabase.execSQL("create table sale (codice TEXT primary key, alfasala TEXT NOT NULL,numerotavoli INTEGER NOT NULL,deltatavoli INTEGER NOT NULL );");
        paramSQLiteDatabase.execSQL("create table reparti (codice TEXT primary key, descrizione TEXT NOT NULL, posizione INTEGER NOT NULL );");
        paramSQLiteDatabase.execSQL("create table articoli (codice TEXT primary key, descrizione TEXT,prezzo NUMERIC,iva NUMERIC,reparto TEXT NOT NULL, posizione INTEGER NOT NULL CONSTRAINT reparto REFERENCES reparti(codice) ON DELETE CASCADE);");
        paramSQLiteDatabase.execSQL("create table variantiarticoli (codice TEXT primary key, alfavariante TEXT NOT NULL, prezzovariante NUMERIC, variantepertutti INTEGER NOT NULL);");
        paramSQLiteDatabase.execSQL("create table LinkVariantiArticoli (id INTEGER PRIMARY KEY, codice_variante TEXT NOT NULL CONSTRAINT fk_variante_codice REFERENCES variantiarticoli(codice) ON  DELETE CASCADE,codice_articolo TEXT NOT NULL CONSTRAINT fk_articolo_codice REFERENCES articoli(codice) ON  DELETE CASCADE);");
        paramSQLiteDatabase.execSQL("create table CentriDiProduzione (codice TEXT primary key, alfacdp TEXT NOT NULL);");
        paramSQLiteDatabase.execSQL("create table MsgForCDP (codice TEXT primary key, testo TEXT NOT NULL, cdp TEXT NOT NULL);");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fki_articoli_reparto_reparti_codice BEFORE INSERT ON [articoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table articoli violates foreign key constraint fki_articoli_reparto_reparti_codice')   WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL;  END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fku_articoli_reparto_reparti_codice BEFORE UPDATE ON [articoli]  FOR EACH ROW BEGIN     SELECT RAISE(ROLLBACK, 'update on table articoli violates foreign key constraint fku_articoli_reparto_reparti_codice')       WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fkdc_articoli_reparto_reparti_codice BEFORE DELETE ON reparti FOR EACH ROW BEGIN     DELETE FROM articoli WHERE articoli.reparto = OLD.codice; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE DELETE ON variantiarticoli FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'delete on table variantiarticoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice_variante FROM LinkVariantiArticoli WHERE codice_variante = OLD.codice) IS NOT NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fki_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_articolo_articoli_codice')    WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fku_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_articolo_articoli_codice') WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;");
        paramSQLiteDatabase.execSQL("CREATE TRIGGER fkd_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE DELETE ON articoli FOR EACH ROW BEGIN   SELECT RAISE(ROLLBACK, 'delete on table articoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_articolo_articoli_codice')   WHERE (SELECT codice_articolo FROM LinkVariantiArticoli WHERE codice_articolo = OLD.codice) IS NOT NULL; END;");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        System.err.println("Dentro update tables");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS operatori");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS connessioneserver");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS sale");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articoli");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS reparti");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS LinkVariantiArticoli");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS variantiarticoli");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS CentriDiProduzione");
        onCreate(paramSQLiteDatabase);
    }
}

