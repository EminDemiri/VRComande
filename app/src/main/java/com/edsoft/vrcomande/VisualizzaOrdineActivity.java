package com.edsoft.vrcomande;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.networkutility.ParserComanda;
import com.edsoft.vrcomande.core.networkutility.TLVParser;
import com.edsoft.vrcomande.core.networkutility.VisuArticoloXML;
import com.edsoft.vrcomande.core.networkutility.networkresult;
import com.edsoft.vrcomande.core.scontrin.richiestaconto;
import com.edsoft.vrcomande.core.scontrin.streamoutresult;

public class VisualizzaOrdineActivity
  extends Activity
{
  final int CMD_INSERISCI_COMANDA = 0;
  final int CMD_RICHIEDI_CONTO = 1;
  TextView ValoreConto;
  private int _numeroSala;
  private int _numeroTavolo;
  private ArticoliVisuAdapter adap;
  Button bChiudiVisualizzazione;
  Button bRistampaComanda;
  private DBHelper databaseHelper = new DBHelper(this);
  private SQLiteDatabase db;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        switch (paramAnonymousView.getId())
        {
        case 2131296321: 
          VisualizzaOrdineActivity.this.finish();
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("VisualizzaOrdineActivity - gestore:", paramAnonymousView.getMessage());
        return;
      }
      VisualizzaOrdineActivity.this.RichiediRistampaComande();
      return;
    }
  };
  private ListView mainListView;
  private ProgressDialog pb;
  
  private void CaricaVecchieComande()
  {
    Object localObject = new richiestaconto();
    ((richiestaconto)localObject).setSala(Integer.toString(this._numeroSala));
    ((richiestaconto)localObject).setTavolo(Integer.toString(this._numeroTavolo));
    localObject = ((richiestaconto)localObject).ToXml();
    if (((streamoutresult)localObject).result == 0)
    {
      localObject = (ByteArrayOutputStream)((streamoutresult)localObject).Dati;
      this.pb = ProgressDialog.show(this, getString(2131099667), "START...", true, false);
      RichiestaVecchieComandeSincTask localRichiestaVecchieComandeSincTask = new RichiestaVecchieComandeSincTask(null);
      localRichiestaVecchieComandeSincTask.context = this;
      localRichiestaVecchieComandeSincTask.execute(new String[] { ((ByteArrayOutputStream)localObject).toString().trim() });
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
  
  private networkresult InviaRichiestaRistampaComande(String paramString1, String paramString2, int paramInt)
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
        break label335;
      }
      localObject = (Socket)localgenericresult.Dati;
      ((Socket)localObject).setSoTimeout(5000);
      paramString2 = ((Socket)localObject).getInputStream();
      localOutputStream = ((Socket)localObject).getOutputStream();
      localArrayList = new ArrayList();
      localArrayList.add(Byte.valueOf((byte)93));
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
      paramString1 = TLVParser.readTLV(paramString2, 93);
      if (paramString1 != null) {
        break label296;
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
      label296:
      localnetworkresult.Dati = new String(paramString1);
    }
    label335:
    return localnetworkresult;
  }
  
  private genericresult InviaRichiestaVecchieComande(String paramString1, String paramString2, int paramInt)
  {
    genericresult localgenericresult2 = new genericresult(0, null, "");
    OutputStream localOutputStream = null;
    for (;;)
    {
      try
      {
        localgenericresult1 = new genericresult(0, null, "");
        i = 0;
        localObject = localOutputStream;
        if (i < 3)
        {
          localgenericresult1 = CollegaAlServer(paramString2, paramInt, 1000);
          if (localgenericresult1.result != 0) {
            break label487;
          }
          localObject = (Socket)localgenericresult1.Dati;
          ((Socket)localObject).setSoTimeout(5000);
          paramString2 = ((Socket)localObject).getInputStream();
          localOutputStream = ((Socket)localObject).getOutputStream();
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(Byte.valueOf((byte)94));
          byte[] arrayOfByte = TLVParser.intToByteArray(paramString1.length());
          TLVParser.reverse(arrayOfByte);
          paramInt = 0;
          if (paramInt < arrayOfByte.length)
          {
            localArrayList.add(Byte.valueOf(arrayOfByte[paramInt]));
            paramInt += 1;
            continue;
          }
          paramString1 = paramString1.getBytes("UTF-8");
          paramInt = 0;
          if (paramInt < paramString1.length)
          {
            localArrayList.add(Byte.valueOf(paramString1[paramInt]));
            paramInt += 1;
            continue;
          }
          paramString1 = new byte[localArrayList.size()];
          paramInt = 0;
          if (paramInt < localArrayList.size())
          {
            paramString1[paramInt] = ((Byte)localArrayList.get(paramInt)).byteValue();
            paramInt += 1;
            continue;
          }
          localOutputStream.write(paramString1);
          paramString1 = TLVParser.readTLV(paramString2, 94);
          if (paramString1 != null) {
            continue;
          }
          localgenericresult2.result = -1;
          localgenericresult2.errMesg = "Errore comunicazione con il server";
        }
      }
      catch (UnknownHostException paramString1)
      {
        genericresult localgenericresult1;
        Object localObject;
        paramString1.printStackTrace();
        localgenericresult2.result = -1;
        localgenericresult2.errMesg = paramString1.getMessage();
        return localgenericresult2;
        paramString1 = new ParserComanda();
        paramString2 = paramString1.parseXml((String)localgenericresult2.Dati);
        if (paramString2.result == 0) {
          break label478;
        }
        localgenericresult2.result = -1;
        localgenericresult2.errMesg = paramString2.errMesg;
        continue;
      }
      catch (IOException paramString1)
      {
        paramString1.printStackTrace();
        localgenericresult2.result = -1;
        localgenericresult2.errMesg = paramString1.getMessage();
        return localgenericresult2;
      }
      if (localObject != null) {
        ((Socket)localObject).close();
      }
      if (localgenericresult1.result == 0) {
        break;
      }
      localgenericresult2.result = -1;
      localgenericresult2.errMesg = "Errore collegamento con il server";
      return localgenericresult2;
      localgenericresult2.Dati = new String(paramString1);
      int i = 1;
      paramInt = i;
      if (((String)localgenericresult2.Dati).startsWith("<?xml version='1.0' encoding='utf-8'?>".replace('\'', '"')))
      {
        paramInt = i;
        if (((String)localgenericresult2.Dati).contains("RichiestaOrdiniTavolo")) {
          paramInt = 0;
        }
      }
      if (paramInt != 0)
      {
        localgenericresult2.result = -1;
        localgenericresult2.errMesg = ((String)localgenericresult2.Dati);
      }
      else
      {
        label478:
        localgenericresult2.Dati = paramString1;
        continue;
        label487:
        i += 1;
      }
    }
    return localgenericresult2;
  }
  
  private void RichiediRistampaComande()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Conferma ristampa comanda...");
    localBuilder.setMessage("Sei certo di voler ristampare la comanda?");
    localBuilder.setIcon(2130837541);
    localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = new richiestaconto();
        paramAnonymousDialogInterface.setSala(Integer.toString(VisualizzaOrdineActivity.this._numeroSala));
        paramAnonymousDialogInterface.setTavolo(Integer.toString(VisualizzaOrdineActivity.this._numeroTavolo));
        paramAnonymousDialogInterface = paramAnonymousDialogInterface.ToXml();
        if (paramAnonymousDialogInterface.result == 0)
        {
          paramAnonymousDialogInterface = (ByteArrayOutputStream)paramAnonymousDialogInterface.Dati;
          String str = VisualizzaOrdineActivity.this.getString(2131099653);
          VisualizzaOrdineActivity.access$402(VisualizzaOrdineActivity.this, ProgressDialog.show(VisualizzaOrdineActivity.this, str, "START...", true, false));
          new VisualizzaOrdineActivity.RichiestaRistampaComandeSincTask(VisualizzaOrdineActivity.this, null).execute(new String[] { paramAnonymousDialogInterface.toString().trim() });
        }
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
    try
    {
      super.onCreate(paramBundle);
      requestWindowFeature(1);
      getWindow().setFlags(1024, 1024);
      setContentView(2130903047);
      this.db = this.databaseHelper.getWritableDatabase();
      this.mainListView = ((ListView)findViewById(2131296319));
      this.bChiudiVisualizzazione = ((Button)findViewById(2131296321));
      this.bChiudiVisualizzazione.setOnClickListener(this.gestore);
      this.ValoreConto = ((TextView)findViewById(2131296318));
      this.bRistampaComanda = ((Button)findViewById(2131296320));
      this.bRistampaComanda.setOnClickListener(this.gestore);
      this._numeroSala = getIntent().getExtras().getInt("numero_sala");
      this._numeroTavolo = getIntent().getExtras().getInt("numero_tavolo");
      CaricaVecchieComande();
      this.mainListView.setAdapter(this.adap);
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
  
  private static class ArticoliVisuAdapter
    extends BaseAdapter
  {
    private ArrayList<VisuArticoloXML> articoli;
    private Context context;
    private LayoutInflater mInflater;
    
    public ArticoliVisuAdapter(Context paramContext, ArrayList<VisuArticoloXML> paramArrayList)
    {
      this.articoli = paramArrayList;
      this.context = paramContext;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public int getCount()
    {
      return this.articoli.size();
    }
    
    public Object getItem(int paramInt)
    {
      return this.articoli.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      paramViewGroup = paramView;
      if (paramViewGroup == null) {
        paramView = paramViewGroup;
      }
      try
      {
        paramViewGroup = this.mInflater.inflate(2130903048, null);
        paramView = paramViewGroup;
        ViewHolder localViewHolder = new ViewHolder();
        paramView = paramViewGroup;
        localViewHolder.textLine = ((TextView)paramViewGroup.findViewById(2131296322));
        paramView = paramViewGroup;
        paramViewGroup.setTag(localViewHolder);
        StringBuilder localStringBuilder1;
        StringBuilder localStringBuilder2;
        for (;;)
        {
          paramView = paramViewGroup;
          localStringBuilder1 = new StringBuilder();
          paramView = paramViewGroup;
          VisuArticoloXML localVisuArticoloXML = (VisuArticoloXML)this.articoli.get(paramInt);
          paramView = paramViewGroup;
          localStringBuilder1.append(Integer.toString(localVisuArticoloXML.getQtaArticolo()));
          paramView = paramViewGroup;
          localStringBuilder1.append(" x ");
          paramView = paramViewGroup;
          localStringBuilder1.append(localVisuArticoloXML.getAlfaArticolo());
          paramView = paramViewGroup;
          localStringBuilder1.append("\r\n");
          paramView = paramViewGroup;
          if (localVisuArticoloXML.getElencoVarianti().size() == 0) {
            break label236;
          }
          paramView = paramViewGroup;
          localStringBuilder2 = new StringBuilder("Varianti:\r\n");
          paramInt = 0;
          for (;;)
          {
            paramView = paramViewGroup;
            if (paramInt >= localVisuArticoloXML.getElencoVarianti().size()) {
              break;
            }
            if (paramInt > 0)
            {
              paramView = paramViewGroup;
              localStringBuilder2.append(", ");
            }
            paramView = paramViewGroup;
            localStringBuilder2.append((String)localVisuArticoloXML.getElencoVarianti().get(paramInt));
            paramInt += 1;
          }
          paramView = paramViewGroup;
          localViewHolder = (ViewHolder)paramViewGroup.getTag();
        }
        paramView = paramViewGroup;
        localStringBuilder1.append(localStringBuilder2);
        label236:
        paramView = paramViewGroup;
        localViewHolder.textLine.setText(localStringBuilder1);
        return paramViewGroup;
      }
      catch (Exception paramViewGroup)
      {
        Log.e("ArticoliVisuAdapter - gestore:", paramViewGroup.getMessage());
      }
      return paramView;
    }
    
    static class ViewHolder
    {
      TextView textLine;
    }
  }
  
  private class RichiestaRistampaComandeSincTask
    extends AsyncTask<String, String, networkresult>
  {
    public Context context;
    
    private RichiestaRistampaComandeSincTask() {}
    
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
          Cursor localCursor = ConnessioneAlServer.getAllServer(VisualizzaOrdineActivity.this.db);
          if (localCursor.moveToNext())
          {
            paramVarArgs = localCursor.getString(0);
            i = localCursor.getInt(1);
            continue;
            publishProgress(new String[] { "Comunicazione con il server..." });
            paramVarArgs = VisualizzaOrdineActivity.this.InviaRichiestaRistampaComande(str, paramVarArgs, j);
            return paramVarArgs;
          }
        }
        catch (Exception paramVarArgs)
        {
          paramVarArgs = paramVarArgs;
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = paramVarArgs.getMessage();
          Log.e("RichiestaRistampaComandeSincTask:", paramVarArgs.getMessage());
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
      for (;;)
      {
        try
        {
          if (paramnetworkresult.result != 0)
          {
            VisualizzaOrdineActivity.this.VisualizzaErrore("Errore", paramnetworkresult.errMesg);
            VisualizzaOrdineActivity.this.pb.dismiss();
            return;
          }
          if (paramnetworkresult.errMesg.compareTo("") != 0) {
            VisualizzaOrdineActivity.this.VisualizzaErrore("Errore", paramnetworkresult.errMesg);
          } else {
            VisualizzaOrdineActivity.this.VisualizzaOK("OK", "la richiesta è stata inviata correttamente");
          }
        }
        catch (Exception paramnetworkresult)
        {
          paramnetworkresult.toString();
          return;
        }
      }
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      VisualizzaOrdineActivity.this.pb.setMessage(paramVarArgs[0]);
    }
  }
  
  private class RichiestaVecchieComandeSincTask
    extends AsyncTask<String, String, genericresult>
  {
    public Context context;
    
    private RichiestaVecchieComandeSincTask() {}
    
    private void CaricaDatiInMaschera(double paramDouble, ArrayList<VisuArticoloXML> paramArrayList)
    {
      try
      {
        VisualizzaOrdineActivity.this.ValoreConto.setText(String.format("%.2f", new Object[] { Double.valueOf(paramDouble) }));
        VisualizzaOrdineActivity.access$902(VisualizzaOrdineActivity.this, new VisualizzaOrdineActivity.ArticoliVisuAdapter(this.context, paramArrayList));
        VisualizzaOrdineActivity.this.mainListView.setAdapter(VisualizzaOrdineActivity.this.adap);
        return;
      }
      catch (Exception paramArrayList)
      {
        Log.e("CaricaReparti:", paramArrayList.toString());
      }
    }
    
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
          Cursor localCursor = ConnessioneAlServer.getAllServer(VisualizzaOrdineActivity.this.db);
          if (localCursor.moveToNext())
          {
            paramVarArgs = localCursor.getString(0);
            i = localCursor.getInt(1);
            continue;
            publishProgress(new String[] { "Comunicazione con il server..." });
            paramVarArgs = VisualizzaOrdineActivity.this.InviaRichiestaVecchieComande(str, paramVarArgs, j);
            return paramVarArgs;
          }
        }
        catch (Exception paramVarArgs)
        {
          paramVarArgs = paramVarArgs;
          localgenericresult.result = -1;
          localgenericresult.errMesg = paramVarArgs.getMessage();
          Log.e("RichiestaVecchieComandeSincTask:", paramVarArgs.getMessage());
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
      for (;;)
      {
        try
        {
          if (paramgenericresult.result != 0)
          {
            VisualizzaOrdineActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
            VisualizzaOrdineActivity.this.pb.dismiss();
            return;
          }
          if (paramgenericresult.errMesg.compareTo("") != 0)
          {
            VisualizzaOrdineActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
            continue;
          }
          if (!(paramgenericresult.Dati instanceof ParserComanda)) {
            continue;
          }
        }
        catch (Exception paramgenericresult)
        {
          paramgenericresult.toString();
          return;
        }
        CaricaDatiInMaschera(((ParserComanda)paramgenericresult.Dati).getTotaleScontrino(), ((ParserComanda)paramgenericresult.Dati).getParsedArticoli());
      }
    }
    
    protected void onProgressUpdate(String... paramVarArgs)
    {
      VisualizzaOrdineActivity.this.pb.setMessage(paramVarArgs[0]);
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\VisualizzaOrdineActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */