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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.DBHelper;
import com.edsoft.vrcomande.core.dbutility.VariantiArticoli;
import com.edsoft.vrcomande.core.networkutility.VariantiXML;
import com.edsoft.vrcomande.core.scontrin.VariantiComanda;

public class VariantiActivity
  extends Activity
  implements View.OnClickListener
{
  static final String cod_variante_libera = "vlibera";
  static final String cod_variante_msgcdp = "vmsgcdp";
  String _AlfaCdp;
  String _CdpMsg;
  boolean _InserisciMessaggio;
  String _alfaArt;
  ArrayList<String> _alfaSegue;
  String _codArt;
  ArrayList<String> _codVarianti;
  int _ivaart;
  ArrayList<String> _msgPredef;
  ArrayList<String> _ordinamentoDiSelezione = new ArrayList();
  int _prezzoArt;
  private VariantiAdapter adap;
  private Button bAccettaVarianti;
  private Button bAnnullaVarianti;
  private Button bVarianteLibera;
  DBHelper databaseHelper = new DBHelper(this);
  SQLiteDatabase db;
  private ListView elencovarianti;
  View.OnClickListener gestore = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      try
      {
        switch (paramAnonymousView.getId())
        {
        case 2131296314: 
          VariantiActivity.this.SettaRisultato();
          return;
        }
      }
      catch (Exception paramAnonymousView)
      {
        Log.e("bulkInsert:", paramAnonymousView.getMessage());
        return;
      }
      VariantiActivity.this.ChiudiActivity();
      return;
      VariantiActivity.this.InserisciVarianteLibera();
      return;
    }
  };
  
  private void CaricaMessaggi()
  {
    try
    {
      this._InserisciMessaggio = true;
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      while (i < this._msgPredef.size())
      {
        VariantiComanda localVariantiComanda = new VariantiComanda();
        localVariantiComanda.setCodVariante("vmsgcdp");
        localVariantiComanda.setAlfaVariante((String)this._msgPredef.get(i));
        localVariantiComanda.setPrezzoVariante(0.0D);
        localVariantiComanda.setChecked(false);
        localArrayList.add(localVariantiComanda);
        i += 1;
      }
      this.adap = new VariantiAdapter(this, localArrayList, this);
      return;
    }
    catch (Exception localException)
    {
      Log.e("CaricaMessaggi:", localException.toString());
    }
  }
  
  private void CaricaSegue()
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      while (i < this._alfaSegue.size())
      {
        VariantiComanda localVariantiComanda = new VariantiComanda();
        localVariantiComanda.setCodVariante("vlibera");
        localVariantiComanda.setAlfaVariante("Segue: " + (String)this._alfaSegue.get(i));
        localVariantiComanda.setPrezzoVariante(0.0D);
        localVariantiComanda.setChecked(false);
        localArrayList.add(localVariantiComanda);
        i += 1;
      }
      this.adap = new VariantiAdapter(this, localArrayList, this);
      return;
    }
    catch (Exception localException)
    {
      Log.e("CaricaReparti:", localException.toString());
    }
  }
  
  private void CaricaVarianti()
  {
    for (;;)
    {
      int i;
      Object localObject1;
      Object localObject2;
      try
      {
        this._codArt = "";
        this._alfaArt = "";
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i >= this._codVarianti.size()) {
          break label267;
        }
        switch (i)
        {
        case 0: 
          localObject1 = new VariantiComanda();
          localObject2 = (String)this._codVarianti.get(i);
          Cursor localCursor = VariantiArticoli.getVariante(this.db, (String)localObject2);
          if (!localCursor.moveToNext()) {
            break label240;
          }
          ((VariantiComanda)localObject1).setCodVariante((String)localObject2);
          ((VariantiComanda)localObject1).setAlfaVariante(localCursor.getString(1));
          ((VariantiComanda)localObject1).setPrezzoVariante(localCursor.getDouble(2));
          ((VariantiComanda)localObject1).setChecked(false);
          continue;
          this._codArt = ((String)this._codVarianti.get(i));
        }
      }
      catch (Exception localException)
      {
        Log.e("CaricaReparti:", localException.toString());
        return;
      }
      break label375;
      this._alfaArt = ((String)this._codVarianti.get(i));
      break label375;
      this._prezzoArt = Integer.parseInt((String)this._codVarianti.get(i));
      break label375;
      this._ivaart = Integer.parseInt((String)this._codVarianti.get(i));
      break label375;
      label240:
      if ((((VariantiComanda)localObject1).getCodVariante() != "") && (((VariantiComanda)localObject1).getAlfaVariante() != ""))
      {
        localException.add(localObject1);
        break label375;
        label267:
        localObject1 = VariantiArticoli.getVariantiPerTutti(this.db);
        while (((Cursor)localObject1).moveToNext())
        {
          localObject2 = new VariantiComanda();
          ((VariantiComanda)localObject2).setCodVariante(((Cursor)localObject1).getString(0));
          ((VariantiComanda)localObject2).setAlfaVariante(((Cursor)localObject1).getString(1));
          ((VariantiComanda)localObject2).setPrezzoVariante(((Cursor)localObject1).getDouble(2));
          ((VariantiComanda)localObject2).setChecked(false);
          if ((((VariantiComanda)localObject2).getCodVariante() != "") && (((VariantiComanda)localObject2).getAlfaVariante() != "")) {
            localException.add(localObject2);
          }
        }
        this.adap = new VariantiAdapter(this, localException, this);
        return;
        continue;
      }
      label375:
      i += 1;
    }
  }
  
  private void ChiudiActivity()
  {
    setResult(0, new Intent());
    finish();
  }
  
  private void InserisciVarianteLibera()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Variante libera");
    localBuilder.setMessage("Digita la descrizione per la variante");
    final EditText localEditText = new EditText(this);
    localBuilder.setView(localEditText);
    localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = localEditText.getText().toString();
        VariantiComanda localVariantiComanda = new VariantiComanda();
        if (!VariantiActivity.this._InserisciMessaggio) {
          localVariantiComanda.setCodVariante("vlibera");
        }
        for (;;)
        {
          localVariantiComanda.setAlfaVariante(paramAnonymousDialogInterface);
          localVariantiComanda.setPrezzoVariante(0.0D);
          localVariantiComanda.setChecked(true);
          VariantiActivity.this.adap.add(localVariantiComanda);
          if (!VariantiActivity.this._ordinamentoDiSelezione.contains(paramAnonymousDialogInterface)) {
            VariantiActivity.this._ordinamentoDiSelezione.add(paramAnonymousDialogInterface);
          }
          return;
          localVariantiComanda.setCodVariante("vmsgcdp");
        }
      }
    });
    localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
    localBuilder.show();
  }
  
  private void SettaRisultato()
  {
    ArrayList localArrayList = new ArrayList();
    int k = this.elencovarianti.getAdapter().getCount();
    if (k > 0) {
      localArrayList.add(this._codArt + "|" + this._alfaArt + "|" + Integer.toString(this._prezzoArt) + "|" + Integer.toString(this._ivaart));
    }
    int i = 0;
    Object localObject1;
    if (i < this._ordinamentoDiSelezione.size())
    {
      int j = 0;
      for (;;)
      {
        Object localObject2;
        String str;
        if (j < k)
        {
          localObject1 = this.elencovarianti.getAdapter().getView(j, null, null);
          if ((localObject1 == null) || (!((CheckedTextView)((View)localObject1).findViewById(2131296331)).isChecked())) {
            break label342;
          }
          localObject2 = (VariantiXML)this.elencovarianti.getItemAtPosition(j);
          localObject1 = ((VariantiXML)localObject2).getCodVariante();
          str = ((VariantiXML)localObject2).getAlfaVariante();
          localObject2 = Integer.toString(((VariantiXML)localObject2).getPrezzoVarianteInt());
          if ((localObject1 == "") || (str.compareTo((String)this._ordinamentoDiSelezione.get(i)) != 0)) {
            break label342;
          }
          if (!this._InserisciMessaggio) {
            break label299;
          }
          localArrayList.add((String)localObject1 + "|" + str + "|" + (String)localObject2 + "|" + this._CdpMsg + "|" + this._AlfaCdp);
        }
        for (;;)
        {
          i += 1;
          break;
          label299:
          localArrayList.add((String)localObject1 + "|" + str + "|" + (String)localObject2);
        }
        label342:
        j += 1;
      }
    }
    if (localArrayList.size() > 0)
    {
      localObject1 = new Intent();
      ((Intent)localObject1).putStringArrayListExtra("varianti_scelte", localArrayList);
      setResult(-1, (Intent)localObject1);
      finish();
    }
  }
  
  public void onClick(View paramView) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.db = this.databaseHelper.getWritableDatabase();
    setContentView(2130903046);
    this._codVarianti = getIntent().getExtras().getStringArrayList("codici_varianti");
    this._alfaSegue = getIntent().getExtras().getStringArrayList("alfa_segue");
    this._CdpMsg = getIntent().getExtras().getString("cdp_msg");
    this._AlfaCdp = getIntent().getExtras().getString("alfa_cdp");
    this._msgPredef = getIntent().getExtras().getStringArrayList("msg_predef");
    this.elencovarianti = ((ListView)findViewById(2131296312));
    this._InserisciMessaggio = false;
    if (this._alfaSegue == null) {
      if (this._CdpMsg == null) {
        CaricaVarianti();
      }
    }
    for (;;)
    {
      this.elencovarianti.setAdapter(this.adap);
      this.elencovarianti.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          paramAnonymousAdapterView = (VariantiComanda)VariantiActivity.this.adap.getItem(paramAnonymousInt);
          paramAnonymousView = (CheckedTextView)paramAnonymousView.findViewById(2131296331);
          String str = paramAnonymousAdapterView.getAlfaVariante();
          if (!paramAnonymousView.isChecked())
          {
            paramAnonymousView.setChecked(true);
            if (!VariantiActivity.this._ordinamentoDiSelezione.contains(str)) {
              VariantiActivity.this._ordinamentoDiSelezione.add(str);
            }
          }
          for (;;)
          {
            paramAnonymousAdapterView.setChecked(paramAnonymousView.isChecked());
            return;
            paramAnonymousView.setChecked(false);
            if (VariantiActivity.this._ordinamentoDiSelezione.contains(str)) {
              VariantiActivity.this._ordinamentoDiSelezione.remove(str);
            }
          }
        }
      });
      this.bAccettaVarianti = ((Button)findViewById(2131296314));
      this.bAccettaVarianti.setOnClickListener(this.gestore);
      this.bAnnullaVarianti = ((Button)findViewById(2131296316));
      this.bAnnullaVarianti.setOnClickListener(this.gestore);
      this.bVarianteLibera = ((Button)findViewById(2131296311));
      this.bVarianteLibera.setOnClickListener(this.gestore);
      return;
      CaricaMessaggi();
      continue;
      CaricaSegue();
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.db.close();
    this.databaseHelper.close();
  }
  
  private static class VariantiAdapter
    extends BaseAdapter
  {
    private VariantiActivity _act;
    private Context context;
    private LayoutInflater mInflater;
    ArrayList<VariantiComanda> varianti;
    
    public VariantiAdapter(Context paramContext, ArrayList<VariantiComanda> paramArrayList, VariantiActivity paramVariantiActivity)
    {
      this.varianti = paramArrayList;
      this.context = paramContext;
      this._act = paramVariantiActivity;
      this.mInflater = LayoutInflater.from(this.context);
    }
    
    public void add(VariantiComanda paramVariantiComanda)
    {
      this.varianti.add(paramVariantiComanda);
      notifyDataSetChanged();
    }
    
    public int getCount()
    {
      return this.varianti.size();
    }
    
    public Object getItem(int paramInt)
    {
      return this.varianti.get(paramInt);
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
          paramViewGroup = this.mInflater.inflate(2130903054, null);
          paramView = paramViewGroup;
          localViewHolder = new ViewHolder();
          paramView = paramViewGroup;
          localViewHolder.dataLine = ((CheckedTextView)paramViewGroup.findViewById(2131296331));
          paramView = paramViewGroup;
          paramViewGroup.setTag(localViewHolder);
          paramView = paramViewGroup;
          localViewHolder.dataLine.setText(((VariantiComanda)this.varianti.get(paramInt)).getAlfaVariante());
          paramView = paramViewGroup;
          localViewHolder.dataLine.setTag(((VariantiComanda)this.varianti.get(paramInt)).getCodVariante() + "|" + ((VariantiComanda)this.varianti.get(paramInt)).getAlfaVariante() + "|" + Integer.toString(((VariantiComanda)this.varianti.get(paramInt)).getPrezzoVarianteInt()));
          paramView = paramViewGroup;
          localViewHolder.dataLine.setChecked(((VariantiComanda)this.varianti.get(paramInt)).getChecked());
          return paramViewGroup;
        }
        catch (Exception paramViewGroup)
        {
          ViewHolder localViewHolder;
          Log.e("VariantiAdapter - getView:", paramViewGroup.getMessage());
        }
        paramView = paramViewGroup;
        localViewHolder = (ViewHolder)paramViewGroup.getTag();
      }
      return paramView;
    }
    
    static class ViewHolder
    {
      CheckedTextView dataLine;
    }
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\VariantiActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */