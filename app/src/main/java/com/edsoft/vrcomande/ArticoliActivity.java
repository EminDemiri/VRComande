package com.edsoft.vrcomande;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.Articoli;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.dbutility.LinkVariantiArticoli;
import com.edsoft.vrcomande.core.dbutility.Reparti;
import com.edsoft.vrcomande.core.dbutility.fresul;
import com.edsoft.vrcomande.core.networkutility.ArticoliXML;
import com.edsoft.vrcomande.core.scontrin.ArticoliComanda;

public class ArticoliActivity
  extends Activity
  implements View.OnClickListener
{
  private ListView ArticoliListView;
  String _codReparto;
  ArrayList<ArticoliComanda> _elencoart;
  int _qtaArt;
  private ArticoliAdapter adap;
  private Button bAggSegue;
  private Button bAggVarianti;
  private Button bAnnullaArticoli;
  private Button bQtaMinus;
  private Button bQtaPlus;
  private Button bVediComanda;
  private Button bVediReparti;
  DBHelper databaseHelper = new DBHelper(this);
  SQLiteDatabase db;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      for (;;)
      {
        try
        {
          switch (paramAnonymousView.getId())
          {
          case 2131296257: 
          case 2131296259: 
          case 2131296260: 
          case 2131296262: 
          case 2131296265: 
            ArticoliActivity.this.modqta.setText(Integer.toString(ArticoliActivity.this._qtaArt));
            return;
          }
        }
        catch (Exception paramAnonymousView)
        {
          Log.e("ArticoliActivity - gestore:", paramAnonymousView.getMessage());
          return;
        }
        paramAnonymousView = ArticoliActivity.this;
        paramAnonymousView._qtaArt += 1;
        continue;
        if (ArticoliActivity.this._qtaArt > 1)
        {
          paramAnonymousView = ArticoliActivity.this;
          paramAnonymousView._qtaArt -= 1;
          continue;
          ArticoliActivity.this.VediComanda(true);
          continue;
          ArticoliActivity.this.VediComanda(false);
          continue;
          ArticoliActivity.this.ChiudiActivity();
          continue;
          ArticoliActivity.this.AggiungiVariante();
          continue;
          ArticoliActivity.this.AggiungiSegue();
        }
      }
    }
  };
  private TextView modqta;
  Toast t;
  
  private void AbortActivity()
  {
    setResult(0, new Intent());
    finish();
  }
  
  private void CaricaArticoli()
  {
    for (;;)
    {
      try
      {
        localObject = Articoli.getAllArticoli(this.db, this._codReparto);
        ArrayList localArrayList = new ArrayList();
        if (!((Cursor)localObject).moveToNext()) {
          break;
        }
        ArticoliXML localArticoliXML = new ArticoliXML();
        localArticoliXML.setCodArt(((Cursor)localObject).getString(0));
        localArticoliXML.setAlfaArt(((Cursor)localObject).getString(1));
        localArticoliXML.setPrezzoArt(((Cursor)localObject).getDouble(2));
        localArticoliXML.setIvaArt(((Cursor)localObject).getDouble(3));
        Cursor localCursor = LinkVariantiArticoli.getAllArtLink(this.db, localArticoliXML.getCodArt());
        if (localCursor.moveToNext())
        {
          String str1 = localArticoliXML.getCodArt();
          String str2 = localCursor.getString(2);
          String str3 = localCursor.getString(1);
          if (str2.compareTo(str1) == 0) {
            localArticoliXML.AddLinkVariante(str3);
          }
        }
        else
        {
          localException.add(localArticoliXML);
        }
      }
      catch (Exception localException)
      {
        Log.e("CaricaReparti:", localException.toString());
        return;
      }
    }
    Object localObject = new ArticoliXML[localException.size()];
    localException.toArray((Object[])localObject);
    this.adap = new ArticoliAdapter(this, (ArticoliXML[])localObject, this);
  }
  
  private void ChiudiActivity()
  {
    if (this._elencoart.isEmpty())
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
        ArticoliActivity.this.AbortActivity();
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
  
  private ArticoliComanda UltimoArticoloVenduto()
  {
    Object localObject3 = null;
    Object localObject2 = null;
    Object localObject1 = localObject3;
    int i;
    if (this._elencoart != null)
    {
      localObject1 = localObject3;
      if (this._elencoart.size() != 0)
      {
        i = this._elencoart.size() - 1;
        localObject1 = localObject2;
      }
    }
    for (;;)
    {
      if (i >= 0)
      {
        localObject1 = (ArticoliComanda)this._elencoart.get(i);
        if ((localObject1 == null) || (((ArticoliComanda)localObject1).getCodArt() == null)) {}
      }
      else
      {
        return (ArticoliComanda)localObject1;
      }
      i -= 1;
    }
  }
  
  public void AccettaDati(ArticoliComanda paramArticoliComanda, boolean paramBoolean)
  {
    fresul localfresul = SettaRisultato(paramArticoliComanda, paramBoolean);
    if (localfresul.result == 0)
    {
      this.t = Toast.makeText(this, "Articolo inserito: " + Integer.toString(paramArticoliComanda.getQta()) + " x " + paramArticoliComanda.getAlfaArt(), 0);
      this.t.show();
      return;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Errore inserimento articolo...");
    localBuilder.setMessage("Si è verificato il seguente errore mentre si tentatava di aggiungere alla comanda l'articolo " + paramArticoliComanda.getAlfaArt() + ":\r\n" + localfresul.errMesg);
    localBuilder.setIcon(2130837534);
    localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.show();
  }
  
  public void AggiungiSegue()
  {
    try
    {
      if ((this._elencoart != null) && (this._elencoart.size() != 0))
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(this._codReparto);
        StartActivitySegue(localArrayList);
      }
      return;
    }
    catch (Exception localException)
    {
      Log.e("AggiungiVariante:", localException.getMessage());
    }
  }
  
  public void AggiungiVariante()
  {
    ArrayList localArrayList;
    try
    {
      ArticoliComanda localArticoliComanda = UltimoArticoloVenduto();
      if (localArticoliComanda != null)
      {
        localArrayList = new ArrayList();
        localArrayList.add(localArticoliComanda.getCodArt());
        localArrayList.add(localArticoliComanda.getAlfaArt());
        localArrayList.add(Integer.toString(localArticoliComanda.getPrezzoArtInt()));
        localArrayList.add(Integer.toString(localArticoliComanda.getIvaArtInt()));
        Cursor localCursor = LinkVariantiArticoli.getAllArtLink(this.db, localArticoliComanda.getCodArt());
        while (localCursor.moveToNext())
        {
          String str1 = localArticoliComanda.getCodArt();
          String str2 = localCursor.getString(2);
          String str3 = localCursor.getString(1);
          if (str2.compareTo(str1) == 0) {
            localArrayList.add(str3);
          }
        }
      }
      return;
    }
    catch (Exception localException)
    {
      Log.e("AggiungiVariante:", localException.getMessage());
    }
    StartActivityVarianti(localArrayList);
  }
  
  public fresul SettaRisultato(ArticoliComanda paramArticoliComanda, boolean paramBoolean)
  {
    fresul localfresul = new fresul(0, "");
    if (paramBoolean) {}
    for (;;)
    {
      try
      {
        localArticoliComanda = UltimoArticoloVenduto();
        localArticoliComanda.AddLinkVariante((String[])paramArticoliComanda.getElencoVarianti().toArray(new String[paramArticoliComanda.getElencoVarianti().size()]));
        this._elencoart.set(this._elencoart.size() - 1, localArticoliComanda);
        this._qtaArt = 1;
        this.modqta.setText(Integer.toString(this._qtaArt));
        return localfresul;
      }
      catch (Exception paramArticoliComanda)
      {
        ArticoliComanda localArticoliComanda;
        int i;
        localfresul.result = -1;
        localfresul.errMesg = paramArticoliComanda.getMessage();
        Log.e("AggiungiArticolo comanda:", paramArticoliComanda.getMessage());
      }
      localArticoliComanda = new ArticoliComanda();
      localArticoliComanda.setCodArt(paramArticoliComanda.getCodArt());
      localArticoliComanda.setAlfaArt(paramArticoliComanda.getAlfaArt());
      localArticoliComanda.setPrezzoArt(paramArticoliComanda.getPrezzoArt());
      localArticoliComanda.setIvaArt(paramArticoliComanda.getIvaArt());
      localArticoliComanda.setQta(paramArticoliComanda.getQta());
      i = 0;
      if (i < paramArticoliComanda.getElencoVarianti().size())
      {
        localArticoliComanda.AddLinkVariante((String)paramArticoliComanda.getElencoVarianti().get(i));
        i += 1;
      }
      else
      {
        this._elencoart.add(localArticoliComanda);
      }
    }
    return localfresul;
  }
  
  public void StartActivitySegue(ArrayList<String> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = Reparti.getAllReparti(this.db);
    while (localCursor.moveToNext())
    {
      String str1 = localCursor.getString(0);
      String str2 = localCursor.getString(1);
      if (!paramArrayList.contains(str1)) {
        localArrayList.add(str2);
      }
    }
    paramArrayList = new Intent(this, VariantiActivity.class);
    paramArrayList.putStringArrayListExtra("alfa_segue", localArrayList);
    startActivityForResult(paramArrayList, 2);
  }
  
  public void StartActivityVarianti(ArrayList<String> paramArrayList)
  {
    Intent localIntent = new Intent(this, VariantiActivity.class);
    localIntent.putStringArrayListExtra("codici_varianti", paramArrayList);
    startActivityForResult(localIntent, 2);
  }
  
  public void VediComanda(boolean paramBoolean)
  {
    Intent localIntent = new Intent();
    localIntent.putParcelableArrayListExtra("elem_comanda", this._elencoart);
    localIntent.putExtra("vedi_comanda", paramBoolean);
    setResult(-1, localIntent);
    finish();
  }
  
  public int getQta()
  {
    return this._qtaArt;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == 0) {}
    while (paramInt2 != -1) {
      return;
    }
    for (;;)
    {
      try
      {
        paramIntent = paramIntent.getStringArrayListExtra("varianti_scelte");
        ArticoliComanda localArticoliComanda = UltimoArticoloVenduto();
        if (paramIntent.size() <= 0) {
          break;
        }
        paramInt1 = 0;
        if (paramInt1 >= paramIntent.size()) {
          break;
        }
        if (paramInt1 != 0) {
          localArticoliComanda.AddLinkVariante((String)paramIntent.get(paramInt1));
        }
      }
      catch (Exception paramIntent)
      {
        Log.e("OrdineActivity - onActivityResult:", paramIntent.toString());
        return;
      }
      paramInt1 += 1;
    }
  }
  
  public void onClick(View paramView) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    setContentView(2130903040);
    this.db = this.databaseHelper.getWritableDatabase();
    this._codReparto = getIntent().getExtras().getString("codice_reparto");
    this.ArticoliListView = ((ListView)findViewById(2131296259));
    CaricaArticoli();
    this.ArticoliListView.setAdapter(this.adap);
    this.ArticoliListView.setItemsCanFocus(false);
    this._elencoart = new ArrayList();
    this._qtaArt = 1;
    this.bQtaPlus = ((Button)findViewById(2131296256));
    this.bQtaPlus.setOnClickListener(this.gestore);
    this.bQtaMinus = ((Button)findViewById(2131296258));
    this.bQtaMinus.setOnClickListener(this.gestore);
    this.modqta = ((TextView)findViewById(2131296257));
    this.modqta.setText(Integer.toString(this._qtaArt));
    this.bVediComanda = ((Button)findViewById(2131296266));
    this.bVediComanda.setOnClickListener(this.gestore);
    this.bAnnullaArticoli = ((Button)findViewById(2131296267));
    this.bAnnullaArticoli.setOnClickListener(this.gestore);
    this.bVediReparti = ((Button)findViewById(2131296264));
    this.bVediReparti.setOnClickListener(this.gestore);
    this.bAggVarianti = ((Button)findViewById(2131296261));
    this.bAggVarianti.setOnClickListener(this.gestore);
    this.bAggSegue = ((Button)findViewById(2131296263));
    this.bAggSegue.setOnClickListener(this.gestore);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.db.close();
    this.databaseHelper.close();
    if (this.t != null) {
      this.t.cancel();
    }
  }
  
  private static class ArticoliAdapter
    extends BaseAdapter
  {
    private ArticoliActivity _act;
    private ArticoliXML[] articoli;
    private Context context;
    View.OnClickListener gestorenoramli = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          paramAnonymousView = ((String)paramAnonymousView.getTag()).split("\\|");
          if (paramAnonymousView.length >= 3)
          {
            ArticoliComanda localArticoliComanda = new ArticoliComanda();
            localArticoliComanda.setCodArt(paramAnonymousView[0]);
            localArticoliComanda.setAlfaArt(paramAnonymousView[1]);
            localArticoliComanda.setPrezzoArtInt(Integer.parseInt(paramAnonymousView[2]));
            localArticoliComanda.setIvaArtInt(Integer.parseInt(paramAnonymousView[3]));
            localArticoliComanda.setQta(ArticoliActivity.this.getQta());
            ArticoliActivity.this.AccettaDati(localArticoliComanda, false);
          }
          return;
        }
        catch (Exception paramAnonymousView)
        {
          Log.e("gestorenoramli:", paramAnonymousView.getMessage());
        }
      }
    };
    private LayoutInflater mInflater;
    
    public ArticoliAdapter(Context paramContext, ArticoliXML[] paramArrayOfArticoliXML, ArticoliActivity paramArticoliActivity)
    {
      this.articoli = paramArrayOfArticoliXML;
      this.context = paramContext;
      this._act = paramArticoliActivity;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public int getCount()
    {
      return this.articoli.length;
    }
    
    public Object getItem(int paramInt)
    {
      return this.articoli[paramInt];
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
        int i;
        try
        {
          paramViewGroup = this.mInflater.inflate(2130903050, null);
          paramView = paramViewGroup;
          localObject = new ViewHolder();
          paramView = paramViewGroup;
          ((ViewHolder)localObject).textLine = ((TextView)paramViewGroup.findViewById(2131296324));
          paramView = paramViewGroup;
          paramViewGroup.setTag(localObject);
          paramView = paramViewGroup;
          ((ViewHolder)localObject).textLine.setText(this.articoli[paramInt].getAlfaArt());
          paramView = paramViewGroup;
          ((ViewHolder)localObject).textLine.setTag(this.articoli[paramInt].getCodArt() + "|" + this.articoli[paramInt].getAlfaArt() + "|" + Integer.toString(this.articoli[paramInt].getPrezzoArtInt()) + "|" + Integer.toString(this.articoli[paramInt].getIvaArtInt()));
          paramView = paramViewGroup;
          ((ViewHolder)localObject).textLine.setOnClickListener(this.gestorenoramli);
          paramView = paramViewGroup;
          StringBuilder localStringBuilder = new StringBuilder();
          i = 0;
          paramView = paramViewGroup;
          localObject = paramViewGroup;
          if (i >= this.articoli[paramInt].getElencoVarianti().size()) {
            break label299;
          }
          paramView = paramViewGroup;
          localStringBuilder.append((String)this.articoli[paramInt].getElencoVarianti().get(i));
          paramView = paramViewGroup;
          if (i + 1 >= this.articoli[paramInt].getElencoVarianti().size()) {
            break label302;
          }
          paramView = paramViewGroup;
          localStringBuilder.append("|");
        }
        catch (Exception paramViewGroup)
        {
          Log.e("ArticoliAdapter - getView:", paramViewGroup.getMessage());
          localObject = paramView;
        }
        paramView = paramViewGroup;
        Object localObject = (ViewHolder)paramViewGroup.getTag();
        continue;
        label299:
        return (View)localObject;
        label302:
        i += 1;
      }
    }
    
    static class ViewHolder
    {
      TextView textLine;
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\ArticoliActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */