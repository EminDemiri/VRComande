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
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.edsoft.vrcomande.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.dbutility.InfoCDP;
import com.edsoft.vrcomande.core.dbutility.MsgForCDP;
import com.edsoft.vrcomande.core.dbutility.Reparti;
import com.edsoft.vrcomande.core.networkutility.InfoCdpXML;
import com.edsoft.vrcomande.core.networkutility.RepartiXML;
import com.edsoft.vrcomande.core.networkutility.TLVParser;
import com.edsoft.vrcomande.core.networkutility.networkresult;
import com.edsoft.vrcomande.core.scontrin.ArticoliComanda;
import com.edsoft.vrcomande.core.scontrin.comanda;
import com.edsoft.vrcomande.core.scontrin.streamoutresult;

public class OrdineActivity
  extends Activity
  implements View.OnClickListener
{
  static final String CODICE_ART_MSG = "MSGFORCDP";
  private final int VISTA_CDP = 2;
  private final int VISTA_COMANDA = 1;
  private final int VISTA_REPARTI = 0;
  String _AlfaOP;
  String _CodiceOP;
  String _androidId;
  String _idComanda = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
  String _nominativo_preno;
  int _numeroSala;
  int _numeroTavolo;
  String _orario_oreno;
  int _qtaCoperti;
  String _sn;
  private RepartiAdapter adap;
  private CDPAdapter adap2;
  private ArticoliComandaAdapter adapComanda;
  private Button bAggiungiCoperto;
  private Button bAnnullaComanda;
  private Button bAnnullaDaRep;
  private Button bBackToComanda;
  private Button bInviaComanda;
  private Button bOrdinazioniPrecedenti;
  private Button bRimuoviCoperto;
  private Button bSbloccaComanda;
  private Button bVediComanda;
  private Button bVediReparti;
  private ListView centridiproduzione;
  DBHelper databaseHelper = new DBHelper(this);
  SQLiteDatabase db;
  private ListView elmentiordinati;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        switch (paramAnonymousView.getId())
        {
        case 2131296295: 
          OrdineActivity.this.GeneraComanda();
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("OrdineActivity - gestore:", paramAnonymousView.getMessage());
        return;
      }
      OrdineActivity.this.ChiudiActivity();
      return;
      paramAnonymousView = OrdineActivity.this;
      paramAnonymousView._qtaCoperti += 1;
      OrdineActivity.this.modqtacoperto.setText(Integer.toString(OrdineActivity.this._qtaCoperti));
      return;
      if (OrdineActivity.this._qtaCoperti > 1)
      {
        paramAnonymousView = OrdineActivity.this;
        paramAnonymousView._qtaCoperti -= 1;
      }
      OrdineActivity.this.modqtacoperto.setText(Integer.toString(OrdineActivity.this._qtaCoperti));
      return;
      OrdineActivity.this.AvviaActivityVisuOldComande();
      return;
      OrdineActivity.this.viewFlipper.setDisplayedChild(0);
      return;
      OrdineActivity.this.viewFlipper.setDisplayedChild(1);
      return;
      OrdineActivity.this.viewFlipper.setDisplayedChild(2);
      return;
      OrdineActivity.this.viewFlipper.setDisplayedChild(1);
      return;
    }
  };
  private TextView labelTavolo;
  float lastX;
  private ListView mainListView;
  private TextView modqtacoperto;
  ProgressDialog pb;
  ViewFlipper viewFlipper;
  
  private void AbortActivity()
  {
    setResult(0, new Intent());
    finish();
  }
  
  private void AvviaActivityVisuOldComande()
  {
    Intent localIntent = new Intent(this, VisualizzaOrdineActivity.class);
    localIntent.putExtra("numero_sala", this._numeroSala);
    localIntent.putExtra("numero_tavolo", this._numeroTavolo);
    startActivity(localIntent);
  }
  
  private void CaricaCDP()
  {
    Object localObject;
    try
    {
      localObject = InfoCDP.getAllCDP(this.db);
      ArrayList localArrayList = new ArrayList();
      while (((Cursor)localObject).moveToNext())
      {
        InfoCdpXML localInfoCdpXML = new InfoCdpXML();
        localInfoCdpXML.setCodCDP(((Cursor)localObject).getString(0));
        localInfoCdpXML.setAlfaCDP(((Cursor)localObject).getString(1));
        localArrayList.add(localInfoCdpXML);
      }
      localObject = new InfoCdpXML[localException.size()];
    }
    catch (Exception localException)
    {
      Log.e("CaricaCDP:", localException.toString());
      return;
    }
    localException.toArray((Object[])localObject);
    this.adap2 = new CDPAdapter(this, (InfoCdpXML[])localObject);
  }
  
  private void CaricaReparti()
  {
    Object localObject;
    try
    {
      localObject = Reparti.getAllReparti(this.db);
      ArrayList localArrayList = new ArrayList();
      while (((Cursor)localObject).moveToNext())
      {
        RepartiXML localRepartiXML = new RepartiXML();
        localRepartiXML.setCodRep(((Cursor)localObject).getString(0));
        localRepartiXML.setAlfaRep(((Cursor)localObject).getString(1));
        localArrayList.add(localRepartiXML);
      }
      localObject = new RepartiXML[localException.size()];
    }
    catch (Exception localException)
    {
      Log.e("CaricaReparti:", localException.toString());
      return;
    }
    localException.toArray((Object[])localObject);
    this.adap = new RepartiAdapter(this, (RepartiXML[])localObject);
  }
  
  private void ChiudiActivity()
  {
    if (this.adapComanda.dati().size() == 0)
    {
      AbortActivity();
      return;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Conferma di eleminazione...");
    localBuilder.setMessage("Sei certo di voler annullare la comanda?");
    localBuilder.setIcon(2130837534);
    localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        OrdineActivity.this.AbortActivity();
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
  
  private void ChiudiOKActivity()
  {
    setResult(-1, new Intent());
    finish();
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
  
  private void GeneraComanda()
  {
    final streamoutresult localstreamoutresult;
    if (this.adapComanda.dati().size() > 0)
    {
      localstreamoutresult = new comanda(this._numeroTavolo, this._numeroSala, this._CodiceOP, this._AlfaOP, this._qtaCoperti, this._androidId, (ArticoliComanda[])this.adapComanda.dati().toArray(new ArticoliComanda[this.adapComanda.dati().size()]), this._idComanda, this._nominativo_preno, this._orario_oreno).ToXml();
      if (localstreamoutresult.result == 0)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("Conferma di invio...");
        localBuilder.setMessage("Sei certo di voler inviare la comanda?");
        localBuilder.setIcon(2130837541);
        localBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface = (ByteArrayOutputStream)localstreamoutresult.Dati;
            String str = OrdineActivity.this.getString(2131099675);
            OrdineActivity.this.pb = ProgressDialog.show(OrdineActivity.this, str, "START...", true, false);
            new OrdineActivity.SincTask(OrdineActivity.this, null).execute(new String[] { paramAnonymousDialogInterface.toString().trim() });
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
    }
    else
    {
      return;
    }
    Toast.makeText(this, localstreamoutresult.errMesg, 1).show();
  }
  
  private networkresult InviaComanda(String paramString1, String paramString2, int paramInt)
  {
    networkresult localnetworkresult = new networkresult(0, "", "");
    ArrayList localArrayList = null;
    label393:
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
          localgenericresult = CollegaAlServer(paramString2, paramInt, 1000);
          if (localgenericresult.result != 0) {
            break label393;
          }
          paramString2 = (Socket)localgenericresult.Dati;
          paramString2.setSoTimeout(5000);
          localObject1 = paramString2.getInputStream();
          localObject2 = paramString2.getOutputStream();
          localArrayList = new ArrayList();
          localArrayList.add(Byte.valueOf((byte)97));
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
          ((OutputStream)localObject2).write(paramString1);
          paramString1 = TLVParser.readTLV((InputStream)localObject1, 97);
          if (paramString1 == null)
          {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = "Errore comunicazione con il server";
            localObject2 = localgenericresult;
            localObject1 = paramString2;
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
        localnetworkresult.Dati = new String(paramString1);
        localObject1 = paramString2;
        localObject2 = localgenericresult;
        if (localnetworkresult.Dati.compareTo("OK") != 0)
        {
          localnetworkresult.result = -1;
          localnetworkresult.errMesg = localnetworkresult.Dati;
          localObject1 = paramString2;
          localObject2 = localgenericresult;
          continue;
          i += 1;
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        localnetworkresult.result = -1;
        localnetworkresult.errMesg = paramString1.getMessage();
        return localnetworkresult;
      }
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
    Toast.makeText(this, paramString2, 1).show();
    ChiudiOKActivity();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == 0) {
      break label11;
    }
    label11:
    while (paramInt2 != -1) {
      return;
    }
    for (;;)
    {
      try
      {
        ArrayList localArrayList = paramIntent.getStringArrayListExtra("varianti_scelte");
        if (localArrayList != null)
        {
          paramIntent = new ArrayList();
          paramInt1 = 0;
          if (paramInt1 < localArrayList.size())
          {
            String[] arrayOfString = ((String)localArrayList.get(paramInt1)).split("\\|");
            if (arrayOfString[0].trim().compareTo("vmsgcdp") != 0) {
              break label301;
            }
            ArticoliComanda localArticoliComanda = new ArticoliComanda();
            localArticoliComanda.setCodArt("MSGFORCDP");
            localArticoliComanda.setAlfaArt(arrayOfString[1]);
            localArticoliComanda.setPrezzoArt(0.0D);
            localArticoliComanda.setIvaArt(0.0D);
            localArticoliComanda.setQta(1);
            localArticoliComanda.AddLinkVariante("#CDP#" + arrayOfString[3] + "|" + arrayOfString[4] + "|0");
            paramIntent.add(localArticoliComanda);
            break label301;
          }
          if (paramIntent.size() <= 0) {
            break label11;
          }
          this.adapComanda.addall(paramIntent);
          this.elmentiordinati.setSelection(this.elmentiordinati.getCount() - 1);
          this.viewFlipper.setDisplayedChild(1);
          return;
        }
      }
      catch (Exception paramIntent)
      {
        Log.e("OrdineActivity - onActivityResult:", paramIntent.toString());
        return;
      }
      boolean bool = paramIntent.getBooleanExtra("vedi_comanda", false);
      paramIntent = paramIntent.getParcelableArrayListExtra("elem_comanda");
      this.adapComanda.addall(paramIntent);
      this.elmentiordinati.setSelection(this.elmentiordinati.getCount() - 1);
      if ((!bool) || (this.viewFlipper.getDisplayedChild() != 0)) {
        break;
      }
      this.viewFlipper.showNext();
      return;
      label301:
      paramInt1 += 1;
    }
  }
  
  public void onClick(View paramView) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this._CodiceOP = getIntent().getExtras().getString("cod_op");
    this._AlfaOP = getIntent().getExtras().getString("alfa_op");
    this._numeroSala = getIntent().getExtras().getInt("numero_sala");
    this._numeroTavolo = getIntent().getExtras().getInt("numero_tavolo");
    this._nominativo_preno = getIntent().getExtras().getString("nominativo_preno");
    this._orario_oreno = getIntent().getExtras().getString("orario_oreno");
    this._qtaCoperti = getIntent().getExtras().getInt("coperti_preno");
    this.db = this.databaseHelper.getWritableDatabase();
    setContentView(2130903044);
    this.viewFlipper = ((ViewFlipper)findViewById(2131296298));
    this._sn = "";
    this._androidId = "";
    if (Build.VERSION.SDK_INT >= 9)
    {
      this._sn = Build.SERIAL;
      this._androidId = Settings.Secure.getString(getContentResolver(), "android_id");
    }
    paramBundle = new RepartiXML();
    paramBundle.setCodRep("1");
    paramBundle.setAlfaRep("PRIMI");
    this.labelTavolo = ((TextView)findViewById(2131296284));
    this.labelTavolo.setText("Sala " + Integer.toString(this._numeroSala) + ", Tavolo " + Integer.toString(this._numeroTavolo));
    this.mainListView = ((ListView)findViewById(2131296291));
    this.elmentiordinati = ((ListView)findViewById(2131296292));
    this.centridiproduzione = ((ListView)findViewById(2131296302));
    CaricaReparti();
    this.mainListView.setAdapter(this.adap);
    CaricaCDP();
    this.centridiproduzione.setAdapter(this.adap2);
    this.bInviaComanda = ((Button)findViewById(2131296295));
    this.bInviaComanda.setOnClickListener(this.gestore);
    this.bAnnullaComanda = ((Button)findViewById(2131296297));
    this.bAnnullaComanda.setOnClickListener(this.gestore);
    this.bAggiungiCoperto = ((Button)findViewById(2131296288));
    this.bAggiungiCoperto.setOnClickListener(this.gestore);
    this.bRimuoviCoperto = ((Button)findViewById(2131296290));
    this.bRimuoviCoperto.setOnClickListener(this.gestore);
    this.bOrdinazioniPrecedenti = ((Button)findViewById(2131296294));
    this.bOrdinazioniPrecedenti.setOnClickListener(this.gestore);
    this.bVediComanda = ((Button)findViewById(2131296266));
    this.bVediComanda.setOnClickListener(this.gestore);
    this.bVediReparti = ((Button)findViewById(2131296264));
    this.bVediReparti.setOnClickListener(this.gestore);
    this.bAnnullaDaRep = ((Button)findViewById(2131296300));
    this.bAnnullaDaRep.setOnClickListener(this.gestore);
    this.bSbloccaComanda = ((Button)findViewById(2131296301));
    this.bSbloccaComanda.setOnClickListener(this.gestore);
    this.bBackToComanda = ((Button)findViewById(2131296304));
    this.bBackToComanda.setOnClickListener(this.gestore);
    this.modqtacoperto = ((TextView)findViewById(2131296286));
    this.modqtacoperto.setText(Integer.toString(this._qtaCoperti));
    this.adapComanda = new ArticoliComandaAdapter(this);
    this.elmentiordinati.setAdapter(this.adapComanda);
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
  
  private class ArticoliComandaAdapter
    extends BaseAdapter
  {
    ArrayList<ArticoliComanda> articoli = new ArrayList();
    private Context context;
    View.OnClickListener gestore = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          if ((paramAnonymousView instanceof Button))
          {
            paramAnonymousView = ((Button)paramAnonymousView).getTag();
            if ((paramAnonymousView instanceof Integer))
            {
              final int i = ((Integer)paramAnonymousView).intValue();
              paramAnonymousView = new AlertDialog.Builder(OrdineActivity.ArticoliComandaAdapter.this.context);
              paramAnonymousView.setTitle("Conferma di eleminazione...");
              paramAnonymousView.setMessage("Sei certo di voler elminare?");
              paramAnonymousView.setIcon(2130837534);
              paramAnonymousView.setPositiveButton("SI", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  paramAnonymous2DialogInterface = (ArticoliComanda)OrdineActivity.ArticoliComandaAdapter.this.articoli.get(i);
                  if (paramAnonymous2DialogInterface.getQta() > 1) {
                    paramAnonymous2DialogInterface.setQta(paramAnonymous2DialogInterface.getQta() - 1);
                  }
                  for (;;)
                  {
                    OrdineActivity.ArticoliComandaAdapter.this.notifyDataSetChanged();
                    return;
                    OrdineActivity.ArticoliComandaAdapter.this.articoli.remove(i);
                  }
                }
              });
              paramAnonymousView.setNegativeButton("NO", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  paramAnonymous2DialogInterface.cancel();
                }
              });
              paramAnonymousView.show();
            }
          }
          return;
        }
        catch (Exception paramAnonymousView)
        {
          Log.e("ArticoliComandaAdapter - gestore:", paramAnonymousView.getMessage());
        }
      }
    };
    private LayoutInflater mInflater;
    
    public ArticoliComandaAdapter(Context paramContext)
    {
      this.context = paramContext;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public void add(ArticoliComanda paramArticoliComanda)
    {
      if (!this.articoli.contains(paramArticoliComanda)) {
        this.articoli.add(paramArticoliComanda);
      }
      for (;;)
      {
        notifyDataSetChanged();
        return;
        int i = ((ArticoliComanda)this.articoli.get(this.articoli.indexOf(paramArticoliComanda))).getQta();
        int j = paramArticoliComanda.getQta();
        ((ArticoliComanda)this.articoli.get(this.articoli.indexOf(paramArticoliComanda))).setQta(i + j);
      }
    }
    
    public void addall(ArrayList<ArticoliComanda> paramArrayList)
    {
      int i = -1;
      int j = 0;
      int m;
      int k;
      if (j < this.articoli.size())
      {
        ArticoliComanda localArticoliComanda = (ArticoliComanda)this.articoli.get(j);
        m = 0;
        for (;;)
        {
          k = i;
          if (m < localArticoliComanda.getElencoVarianti().size())
          {
            if (((String)localArticoliComanda.getElencoVarianti().get(m)).toUpperCase().indexOf("SEGUE:") > 0) {
              k = j;
            }
          }
          else
          {
            j += 1;
            i = k;
            break;
          }
          m += 1;
        }
      }
      j = 0;
      while (j < paramArrayList.size()) {
        if (((ArticoliComanda)paramArrayList.get(j)).getCodArt().trim().compareTo("MSGFORCDP") == 0)
        {
          this.articoli.add(paramArrayList.get(j));
          j += 1;
        }
        else
        {
          int n = 0;
          m = 0;
          label160:
          k = n;
          int i1;
          if (m < ((ArticoliComanda)paramArrayList.get(j)).getElencoVarianti().size())
          {
            if (((String)((ArticoliComanda)paramArrayList.get(j)).getElencoVarianti().get(m)).toUpperCase().indexOf("SEGUE:") > 0) {
              k = 1;
            }
          }
          else
          {
            i1 = 0;
            n = i1;
            if (k == 0) {
              m = 0;
            }
          }
          for (;;)
          {
            n = i1;
            if (m < this.articoli.size())
            {
              if ((((ArticoliComanda)paramArrayList.get(j)).equals(this.articoli.get(m))) && (m > i))
              {
                n = 1;
                i1 = ((ArticoliComanda)this.articoli.get(m)).getQta();
                int i2 = ((ArticoliComanda)paramArrayList.get(j)).getQta();
                ((ArticoliComanda)this.articoli.get(m)).setQta(i1 + i2);
              }
            }
            else
            {
              if (n == 0) {
                this.articoli.add(paramArrayList.get(j));
              }
              if (k == 0) {
                break;
              }
              i = this.articoli.size() - 1;
              break;
              m += 1;
              break label160;
            }
            m += 1;
          }
        }
      }
      notifyDataSetChanged();
    }
    
    public ArrayList<ArticoliComanda> dati()
    {
      return this.articoli;
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
        paramViewGroup = this.mInflater.inflate(2130903052, null);
        paramView = paramViewGroup;
        ViewHolder localViewHolder = new ViewHolder();
        paramView = paramViewGroup;
        localViewHolder.textLine = ((TextView)paramViewGroup.findViewById(2131296328));
        paramView = paramViewGroup;
        localViewHolder.buttonLine = ((Button)paramViewGroup.findViewById(2131296329));
        paramView = paramViewGroup;
        paramViewGroup.setTag(localViewHolder);
        StringBuilder localStringBuilder1;
        ArticoliComanda localArticoliComanda;
        StringBuilder localStringBuilder2;
        int i;
        for (;;)
        {
          paramView = paramViewGroup;
          localStringBuilder1 = new StringBuilder();
          paramView = paramViewGroup;
          localArticoliComanda = (ArticoliComanda)this.articoli.get(paramInt);
          paramView = paramViewGroup;
          if (localArticoliComanda.getCodArt().compareTo("MSGFORCDP") != 0) {
            break label297;
          }
          paramView = paramViewGroup;
          localStringBuilder1.append(localArticoliComanda.getAlfaArt());
          paramView = paramViewGroup;
          localStringBuilder1.append("\r\n");
          paramView = paramViewGroup;
          if (localArticoliComanda.getElencoVarianti().size() == 0) {
            break label255;
          }
          paramView = paramViewGroup;
          localStringBuilder2 = new StringBuilder("messaggio diretto a:\r\n");
          i = 0;
          for (;;)
          {
            paramView = paramViewGroup;
            if (i >= localArticoliComanda.getElencoVarianti().size()) {
              break;
            }
            if (i > 0)
            {
              paramView = paramViewGroup;
              localStringBuilder2.append(", ");
            }
            paramView = paramViewGroup;
            localStringBuilder2.append(((String)localArticoliComanda.getElencoVarianti().get(i)).split("\\|")[1]);
            i += 1;
          }
          paramView = paramViewGroup;
          localViewHolder = (ViewHolder)paramViewGroup.getTag();
        }
        paramView = paramViewGroup;
        localStringBuilder1.append(localStringBuilder2);
        label255:
        paramView = paramViewGroup;
        localViewHolder.textLine.setText(localStringBuilder1);
        paramView = paramViewGroup;
        localViewHolder.buttonLine.setTag(Integer.valueOf(paramInt));
        paramView = paramViewGroup;
        localViewHolder.buttonLine.setOnClickListener(this.gestore);
        return paramViewGroup;
        label297:
        paramView = paramViewGroup;
        localStringBuilder1.append(Integer.toString(localArticoliComanda.getQta()));
        paramView = paramViewGroup;
        localStringBuilder1.append(" x ");
        paramView = paramViewGroup;
        localStringBuilder1.append(localArticoliComanda.getAlfaArt());
        paramView = paramViewGroup;
        localStringBuilder1.append("\r\n");
        paramView = paramViewGroup;
        if (localArticoliComanda.getElencoVarianti().size() != 0)
        {
          paramView = paramViewGroup;
          localStringBuilder2 = new StringBuilder("Varianti:\r\n");
          i = 0;
          for (;;)
          {
            paramView = paramViewGroup;
            if (i >= localArticoliComanda.getElencoVarianti().size()) {
              break;
            }
            if (i > 0)
            {
              paramView = paramViewGroup;
              localStringBuilder2.append(", ");
            }
            paramView = paramViewGroup;
            localStringBuilder2.append(((String)localArticoliComanda.getElencoVarianti().get(i)).split("\\|")[1]);
            i += 1;
          }
          paramView = paramViewGroup;
          localStringBuilder1.append(localStringBuilder2);
        }
        paramView = paramViewGroup;
        localViewHolder.textLine.setText(localStringBuilder1);
        paramView = paramViewGroup;
        localViewHolder.buttonLine.setTag(Integer.valueOf(paramInt));
        paramView = paramViewGroup;
        localViewHolder.buttonLine.setOnClickListener(this.gestore);
        return paramViewGroup;
      }
      catch (Exception paramViewGroup)
      {
        Log.e("ArticoliComandaAdapter - gestore:", paramViewGroup.getMessage());
      }
      return paramView;
    }
    
    class ViewHolder
    {
      Button buttonLine;
      TextView textLine;
      
      ViewHolder() {}
    }
  }
  
  private class CDPAdapter
    extends BaseAdapter
  {
    private InfoCdpXML[] cdp;
    private Context context;
    View.OnClickListener gestore = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ArrayList localArrayList;
        Object localObject;
        try
        {
          localArrayList = new ArrayList();
          localObject = MsgForCDP.getAllMsgForCDP(OrdineActivity.this.db);
          while (((Cursor)localObject).moveToNext())
          {
            ((Cursor)localObject).getString(0);
            String str1 = ((Cursor)localObject).getString(1);
            String str2 = ((Cursor)localObject).getString(2);
            if ((str2.compareTo((String)paramAnonymousView.getTag()) == 0) || (str2.compareTo("0") == 0)) {
              localArrayList.add(str1);
            }
          }
          localObject = new Intent(OrdineActivity.CDPAdapter.this.context, VariantiActivity.class);
        }
        catch (Exception paramAnonymousView)
        {
          Log.e("CDPAdapter - gestore:", paramAnonymousView.getMessage());
          return;
        }
        ((Intent)localObject).putExtra("cdp_msg", (String)paramAnonymousView.getTag());
        ((Intent)localObject).putExtra("alfa_cdp", (String)((TextView)paramAnonymousView).getText());
        ((Intent)localObject).putExtra("alfa_cdp", (String)((TextView)paramAnonymousView).getText());
        ((Intent)localObject).putStringArrayListExtra("msg_predef", localArrayList);
        OrdineActivity.this.startActivityForResult((Intent)localObject, 2);
      }
    };
    private LayoutInflater mInflater;
    
    public CDPAdapter(Context paramContext, InfoCdpXML[] paramArrayOfInfoCdpXML)
    {
      this.cdp = paramArrayOfInfoCdpXML;
      this.context = paramContext;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public int getCount()
    {
      return this.cdp.length;
    }
    
    public Object getItem(int paramInt)
    {
      return this.cdp[paramInt];
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
      for (;;)
      {
        try
        {
          paramViewGroup = this.mInflater.inflate(2130903051, null);
          paramView = paramViewGroup;
          localViewHolder = new ViewHolder();
          paramView = paramViewGroup;
          localViewHolder.textLine = ((TextView)paramViewGroup.findViewById(2131296327));
          paramView = paramViewGroup;
          paramViewGroup.setTag(localViewHolder);
          paramView = paramViewGroup;
          localViewHolder.textLine.setText(this.cdp[paramInt].getAlfaCDP());
          paramView = paramViewGroup;
          localViewHolder.textLine.setTag(this.cdp[paramInt].getCodCDP());
          paramView = paramViewGroup;
          localViewHolder.textLine.setOnClickListener(this.gestore);
          return paramViewGroup;
        }
        catch (Exception paramViewGroup)
        {
          ViewHolder localViewHolder;
          Log.e("CDPAdapter - gestore:", paramViewGroup.getMessage());
        }
        paramView = paramViewGroup;
        localViewHolder = (ViewHolder)paramViewGroup.getTag();
      }
      return paramView;
    }
    
    class ViewHolder
    {
      TextView textLine;
      
      ViewHolder() {}
    }
  }
  
  private class RepartiAdapter
    extends BaseAdapter
  {
    private Context context;
    View.OnClickListener gestore = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          Intent localIntent = new Intent(OrdineActivity.RepartiAdapter.this.context, ArticoliActivity.class);
          localIntent.putExtra("codice_reparto", (String)paramAnonymousView.getTag());
          OrdineActivity.this.startActivityForResult(localIntent, 2);
          return;
        }
        catch (Exception paramAnonymousView)
        {
          Log.e("RepartiAdapter - gestore:", paramAnonymousView.getMessage());
        }
      }
    };
    private LayoutInflater mInflater;
    private RepartiXML[] reparti;
    
    public RepartiAdapter(Context paramContext, RepartiXML[] paramArrayOfRepartiXML)
    {
      this.reparti = paramArrayOfRepartiXML;
      this.context = paramContext;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public int getCount()
    {
      return this.reparti.length;
    }
    
    public Object getItem(int paramInt)
    {
      return this.reparti[paramInt];
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
      for (;;)
      {
        try
        {
          paramViewGroup = this.mInflater.inflate(2130903053, null);
          paramView = paramViewGroup;
          localViewHolder = new ViewHolder();
          paramView = paramViewGroup;
          localViewHolder.textLine = ((TextView)paramViewGroup.findViewById(2131296330));
          paramView = paramViewGroup;
          paramViewGroup.setTag(localViewHolder);
          paramView = paramViewGroup;
          localViewHolder.textLine.setText(this.reparti[paramInt].getAlfaRep());
          paramView = paramViewGroup;
          localViewHolder.textLine.setTag(this.reparti[paramInt].getCodRep());
          paramView = paramViewGroup;
          localViewHolder.textLine.setOnClickListener(this.gestore);
          return paramViewGroup;
        }
        catch (Exception paramViewGroup)
        {
          ViewHolder localViewHolder;
          Log.e("ArticoliComandaAdapter - gestore:", paramViewGroup.getMessage());
        }
        paramView = paramViewGroup;
        localViewHolder = (ViewHolder)paramViewGroup.getTag();
      }
      return paramView;
    }
    
    class ViewHolder
    {
      TextView textLine;
      
      ViewHolder() {}
    }
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
          Cursor localCursor = ConnessioneAlServer.getAllServer(OrdineActivity.this.db);
          if (localCursor.moveToNext())
          {
            paramVarArgs = localCursor.getString(0);
            i = localCursor.getInt(1);
            continue;
            publishProgress(new String[] { "Comunicazione con il server..." });
            paramVarArgs = OrdineActivity.this.InviaComanda(str, paramVarArgs, j);
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
          OrdineActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
        }
        for (;;)
        {
          OrdineActivity.this.pb.dismiss();
          return;
          if (paramgenericresult.errMesg.compareTo("") != 0) {
            OrdineActivity.this.VisualizzaErrore("Errore", paramgenericresult.errMesg);
          } else {
            OrdineActivity.this.VisualizzaOK("OK", "la comanda è stata inviata correttamente");
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
        OrdineActivity.this.pb.setMessage(paramVarArgs[0]);
        return;
      }
      catch (Exception paramVarArgs)
      {
        Log.e("onProgressUpdate:", paramVarArgs.getMessage());
      }
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\OrdineActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */