package com.edsoft.vrcomande;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.networkutility.TLVParser;
import com.edsoft.vrcomande.core.networkutility.networkresult;

public class ConfigurazioneActivity
  extends Activity
{
  CheckedTextView AttivaAutoUpdate;
  boolean _AutoUpdateDB;
  int _PortaServer;
  String _androidId;
  String _ipServer;
  boolean _modifiche;
  String _sn;
  String _titolo;
  Button bAnnullaConfigurazione;
  Button bRegistraTerminale;
  Button bSalvaConfigurazione;
  final Context context = this;
  DBHelper databaseHelper = new DBHelper(this);
  SQLiteDatabase db;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        switch (paramAnonymousView.getId())
        {
        case 2131296276: 
          ConfigurazioneActivity.this.SalvaConfigurazione();
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        ConfigurazioneActivity.this.VisualizzaErrore("runtime error", paramAnonymousView.getMessage());
        Log.e("ArticoliActivity - gestore:", paramAnonymousView.getMessage());
        return;
      }
      ConfigurazioneActivity.this.ChiudiActivity();
      return;
      ConfigurazioneActivity.this.RegistraTerminale();
      return;
    }
  };
  EditText ipAddress;
  TextView label_idAndroid;
  ProgressDialog pb;
  EditText tcp_port;
  
  private void AbortActivity()
  {
    finish();
  }
  
  private void CaricaConfigurazione()
  {
    for (;;)
    {
      try
      {
        this._titolo = ((String)getTitle());
        if (Build.VERSION.SDK_INT >= 9)
        {
          this._sn = Build.SERIAL;
          this._androidId = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
        }
        this.label_idAndroid.setText("ID terminale:\r\n" + this._androidId);
        Cursor localCursor = ConnessioneAlServer.getAllServer(this.db);
        if (!localCursor.moveToNext()) {
          break;
        }
        this._ipServer = localCursor.getString(0);
        this._PortaServer = localCursor.getInt(1);
        if (localCursor.getInt(2) == 1)
        {
          this._AutoUpdateDB = true;
          this.AttivaAutoUpdate.setChecked(this._AutoUpdateDB);
        }
        else
        {
          this._AutoUpdateDB = false;
        }
      }
      catch (Exception localException)
      {
        Log.e("CaricaReparti:", localException.toString());
        return;
      }
    }
    if (this._ipServer == "")
    {
      this._ipServer = "192.168.1.2";
      DatiModificati(true);
    }
    if (this._PortaServer == 0)
    {
      this._PortaServer = 4444;
      DatiModificati(true);
    }
    localException.close();
  }
  
  private void ChiudiActivity()
  {
    if (!this._modifiche)
    {
      AbortActivity();
      return;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Conferma di chiusura...");
    localBuilder.setMessage("Sei certo di voler uscire dalla configurazione? Tutte le modifiche andranno perse.");
    localBuilder.setIcon(2130837541);
    localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ConfigurazioneActivity.this.AbortActivity();
      }
    });
    localBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.show();
  }
  
  private genericresult CollegaAlServer(String paramString, int paramInt1, int paramInt2)
  {
    localgenericresult = new com.edsoft.vrcomande.genericresult(0, null, "");
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
  
  private void DatiModificati(boolean paramBoolean)
  {
    this._modifiche = paramBoolean;
    if (!paramBoolean)
    {
      setTitle(this._titolo);
      return;
    }
    setTitle("*" + this._titolo);
  }
  
  private void InizializzaIPAndPorta()
  {
    this.ipAddress.setText(this._ipServer);
    this.tcp_port.setText(Integer.toString(this._PortaServer));
    InputFilter local5 = new InputFilter()
    {
      public CharSequence filter(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, Spanned paramAnonymousSpanned, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        if (paramAnonymousInt2 < paramAnonymousInt1) {
          ConfigurazioneActivity.this.DatiModificati(true);
        }
        for (;;)
        {
          return null;
          paramAnonymousSpanned = paramAnonymousSpanned.toString();
          paramAnonymousCharSequence = paramAnonymousSpanned.substring(0, paramAnonymousInt3) + paramAnonymousCharSequence.subSequence(paramAnonymousInt1, paramAnonymousInt2) + paramAnonymousSpanned.substring(paramAnonymousInt4);
          if (!paramAnonymousCharSequence.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
            return "";
          }
          paramAnonymousCharSequence = paramAnonymousCharSequence.split("\\.");
          paramAnonymousInt1 = 0;
          while (paramAnonymousInt1 < paramAnonymousCharSequence.length)
          {
            if (Integer.valueOf(paramAnonymousCharSequence[paramAnonymousInt1]).intValue() > 255) {
              return "";
            }
            ConfigurazioneActivity.this.DatiModificati(true);
            paramAnonymousInt1 += 1;
          }
        }
      }
    };
    this.ipAddress.setFilters(new InputFilter[] { local5 });
    this.tcp_port.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        ConfigurazioneActivity.this.DatiModificati(true);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
  }
  
  private void RegistraTerminale()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Conferma di esecuzione comando...");
    localBuilder.setMessage("Sei certo di voler registrare il terminale?");
    localBuilder.setIcon(2130837541);
    localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = ConfigurazioneActivity.this.getString(2131099687);
        ConfigurazioneActivity.this.pb = ProgressDialog.show(ConfigurazioneActivity.this, paramAnonymousDialogInterface, "START...", true, false);
        new ConfigurazioneActivity.SincTask(ConfigurazioneActivity.this, null).execute(new String[] { ConfigurazioneActivity.this._androidId });
      }
    });
    localBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.show();
  }
  
  private void SalvaConfigurazione()
  {
    String str1 = this.ipAddress.getText().toString().trim();
    String str2 = this.tcp_port.getText().toString().trim();
    if (str1.compareTo("") == 0)
    {
      VisualizzaErrore("Errore configurazione", "è necessario inserire un indirizzo ip valido.");
      return;
    }
    if (str2.compareTo("") == 0)
    {
      VisualizzaErrore("Errore configurazione", "è necessario inserire un valore valido per la porta TCP.");
      return;
    }
    this._ipServer = str1;
    this._PortaServer = Integer.parseInt(str2);
    ConnessioneAlServer.insertCfgServer(this.db, this._ipServer, this._PortaServer, this._AutoUpdateDB);
    DatiModificati(false);
  }
  
  private networkresult SendCmd(String paramString1, String paramString2, int paramInt)
  {
    networkresult localnetworkresult = new networkresult(0, "", "");
    if (paramString1 == "") {}
    try
    {
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = "Nessun ID terminale rilevato.";
      return localnetworkresult;
    }
    catch (Exception paramString1)
    {
      ArrayList localArrayList;
      paramString1.printStackTrace();
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = paramString1.getMessage();
      return localnetworkresult;
    }
    localArrayList = null;
    genericresult localgenericresult = new genericresult(0, null, "");
    int i = 0;
    for (;;)
    {
      Object localObject1 = localArrayList;
      Object localObject2 = localgenericresult;
      if (i < 3)
      {
        localgenericresult = CollegaAlServer(paramString2, paramInt, 1000);
        if (localgenericresult.result != 0) {
          break label413;
        }
        paramString2 = (Socket)localgenericresult.Dati;
        paramString2.setSoTimeout(5000);
        localObject1 = paramString2.getInputStream();
        localObject2 = paramString2.getOutputStream();
        localArrayList = new ArrayList();
        localArrayList.add(Byte.valueOf((byte)95));
        byte[] arrayOfByte = TLVParser.intToByteArray(paramString1.length());
        TLVParser.reverse(arrayOfByte);
        paramInt = 0;
        while (paramInt < arrayOfByte.length)
        {
          localArrayList.add(Byte.valueOf(arrayOfByte[paramInt]));
          paramInt += 1;
        }
        paramString1 = paramString1.getBytes("UTF-8");
        paramInt = 0;
        while (paramInt < paramString1.length)
        {
          localArrayList.add(Byte.valueOf(paramString1[paramInt]));
          paramInt += 1;
        }
        paramString1 = new byte[localArrayList.size()];
        paramInt = 0;
        while (paramInt < localArrayList.size())
        {
          paramString1[paramInt] = ((Byte)localArrayList.get(paramInt)).byteValue();
          paramInt += 1;
        }
        ((OutputStream)localObject2).write(paramString1);
        paramString1 = TLVParser.readTLV((InputStream)localObject1, 95);
        if (paramString1 != null) {
          break label353;
        }
        localnetworkresult.result = -1;
        localnetworkresult.errMesg = "Errore comunicazione con il server";
        localObject2 = localgenericresult;
        localObject1 = paramString2;
      }
      for (;;)
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
        label353:
        localnetworkresult.Dati = new String(paramString1);
        localObject1 = paramString2;
        localObject2 = localgenericresult;
        if (localnetworkresult.Dati.compareTo("OK") != 0)
        {
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = localnetworkresult.Dati;
          localObject1 = paramString2;
          localObject2 = localgenericresult;
        }
      }
      label413:
      i += 1;
    }
    return localnetworkresult;
  }
  
  private void VisualizzaErrore(String paramString1, String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.setIcon(2130837535);
    localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.show();
  }
  
  private void VisualizzaOK(String paramString1, String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.setIcon(2130837538);
    localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.show();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getWindow().setFlags(1024, 1024);
    setContentView(2130903041);
    this.db = this.databaseHelper.getWritableDatabase();
    this._modifiche = false;
    this._ipServer = "";
    this._PortaServer = 0;
    this._sn = "";
    this._androidId = "";
    this.bSalvaConfigurazione = ((Button)findViewById(2131296276));
    this.bSalvaConfigurazione.setOnClickListener(this.gestore);
    this.bAnnullaConfigurazione = ((Button)findViewById(2131296277));
    this.bAnnullaConfigurazione.setOnClickListener(this.gestore);
    this.bRegistraTerminale = ((Button)findViewById(2131296275));
    this.bRegistraTerminale.setOnClickListener(this.gestore);
    this.AttivaAutoUpdate = ((CheckedTextView)findViewById(2131296272));
    this.AttivaAutoUpdate.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((CheckedTextView)paramAnonymousView).toggle();
        ConfigurazioneActivity.this.DatiModificati(true);
        ConfigurazioneActivity.this._AutoUpdateDB = ((CheckedTextView)paramAnonymousView).isChecked();
      }
    });
    this.label_idAndroid = ((TextView)findViewById(2131296274));
    this.ipAddress = ((EditText)findViewById(2131296269));
    this.ipAddress.setImeOptions(6);
    this.tcp_port = ((EditText)findViewById(2131296271));
    this.tcp_port.setImeOptions(6);
    CaricaConfigurazione();
    InizializzaIPAndPorta();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.db.close();
    this.databaseHelper.close();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      ChiudiActivity();
      return false;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  private class SincTask
    extends AsyncTask<String, String, genericresult>
  {
    private SincTask() {}
    
    protected genericresult doInBackground(String... paramVarArgs)
    {
      genericresult localgenericresult = new genericresult(0, null, "");
      for (;;)
      {
        int i;
        int j;
        try
        {
          String str = paramVarArgs[0].replace('[', ' ').replace(']', ' ').trim();
          paramVarArgs = "";
          i = 0;
          Cursor localCursor = ConnessioneAlServer.getAllServer(ConfigurazioneActivity.this.db);
          if (localCursor.moveToNext())
          {
            paramVarArgs = localCursor.getString(0);
            i = localCursor.getInt(1);
            continue;
            publishProgress(new String[] { "Comunicazione con il server..." });
            paramVarArgs = ConfigurazioneActivity.this.SendCmd(str, paramVarArgs, j);
            localgenericresult.result = paramVarArgs.result;
            if (paramVarArgs.result != 0) {
              localgenericresult.errMesg = paramVarArgs.errMesg;
            }
            return localgenericresult;
          }
        }
        catch (Exception paramVarArgs)
        {
          paramVarArgs = paramVarArgs;
          Log.e("StartDbSincTask:", paramVarArgs.getMessage());
          return localgenericresult;
        }
        finally {}
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
      }
    }
    
    protected void onPostExecute(genericresult paramgenericresult)
    {
      try
      {
        if (paramgenericresult.result != 0) {
          ConfigurazioneActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
        }
        for (;;)
        {
          ConfigurazioneActivity.this.pb.dismiss();
          return;
          if (paramgenericresult.errMesg.compareTo("") != 0) {
            ConfigurazioneActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
          } else {
            ConfigurazioneActivity.this.VisualizzaOK("OK", "il terminale è stato correttamente registrato.");
          }
        }
        return;
      }
      catch (Exception paramgenericresult) {}
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      try
      {
        ConfigurazioneActivity.this.pb.setMessage(paramVarArgs[0]);
        return;
      }
      catch (Exception paramVarArgs)
      {
        Log.e("onProgressUpdate:", paramVarArgs.getMessage());
      }
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\ConfigurazioneActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */