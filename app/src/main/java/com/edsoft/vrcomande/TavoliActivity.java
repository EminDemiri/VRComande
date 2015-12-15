package com.edsoft.vrcomande;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import com.edsoft.vrcomande.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.dbutility.SaleLocale;
import com.edsoft.vrcomande.core.networkutility.ParserSituazioneTavoli;
import com.edsoft.vrcomande.core.networkutility.TLVParser;
import com.edsoft.vrcomande.core.networkutility.TavoloXML;
import com.edsoft.vrcomande.core.networkutility.networkresult;
import com.edsoft.vrcomande.core.scontrin.richiestaconto;
import com.edsoft.vrcomande.core.scontrin.streamoutresult;

public class TavoliActivity
  extends Activity
{
  final int CMD_INSERISCI_COMANDA = 0;
  final int CMD_RICHIEDI_CONTO = 1;
  String _AlfaOP;
  String _CodiceOP;
  int _operazione;
  Button bPrevTavoli;
  ArrayList<Button> bSale;
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
        case 2131296309: 
          TavoliActivity.this.finish();
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("TavoliActivity - gestore:", paramAnonymousView.getMessage());
        return;
      }
    }
  };
  View.OnClickListener gestoreConto = new View.OnClickListener()
  {
    public void onClick(final View paramAnonymousView)
    {
      try
      {
        paramAnonymousView = ((String)((Button)paramAnonymousView).getTag()).split("_");
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(TavoliActivity.this);
        localBuilder.setTitle("Conferma di invio...");
        localBuilder.setMessage("Sei certo di voler inviare una richiesta di conto per il tavolo " + paramAnonymousView[1] + "?");
        localBuilder.setIcon(2130837541);
        localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface = new richiestaconto();
            paramAnonymous2DialogInterface.setSala(paramAnonymousView[0]);
            paramAnonymous2DialogInterface.setTavolo(paramAnonymousView[1]);
            paramAnonymous2DialogInterface = paramAnonymous2DialogInterface.ToXml();
            if (paramAnonymous2DialogInterface.result == 0)
            {
              paramAnonymous2DialogInterface = (ByteArrayOutputStream)paramAnonymous2DialogInterface.Dati;
              TavoliActivity.this.pb = ProgressDialog.show(TavoliActivity.this, "Richista conto", "START...", true, false);
              new TavoliActivity.RichiestaContoSincTask(TavoliActivity.this, null).execute(new String[] { paramAnonymous2DialogInterface.toString().trim() });
            }
          }
        });
        localBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.cancel();
          }
        });
        localBuilder.show();
        return;
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("TavoliActivity - gestoreConto:", paramAnonymousView.getMessage());
      }
    }
  };
  View.OnClickListener gestoreSale = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        paramAnonymousView = ((Button)paramAnonymousView).getText().toString();
        TavoliActivity.this.tl.removeAllViews();
        TavoliActivity.this.salaScelta.setText(paramAnonymousView);
        TavoliActivity.this.CaricaTavoli(paramAnonymousView);
        return;
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("TavoliActivity - gestoreSale:", paramAnonymousView.getMessage());
      }
    }
  };
  View.OnClickListener gestoreTavoli = new View.OnClickListener()
  {
    public void onClick(final View paramAnonymousView)
    {
      try
      {
        Object localObject = ((String)((Button)paramAnonymousView).getTag()).split("_");
        final int j = Integer.parseInt(localObject[0]);
        final int k = Integer.parseInt(localObject[1]);
        paramAnonymousView = "";
        final String str = "";
        final int i = 0;
        if (localObject.length >= 5)
        {
          paramAnonymousView = localObject[2];
          str = localObject[3];
          i = Integer.parseInt(localObject[4]);
        }
        if ((paramAnonymousView != "") && (str != ""))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("E' arrivato il cliente della prenotazione?\r\n");
          ((StringBuilder)localObject).append("Nominativo: " + paramAnonymousView + "\r\n");
          ((StringBuilder)localObject).append("orario: " + str + "\r\n");
          if (i != 0) {
            ((StringBuilder)localObject).append("coperti: " + Integer.toString(i) + "\r\n");
          }
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(TavoliActivity.this);
          localBuilder.setTitle("Conferma arrivo cliente...");
          localBuilder.setMessage(((StringBuilder)localObject).toString());
          localBuilder.setIcon(2130837541);
          localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              paramAnonymous2DialogInterface = new Intent(TavoliActivity.this, OrdineActivity.class);
              paramAnonymous2DialogInterface.putExtra("numero_sala", j);
              paramAnonymous2DialogInterface.putExtra("numero_tavolo", k);
              paramAnonymous2DialogInterface.putExtra("cod_op", TavoliActivity.this._CodiceOP);
              paramAnonymous2DialogInterface.putExtra("alfa_op", TavoliActivity.this._AlfaOP);
              paramAnonymous2DialogInterface.putExtra("nominativo_preno", paramAnonymousView);
              paramAnonymous2DialogInterface.putExtra("orario_oreno", str);
              paramAnonymous2DialogInterface.putExtra("coperti_preno", i);
              TavoliActivity.this.startActivityForResult(paramAnonymous2DialogInterface, 2);
            }
          });
          localBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              paramAnonymous2DialogInterface = new Intent(TavoliActivity.this, OrdineActivity.class);
              paramAnonymous2DialogInterface.putExtra("numero_sala", j);
              paramAnonymous2DialogInterface.putExtra("numero_tavolo", k);
              paramAnonymous2DialogInterface.putExtra("cod_op", TavoliActivity.this._CodiceOP);
              paramAnonymous2DialogInterface.putExtra("alfa_op", TavoliActivity.this._AlfaOP);
              paramAnonymous2DialogInterface.putExtra("nominativo_preno", "");
              paramAnonymous2DialogInterface.putExtra("orario_oreno", "");
              paramAnonymous2DialogInterface.putExtra("coperti_preno", 0);
              TavoliActivity.this.startActivityForResult(paramAnonymous2DialogInterface, 2);
            }
          });
          localBuilder.show();
          return;
        }
        paramAnonymousView = new Intent(TavoliActivity.this, OrdineActivity.class);
        paramAnonymousView.putExtra("numero_sala", j);
        paramAnonymousView.putExtra("numero_tavolo", k);
        paramAnonymousView.putExtra("cod_op", TavoliActivity.this._CodiceOP);
        paramAnonymousView.putExtra("alfa_op", TavoliActivity.this._AlfaOP);
        paramAnonymousView.putExtra("nominativo_preno", "");
        paramAnonymousView.putExtra("orario_oreno", "");
        paramAnonymousView.putExtra("coperti_preno", 0);
        TavoliActivity.this.startActivityForResult(paramAnonymousView, 2);
        return;
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("TavoliActivity - gestoreTavoli:", paramAnonymousView.getMessage());
      }
    }
  };
  ProgressDialog pb;
  TextView salaScelta;
  float scale;
  TableLayout tl;
  
  private void CaricaSale()
  {
    Cursor localCursor = SaleLocale.getAllSale(this.db);
    this.bSale.clear();
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296306);
    int i = 0;
    while (localCursor.moveToNext())
    {
      this.bSale.add(new Button(this));
      ((Button)this.bSale.get(i)).setText(localCursor.getString(1));
      ((Button)this.bSale.get(i)).setBackgroundResource(2130837515);
      ((Button)this.bSale.get(i)).setTextSize(35.0F / this.scale);
      ((Button)this.bSale.get(i)).setOnClickListener(this.gestoreSale);
      ((Button)this.bSale.get(i)).setTextColor(Color.parseColor("#FFFFFF"));
      localLinearLayout.addView((Button)this.bSale.get(i));
      i += 1;
    }
  }
  
  private void CaricaTavoli(String paramString)
  {
    this.pb = ProgressDialog.show(this, getString(2131099668), "START...", true, false);
    new SincTask(null).execute(new String[] { paramString.replace('[', ' ').replace(']', ' ').trim() });
  }
  
  private ArrayList<TableRow> CaricamentoTavoli(String paramString, int paramInt1, List<TavoloXML> paramList, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1;
    int n;
    int k;
    int m;
    Button localButton;
    Object localObject2;
    try
    {
      localObject1 = getResources();
      f = 90.0F;
      if (this.scale < 1.0F) {
        f = 120.0F;
      }
      if (this.scale > 1.0F) {
        f = 75.0F;
      }
      if (this.scale >= 2.0F) {
        f = 70.0F;
      }
      i1 = (int)TypedValue.applyDimension(1, f, ((Resources)localObject1).getDisplayMetrics());
      i2 = (int)TypedValue.applyDimension(1, 5.0F, ((Resources)localObject1).getDisplayMetrics());
      localObject1 = new TableRow(this);
      ((TableRow)localObject1).setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
      ((TableRow)localObject1).setPadding(i2, i2, i2, i2);
      ((TableRow)localObject1).setGravity(17);
      n = 0;
      k = 1;
      m = paramInt2;
      if (m >= paramInt1 + paramInt2) {
        break label660;
      }
      localButton = new Button(this);
      localObject3 = new TavoloXML();
      ((TavoloXML)localObject3).setSala(paramString);
      ((TavoloXML)localObject3).setNumero(m + 1);
      ((TavoloXML)localObject3).setSituazione(0);
      localObject2 = localObject3;
      if (paramList.contains(localObject3)) {
        localObject2 = (TavoloXML)paramList.get(paramList.indexOf(localObject3));
      }
      localObject3 = ((TavoloXML)localObject2).getSala() + "_" + Integer.toString(((TavoloXML)localObject2).getNumero());
      str = Integer.toString(((TavoloXML)localObject2).getNumero());
      f = 30.0F / this.scale;
      switch (((TavoloXML)localObject2).getSituazione())
      {
      case 1: 
        localButton.setBackgroundResource(2130837524);
      }
    }
    catch (Exception paramString)
    {
      for (;;)
      {
        float f;
        int i1;
        int i2;
        String str;
        label320:
        paramString = paramString;
        Log.e("CaricamentoTavoli:", paramString.getMessage());
        return localArrayList;
        localButton.setBackgroundResource(2130837522);
        Object localObject3 = (String)localObject3 + "_" + ((TavoloXML)localObject2).getnominativoPreno() + "_" + ((TavoloXML)localObject2).getOrarioPrenoFormattato() + "_" + ((TavoloXML)localObject2).getCoperti();
        continue;
        localButton.setBackgroundResource(2130837520);
      }
    }
    finally {}
    localButton.setTag(localObject3);
    localButton.setText(str);
    localButton.setTextSize(f);
    localButton.setGravity(17);
    localButton.setLayoutParams(new TableRow.LayoutParams(i1, i1));
    int j = 0;
    switch (this._operazione)
    {
    }
    label392:
    int i;
    for (;;)
    {
      i = n;
      localObject2 = localObject1;
      if (j != 1) {
        break label682;
      }

      if (n < 3) {
        break label702;
      }
      localArrayList.add(localObject1);
      localObject2 = new TableRow(this);
      ((TableRow)localObject2).setPadding(i2, i2, i2, i2);
      ((TableRow)localObject2).setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      ((TableRow)localObject2).setGravity(17);
      i = 0;
      k = 1;
      break label682;
      localButton.setBackgroundResource(2130837518);
      break label320;
      label660:
      do
      {
        switch (((TavoloXML)localObject2).getSituazione())
        {
        case 1: 
        case 3: 
          j = 1;
          localButton.setOnClickListener(this.gestoreConto);
          ((TableRow)localObject1).addView(localButton);
          break label392;
          j = 1;
          localButton.setOnClickListener(this.gestoreTavoli);
          ((TableRow)localObject1).addView(localButton);
          break label392;
        }
      } while (k != 0);
      localArrayList.add(localObject1);
      return localArrayList;
      break;
    }
    for (;;)
    {
      label682:
      m += 1;
      n = i;
      localObject1 = localObject2;
      break;
      break label392;
      label702:
      i = n + 1;
      k = 0;
      localObject2 = localObject1;
    }
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
  
  private networkresult InviaRichiestaConto(String paramString1, String paramString2, int paramInt)
  {
    networkresult localnetworkresult = new networkresult(0, "", "");
    OutputStream localOutputStream = null;
    try
    {
      localgenericresult = new genericresult(0, null, "");
      i = 0;
    }
    catch (UnknownHostException paramString1)
    {
      for (;;)
      {
        genericresult localgenericresult;
        int i;
        Object localObject;
        ArrayList localArrayList;
        byte[] arrayOfByte;
        paramString1.printStackTrace();
        localnetworkresult.result = -1;
        localnetworkresult.errMesg = paramString1.getMessage();
        return localnetworkresult;
        i += 1;
      }
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = paramString1.getMessage();
    }
    localObject = localOutputStream;
    if (i < 3)
    {
      localgenericresult = CollegaAlServer(paramString2, paramInt, 1000);
      if (localgenericresult.result != 0) {
        break label340;
      }
      localObject = (Socket)localgenericresult.Dati;
      ((Socket)localObject).setSoTimeout(5000);
      paramString2 = ((Socket)localObject).getInputStream();
      localOutputStream = ((Socket)localObject).getOutputStream();
      localArrayList = new ArrayList();
      localArrayList.add(Byte.valueOf((byte)96));
      arrayOfByte = TLVParser.intToByteArray(paramString1.length());
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
      localOutputStream.write(paramString1);
      paramString1 = TLVParser.readTLV(paramString2, 96);
      if (paramString1 != null) {
        break label301;
      }
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = "Errore comunicazione con il server";
    }
    for (;;)
    {
      if (localObject != null) {
        ((Socket)localObject).close();
      }
      if (localgenericresult.result == 0) {
        break;
      }
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = "Errore collegamento con il server";
      return localnetworkresult;
      label301:
      localnetworkresult.Dati = new String(paramString1);
    }
    label340:
    return localnetworkresult;
  }
  
  private networkresult RichiestaSituazioneTavoli(String paramString1, String paramString2, int paramInt)
  {
    networkresult localnetworkresult = new networkresult(0, "", "");
    OutputStream localOutputStream = null;
    try
    {
      localgenericresult = new genericresult(0, null, "");
      i = 0;
    }
    catch (UnknownHostException paramString1)
    {
      for (;;)
      {
        genericresult localgenericresult;
        int i;
        Object localObject;
        ArrayList localArrayList;
        byte[] arrayOfByte;
        paramString1.printStackTrace();
        localnetworkresult.result = -1;
        localnetworkresult.errMesg = paramString1.getMessage();
        return localnetworkresult;
        i += 1;
      }
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = paramString1.getMessage();
    }
    localObject = localOutputStream;
    if (i < 3)
    {
      localgenericresult = CollegaAlServer(paramString2, paramInt, 1000);
      if (localgenericresult.result != 0) {
        break label340;
      }
      localObject = (Socket)localgenericresult.Dati;
      ((Socket)localObject).setSoTimeout(5000);
      paramString2 = ((Socket)localObject).getInputStream();
      localOutputStream = ((Socket)localObject).getOutputStream();
      localArrayList = new ArrayList();
      localArrayList.add(Byte.valueOf((byte)98));
      arrayOfByte = TLVParser.intToByteArray(paramString1.length());
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
      localOutputStream.write(paramString1);
      paramString1 = TLVParser.readTLV(paramString2, 98);
      if (paramString1 != null) {
        break label301;
      }
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = "Errore comunicazione con il server";
    }
    for (;;)
    {
      if (localObject != null) {
        ((Socket)localObject).close();
      }
      if (localgenericresult.result == 0) {
        break;
      }
      localnetworkresult.result = -1;
      localnetworkresult.errMesg = "Errore collegamento con il server";
      return localnetworkresult;
      label301:
      localnetworkresult.Dati = new String(paramString1);
    }
    label340:
    return localnetworkresult;
  }
  
  private genericresult RilevamentoTavoliOccupati(String paramString)
  {
    genericresult localgenericresult = new genericresult(0, null, "");
    Object localObject = "";
    int i = 0;
    Cursor localCursor = ConnessioneAlServer.getAllServer(this.db);
    while (localCursor.moveToNext())
    {
      localObject = localCursor.getString(0);
      i = localCursor.getInt(1);
    }
    int j;
    if (localObject != "")
    {
      j = i;
      if (i != 0) {}
    }
    else
    {
      localObject = "192.168.1.2";
      j = 4444;
    }
    localObject = RichiestaSituazioneTavoli(paramString, (String)localObject, j);
    if (((networkresult)localObject).result != 0)
    {
      localgenericresult.result = ((networkresult)localObject).result;
      localgenericresult.errMesg = ((networkresult)localObject).errMesg;
    }
    for (;;)
    {
      localCursor.close();
      return localgenericresult;
      paramString = new ParserSituazioneTavoli();
      localObject = paramString.parseXml(((networkresult)localObject).Dati);
      localgenericresult.result = ((networkresult)localObject).result;
      if (((networkresult)localObject).result != 0) {
        localgenericresult.errMesg = ((networkresult)localObject).errMesg;
      } else {
        localgenericresult.Dati = paramString.getTavoliOccupati();
      }
    }
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
        TavoliActivity.this.finish();
      }
    });
    localBuilder.show();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == 0) {}
    while (paramInt2 != -1) {
      return;
    }
    try
    {
      finish();
      return;
    }
    catch (Exception paramIntent)
    {
      Log.e("OrdineActivity - onActivityResult:", paramIntent.toString());
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    try
    {
      super.onCreate(paramBundle);
      requestWindowFeature(1);
      setContentView(2130903045);
      this.db = this.databaseHelper.getWritableDatabase();
      this.tl = ((TableLayout)findViewById(2131296310));
      this.bPrevTavoli = ((Button)findViewById(2131296309));
      this.bPrevTavoli.setOnClickListener(this.gestore);
      this.scale = this.tl.getContext().getResources().getDisplayMetrics().density;
      this.bSale = new ArrayList();
      this.salaScelta = ((TextView)findViewById(2131296307));
      this.salaScelta.setText("");
      this._CodiceOP = getIntent().getExtras().getString("cod_op");
      this._AlfaOP = getIntent().getExtras().getString("alfa_op");
      this._operazione = getIntent().getExtras().getInt("operazione");
      CaricaSale();
      return;
    }
    catch (Exception paramBundle)
    {
      paramBundle = paramBundle.getMessage();
      System.out.println(paramBundle);
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.db.close();
    this.databaseHelper.close();
  }
  
  private class RichiestaContoSincTask
    extends AsyncTask<String, String, networkresult>
  {
    private RichiestaContoSincTask() {}
    
    protected networkresult doInBackground(String... paramVarArgs)
    {
      networkresult localnetworkresult = new networkresult(0, "", "");
      for (;;)
      {
        int i;
        int j;
        try
        {
          String str = paramVarArgs[0].replace('[', ' ').replace(']', ' ').trim();
          paramVarArgs = "";
          i = 0;
          Cursor localCursor = ConnessioneAlServer.getAllServer(TavoliActivity.this.db);
          if (localCursor.moveToNext())
          {
            paramVarArgs = localCursor.getString(0);
            i = localCursor.getInt(1);
            continue;
            publishProgress(new String[] { "Comunicazione con il server..." });
            paramVarArgs = TavoliActivity.this.InviaRichiestaConto(str, paramVarArgs, j);
            return paramVarArgs;
          }
        }
        catch (Exception paramVarArgs)
        {
          paramVarArgs = paramVarArgs;
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = paramVarArgs.getMessage();
          Log.e("RichiestaContoSincTask:", paramVarArgs.getMessage());
          return localnetworkresult;
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
    
    protected void onPostExecute(networkresult paramnetworkresult)
    {
      try
      {
        if (paramnetworkresult.result != 0) {
          TavoliActivity.this.VisualizzaErrore("Errore", paramnetworkresult.errMesg);
        }
        for (;;)
        {
          TavoliActivity.this.pb.dismiss();
          return;
          if (paramnetworkresult.errMesg.compareTo("") != 0) {
            TavoliActivity.this.VisualizzaErrore("Errore", paramnetworkresult.errMesg);
          } else {
            TavoliActivity.this.VisualizzaOK("OK", "la richiesta è stata inviata correttamente");
          }
        }
        return;
      }
      catch (Exception paramnetworkresult) {}
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      TavoliActivity.this.pb.setMessage(paramVarArgs[0]);
    }
  }
  
  private class SincTask
    extends AsyncTask<String, String, genericresult>
  {
    private SincTask() {}
    
    protected genericresult doInBackground(String... paramVarArgs)
    {
      genericresult localgenericresult = new genericresult(0, null, "");
      try
      {
        String str = paramVarArgs[0].replace('[', ' ').replace(']', ' ').trim();
        Object localObject = "";
        int k = 0;
        int m = 0;
        Cursor localCursor = SaleLocale.getAllSale(TavoliActivity.this.db);
        do
        {
          paramVarArgs = (String[])localObject;
          j = m;
          i = k;
          if (!localCursor.moveToNext()) {
            break;
          }
        } while (localCursor.getString(1).compareToIgnoreCase(str) != 0);
        paramVarArgs = localCursor.getString(0);
        int i = localCursor.getInt(2);
        int j = localCursor.getInt(3);
        publishProgress(new String[] { "Comunicazione con il server..." });
        localObject = TavoliActivity.this.RilevamentoTavoliOccupati(paramVarArgs);
        publishProgress(new String[] { "Caricamento maschera..." });
        if ((((genericresult)localObject).result == 0) && ((((genericresult)localObject).Dati instanceof ArrayList)))
        {
          localObject = (ArrayList)((genericresult)localObject).Dati;
          localgenericresult.Dati = TavoliActivity.this.CaricamentoTavoli(paramVarArgs, i, (List)localObject, j);
          return localgenericresult;
        }
        localgenericresult.result = 0;
        localgenericresult.errMesg = ((genericresult)localObject).errMesg;
        localgenericresult.Dati = TavoliActivity.this.CaricamentoTavoli(paramVarArgs, i, new ArrayList(), j);
        return localgenericresult;
      }
      catch (Exception paramVarArgs)
      {
        paramVarArgs = paramVarArgs;
        Log.e("StartDbSincTask:", paramVarArgs.getMessage());
        return localgenericresult;
      }
      finally {}
    }
    
    protected void onPostExecute(genericresult paramgenericresult)
    {
      try
      {
        if (paramgenericresult.result != 0) {
          TavoliActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
        }
        for (;;)
        {
          TavoliActivity.this.pb.dismiss();
          return;
          if (paramgenericresult.errMesg.compareTo("") != 0) {
            TavoliActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
          }
          paramgenericresult = (ArrayList)paramgenericresult.Dati;
          int i = 0;
          while (i < paramgenericresult.size())
          {
            TavoliActivity.this.tl.addView((View)paramgenericresult.get(i), new TableLayout.LayoutParams(-1, -2));
            i += 1;
          }
        }
        return;
      }
      catch (Exception paramgenericresult) {}
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      TavoliActivity.this.pb.setMessage(paramVarArgs[0]);
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\TavoliActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */