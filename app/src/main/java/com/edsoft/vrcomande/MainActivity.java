package com.edsoft.vrcomande;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.dbutility.Operatori;
import com.edsoft.vrcomande.core.dbutility.fresul;
import com.edsoft.vrcomande.core.networkutility.ParserDbPalmare;
import com.edsoft.vrcomande.core.networkutility.TLVParser;
import com.edsoft.vrcomande.core.networkutility.networkresult;

public class MainActivity
  extends Activity
{
  final int CMD_INSERISCI_COMANDA = 0;
  final int CMD_RICHIEDI_CONTO = 1;
  private Button InserisciOrdine;
  private Button RichiestaConto;
  private Button SincronizzaDB;
  String _AlfaOP;
  String _CodiceOP;
  boolean _LoginEffetuato;
  String _androidId;
  String _sn;
  private Button bLogInLogOut;
  private Button bSettings;
  final Context context = this;
  DBHelper databaseHelper = new DBHelper(this);
  private ImageButton exitB;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        switch (paramAnonymousView.getId())
        {
        case 2131296279: 
          paramAnonymousView = new Intent(MainActivity.this, ConfigurazioneActivity.class);
          MainActivity.this.startActivity(paramAnonymousView);
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("bulkInsert:", paramAnonymousView.getMessage());
        return;
      }
      MainActivity.this.AvviaActivityTavoli(0);
      return;
      MainActivity.this.StartDbSincTask();
      return;
      MainActivity.this.finish();
      System.exit(0);
      return;
      if (MainActivity.this._LoginEffetuato)
      {
        MainActivity.this.Logout();
        return;
      }
      MainActivity.this.Login();
      return;
      MainActivity.this.AvviaActivityTavoli(1);
      return;
    }
  };
  ProgressDialog pb;
  
  private void AutoSincronizzaDB()
  {
    for (;;)
    {
      try
      {
        Cursor localCursor = ConnessioneAlServer.getAllServer(this.databaseHelper.getWritableDatabase());
        if (localCursor.moveToNext())
        {
          if (localCursor.getInt(2) == 1)
          {
            i = 1;
            if (i == 0) {
              continue;
            }
            StartDbSincTask();
          }
        }
        else {
          return;
        }
      }
      catch (Exception localException)
      {
        Log.e("bulkInsert:", localException.getMessage());
      }
      int i = 0;
    }
  }
  
  private void AvviaActivityTavoli(int paramInt)
  {
    Intent localIntent = new Intent(this, TavoliActivity.class);
    localIntent.putExtra("cod_op", this._CodiceOP);
    localIntent.putExtra("alfa_op", this._AlfaOP);
    localIntent.putExtra("operazione", paramInt);
    startActivity(localIntent);
  }
  
  private fresul CaricaOp(String paramString1, String paramString2)
  {
    fresul localfresul = new fresul(0, "");
    try
    {
      paramString1 = Operatori.getOperatore(this.databaseHelper.getWritableDatabase(), paramString1, paramString2);
      while (paramString1.moveToNext())
      {
        this._CodiceOP = paramString1.getString(0);
        this._AlfaOP = paramString1.getString(1);
      }
      if (this._CodiceOP.compareTo("") == 0) {
        break label98;
      }
    }
    catch (Exception paramString1)
    {
      localfresul.result = -1;
      localfresul.errMesg = paramString1.getMessage();
      return localfresul;
    }
    if (this._AlfaOP.compareTo("") == 0)
    {
      label98:
      localfresul.result = -1;
      localfresul.errMesg = "Login fallito";
    }
    paramString1.close();
    return localfresul;
  }
  
  private genericresult CollegaAlServer(String paramString, int paramInt1, int paramInt2)
  {
    localgenericresult = new genericresult(0, null, "");
    try
    {
      paramString = new InetSocketAddress(paramString, paramInt1);
      Socket localSocket = new Socket();
      paramInt1 = 0;
      while (paramInt1 < 5)
      {
        localSocket.connect(paramString, paramInt2);
        if (localSocket.isConnected())
        {
          localgenericresult.Dati = localSocket;
          return localgenericresult;
        }
        localgenericresult.result = -1;
        localgenericresult.errMesg = "Errore collegamento con il server";
        paramInt1 += 1;
      }
      return localgenericresult;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      localgenericresult.result = -1;
      localgenericresult.errMesg = paramString.getMessage();
    }
  }
  
  private void InizializzaMaschera()
  {
    int i = 1;
    Cursor localCursor = Operatori.getAllOperatori(this.databaseHelper.getWritableDatabase());
    if (localCursor.getCount() > 0) {
      i = 0;
    }
    localCursor.close();
    SettaVisibilita(4);
    if (i != 0) {
      return;
    }
    SettaLoginButton(0);
  }
  
  private void Login()
  {
    View localView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903055, (ViewGroup)findViewById(2131296332));
    final EditText localEditText1 = (EditText)localView.findViewById(2131296334);
    final EditText localEditText2 = (EditText)localView.findViewById(2131296336);
    final TextView localTextView = (TextView)localView.findViewById(2131296337);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Login operatore");
    localBuilder.setView(localView);
    localBuilder.setIcon(2130837542);
    localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = localEditText1.getText().toString();
        String str = localEditText2.getText().toString();
        paramAnonymousDialogInterface = MainActivity.this.CaricaOp(paramAnonymousDialogInterface, str);
        if (paramAnonymousDialogInterface.result != 0)
        {
          localTextView.setText(paramAnonymousDialogInterface.errMesg);
          Toast.makeText(MainActivity.this, paramAnonymousDialogInterface.errMesg, 0).show();
          return;
        }
        MainActivity.this._LoginEffetuato = true;
        MainActivity.this.SettaLoginButton(0);
        MainActivity.this.SettaVisibilita(0);
        MainActivity.this.AutoSincronizzaDB();
      }
    });
    localBuilder.show();
  }
  
  private void Logout()
  {
    this._LoginEffetuato = false;
    this._CodiceOP = "";
    this._AlfaOP = "";
    SettaVisibilita(4);
    SettaLoginButton(0);
  }
  
  private networkresult ScaricaDB(String paramString, int paramInt)
  {
    networkresult localnetworkresult = new networkresult(0, "", "");
    ArrayList localArrayList = null;
    label432:
    for (;;)
    {
      try
      {
        genericresult localgenericresult = new genericresult(0, null, "");
        int i = 0;
        Object localObject1 = localArrayList;
        Object localObject2 = localgenericresult;
        if (i < 3)
        {
          localgenericresult = CollegaAlServer(paramString, paramInt, 1000);
          if (localgenericresult.result != 0) {
            break label432;
          }
          paramString = (Socket)localgenericresult.Dati;
          paramString.setSoTimeout(45000);
          localObject1 = paramString.getInputStream();
          localObject2 = paramString.getOutputStream();
          localArrayList = new ArrayList();
          localArrayList.add(Byte.valueOf((byte)99));
          byte[] arrayOfByte = TLVParser.intToByteArray(this._androidId.length());
          TLVParser.reverse(arrayOfByte);
          paramInt = 0;
          if (paramInt < arrayOfByte.length)
          {
            localArrayList.add(Byte.valueOf(arrayOfByte[paramInt]));
            paramInt += 1;
            continue;
          }
          arrayOfByte = this._androidId.getBytes("UTF-8");
          paramInt = 0;
          if (paramInt < arrayOfByte.length)
          {
            localArrayList.add(Byte.valueOf(arrayOfByte[paramInt]));
            paramInt += 1;
            continue;
          }
          arrayOfByte = new byte[localArrayList.size()];
          paramInt = 0;
          if (paramInt < localArrayList.size())
          {
            arrayOfByte[paramInt] = ((Byte)localArrayList.get(paramInt)).byteValue();
            paramInt += 1;
            continue;
          }
          ((OutputStream)localObject2).write(arrayOfByte);
          localObject1 = TLVParser.readTLV((InputStream)localObject1, 99);
          if (localObject1 == null)
          {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = "Errore comunicazione con il server";
            localObject2 = localgenericresult;
            localObject1 = paramString;
          }
        }
        else
        {
          if (localObject1 != null) {
            ((Socket)localObject1).close();
          }
          if (((genericresult)localObject2).result == 0) {
            break;
          }
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = "Errore collegamento con il server";
          return localnetworkresult;
        }
        localnetworkresult.Dati = new String((byte[])localObject1);
        i = 1;
        paramInt = i;
        if (localnetworkresult.Dati.startsWith("<?xml version='1.0' encoding='utf-8'?>".replace('\'', '"')))
        {
          paramInt = i;
          if (localnetworkresult.Dati.contains("dbPalmare")) {
            paramInt = 0;
          }
        }
        localObject1 = paramString;
        localObject2 = localgenericresult;
        if (paramInt != 0)
        {
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = localnetworkresult.Dati;
          localObject1 = paramString;
          localObject2 = localgenericresult;
          continue;
          i += 1;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        localnetworkresult.result = -1;
        localnetworkresult.errMesg = paramString.getMessage();
        return localnetworkresult;
      }
    }
    return localnetworkresult;
  }
  
  private void SettaLoginButton(int paramInt)
  {
    if (paramInt == 0)
    {
      if (!this._LoginEffetuato) {
        break label34;
      }
      this.bLogInLogOut.setText(getString(2131099652));
    }
    for (;;)
    {
      this.bLogInLogOut.setVisibility(paramInt);
      return;
      label34:
      this.bLogInLogOut.setText(getString(2131099651));
    }
  }
  
  private void SettaVisibilita(int paramInt)
  {
    SettaLoginButton(paramInt);
    this.InserisciOrdine.setVisibility(paramInt);
    this.RichiestaConto.setVisibility(paramInt);
  }
  
  private void StartDbSincTask()
  {
    try
    {
      this.pb = ProgressDialog.show(this, getString(2131099693), "START...", true, false);
      new SincTask(null).execute(new String[] { "Process started!" });
      return;
    }
    catch (Exception localException)
    {
      Log.e("StartDbSincTask:", localException.getMessage());
    }
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    setContentView(2130903042);
    this._sn = "";
    this._androidId = "";
    if (Build.VERSION.SDK_INT >= 9)
    {
      this._sn = Build.SERIAL;
      this._androidId = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
    }
    this.InserisciOrdine = ((Button)findViewById(2131296281));
    this.InserisciOrdine.setOnClickListener(this.gestore);
    this.SincronizzaDB = ((Button)findViewById(2131296280));
    this.SincronizzaDB.setOnClickListener(this.gestore);
    this.bSettings = ((Button)findViewById(2131296279));
    this.bSettings.setOnClickListener(this.gestore);
    this.exitB = ((ImageButton)findViewById(2131296283));
    this.exitB.setOnClickListener(this.gestore);
    this.bLogInLogOut = ((Button)findViewById(2131296278));
    this.bLogInLogOut.setOnClickListener(this.gestore);
    this.RichiestaConto = ((Button)findViewById(2131296282));
    this.RichiestaConto.setOnClickListener(this.gestore);
    if (!this._LoginEffetuato)
    {
      this._CodiceOP = "";
      this._AlfaOP = "";
      InizializzaMaschera();
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131230721, paramMenu);
    return true;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.databaseHelper.close();
  }
  
  private class SincTask
    extends AsyncTask<String, String, String>
  {
    private SincTask() {}
    
    protected String doInBackground(String... paramVarArgs)
    {
      String str = "";
      int j;
      do
      {
        int i;
        try
        {
          publishProgress(new String[] { "\n" + paramVarArgs[0] });
          SQLiteDatabase localSQLiteDatabase = MainActivity.this.databaseHelper.getWritableDatabase();
          paramVarArgs = "";
          i = 0;
          Object localObject = ConnessioneAlServer.getAllServer(localSQLiteDatabase);
          while (((Cursor)localObject).moveToNext())
          {
            paramVarArgs = ((Cursor)localObject).getString(0);
            i = ((Cursor)localObject).getInt(1);
            continue;
            publishProgress(new String[] { "download dati, attendere..." });
            paramVarArgs = MainActivity.this.ScaricaDB(paramVarArgs, j);
            if (paramVarArgs.result != 0) {
              return paramVarArgs.errMesg;
            }
            publishProgress(new String[] { "elaborazione dei dati, attendere..." });
            localObject = new ParserDbPalmare();
            paramVarArgs = ((ParserDbPalmare)localObject).parseXml(paramVarArgs.Dati);
            if (paramVarArgs.result != 0) {
              return paramVarArgs.errMesg;
            }
            publishProgress(new String[] { "inserimento dai nel db, attendere..." });
            paramVarArgs = new fresul(0, "");
            if (((ParserDbPalmare)localObject).getParsedOperatori().size() > 0) {
              paramVarArgs = MainActivity.this.databaseHelper.BulkImportdbOperatori(localSQLiteDatabase, ((ParserDbPalmare)localObject).getParsedOperatori());
            }
            if (((ParserDbPalmare)localObject).getParsedSale().size() > 0) {
              paramVarArgs = MainActivity.this.databaseHelper.BulkImportdbSale(localSQLiteDatabase, ((ParserDbPalmare)localObject).getParsedSale());
            }
            if (((ParserDbPalmare)localObject).getParsedReparti().size() > 0) {
              paramVarArgs = MainActivity.this.databaseHelper.BulkImportdbArticoli(localSQLiteDatabase, ((ParserDbPalmare)localObject).getParsedReparti(), ((ParserDbPalmare)localObject).getParsedVarianti());
            }
            if (((ParserDbPalmare)localObject).getparsedInfoCdp().size() > 0) {
              paramVarArgs = MainActivity.this.databaseHelper.BulkImportdbCDP(localSQLiteDatabase, ((ParserDbPalmare)localObject).getparsedInfoCdp());
            }
            if (((ParserDbPalmare)localObject).getparsedMsgForCdp().size() > 0) {
              paramVarArgs = MainActivity.this.databaseHelper.BulkImportdbMsgForCDP(localSQLiteDatabase, ((ParserDbPalmare)localObject).getparsedMsgForCdp());
            }
            if (paramVarArgs.result == 0) {
              return str;
            }
            paramVarArgs = paramVarArgs.errMesg;
            return paramVarArgs;
          }
        }
        catch (Exception paramVarArgs)
        {
          paramVarArgs.getMessage();
          return "";
        }
        if (paramVarArgs != "")
        {
          j = i;
          if (i != 0) {}
        }
        else
        {
          paramVarArgs = "192.168.1.2";
          j = 4444;
        }
      } while ((paramVarArgs != "") && (j != 0));
      str = "Errore nella configurazione del server";
      return str;
    }
    
    protected void onPostExecute(String paramString)
    {
      MainActivity.this.pb.dismiss();
      if (paramString != "")
      {
        AlertDialog localAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
        localAlertDialog.setTitle("Errore sincronizzazione DB");
        localAlertDialog.setMessage(paramString);
        localAlertDialog.setButton("OK", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        localAlertDialog.show();
      }
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      MainActivity.this.pb.setMessage(paramVarArgs[0]);
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */