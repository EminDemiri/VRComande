package com.edsoft.vrcomande.core.scontrin;

import android.util.Log;
import android.util.Xml;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.dbutility.fresul;
import org.xmlpull.v1.XmlSerializer;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class comanda {
    String _alfaOperatore = "";
    String _codiceOperatore = "";
    ArrayList<ArticoliComanda> _elencoart = new ArrayList();
    String _idComanda = "";
    String _idTerminale = "";
    String _nominativo_preno = "";
    int _numeroCoperti = 0;
    int _numeroSala = 0;
    int _numeroTavolo = 0;
    String _orario_oreno = "";

    public comanda() {}

    public comanda(int paramInt1, int paramInt2, String paramString1, String paramString2, int paramInt3, String paramString3, ArticoliComanda[] paramArrayOfArticoliComanda, String paramString4, String paramString5, String paramString6)
    {
        this();
        this._numeroTavolo = paramInt1;
        this._numeroSala = paramInt2;
        this._codiceOperatore = paramString1;
        this._alfaOperatore = paramString2;
        this._elencoart.clear();
        this._idTerminale = paramString3;
        this._idComanda = paramString4;
        this._nominativo_preno = paramString5;
        this._orario_oreno = paramString6;
        paramInt1 = 0;
        while (paramInt1 < paramArrayOfArticoliComanda.length)
        {
            AggiungiArticolo(paramArrayOfArticoliComanda[paramInt1]);
            paramInt1 += 1;
        }
    }

    public fresul AggiungiArticolo(ArticoliComanda paramArticoliComanda)
    {
        fresul localfresul = new fresul(0, "");
        try
        {
            ArticoliComanda localArticoliComanda = new ArticoliComanda();
            localArticoliComanda.setCodArt(paramArticoliComanda.getCodArt());
            localArticoliComanda.setAlfaArt(paramArticoliComanda.getAlfaArt());
            localArticoliComanda.setPrezzoArt(paramArticoliComanda.getPrezzoArt());
            localArticoliComanda.setIvaArt(paramArticoliComanda.getIvaArt());
            localArticoliComanda.setQta(paramArticoliComanda.getQta());
            int i = 0;
            while (i < paramArticoliComanda.getElencoVarianti().size())
            {
                localArticoliComanda.AddLinkVariante((String)paramArticoliComanda.getElencoVarianti().get(i));
                i += 1;
            }
            this._elencoart.add(localArticoliComanda);
            return localfresul;
        }
        catch (Exception paramArticoliComanda)
        {
            localfresul.result = -1;
            localfresul.errMesg = paramArticoliComanda.getMessage();
            Log.e("AggiungiArticolo comanda:", paramArticoliComanda.getMessage());
        }
        return localfresul;
    }

    public void RimuoviArticolo(ArticoliComanda paramArticoliComanda)
    {
        try
        {
            if (this._elencoart.contains(paramArticoliComanda)) {
                this._elencoart.remove(paramArticoliComanda);
            }
            return;
        }
        catch (Exception paramArticoliComanda)
        {
            Log.e("RimuoviArticolo comanda:", paramArticoliComanda.getMessage());
        }
    }

    public streamoutresult ToXml()
    {
        Object localObject1 = new ByteArrayOutputStream();
        Object localObject2 = Xml.newSerializer();
        try
        {
            ((XmlSerializer)localObject2).setOutput((OutputStream)localObject1, "ASCII");
            ((XmlSerializer)localObject2).startDocument(null, Boolean.valueOf(true));
            ((XmlSerializer)localObject2).setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            ((XmlSerializer)localObject2).startTag(null, "comanda");
            ((XmlSerializer)localObject2).startTag(null, "info");
            ((XmlSerializer)localObject2).startTag(null, "idTerminale");
            ((XmlSerializer)localObject2).text(this._idTerminale);
            ((XmlSerializer)localObject2).endTag(null, "idTerminale");
            ((XmlSerializer)localObject2).startTag(null, "sala");
            ((XmlSerializer)localObject2).text(Integer.toString(this._numeroSala));
            ((XmlSerializer)localObject2).endTag(null, "sala");
            ((XmlSerializer)localObject2).startTag(null, "tavolo");
            ((XmlSerializer)localObject2).text(Integer.toString(this._numeroTavolo));
            ((XmlSerializer)localObject2).endTag(null, "tavolo");
            ((XmlSerializer)localObject2).startTag(null, "coperti");
            ((XmlSerializer)localObject2).text(Integer.toString(this._numeroCoperti));
            ((XmlSerializer)localObject2).endTag(null, "coperti");
            ((XmlSerializer)localObject2).startTag(null, "IDComanda");
            ((XmlSerializer)localObject2).text(this._idComanda);
            ((XmlSerializer)localObject2).endTag(null, "IDComanda");
            ((XmlSerializer)localObject2).startTag(null, "NominativoPreno");
            ((XmlSerializer)localObject2).text(this._nominativo_preno);
            ((XmlSerializer)localObject2).endTag(null, "NominativoPreno");
            ((XmlSerializer)localObject2).startTag(null, "OrarioPreno");
            ((XmlSerializer)localObject2).text(this._orario_oreno);
            ((XmlSerializer)localObject2).endTag(null, "OrarioPreno");
            ((XmlSerializer)localObject2).startTag(null, "operatore");
            ((XmlSerializer)localObject2).attribute(null, "codice", this._codiceOperatore);
            ((XmlSerializer)localObject2).text(this._alfaOperatore);
            ((XmlSerializer)localObject2).endTag(null, "operatore");
            ((XmlSerializer)localObject2).endTag(null, "info");
            if (this._elencoart.size() > 0)
            {
                ((XmlSerializer)localObject2).startTag(null, "ElencoArticoli");
                int i = 0;
                while (i < this._elencoart.size())
                {
                    ((XmlSerializer)localObject2).startTag(null, "articolo");
                    ((XmlSerializer)localObject2).attribute(null, "codice", ((ArticoliComanda)this._elencoart.get(i)).getCodArt());
                    ((XmlSerializer)localObject2).attribute(null, "qta", Integer.toString(((ArticoliComanda)this._elencoart.get(i)).getQta()));
                    ((XmlSerializer)localObject2).attribute(null, "alfa", ((ArticoliComanda)this._elencoart.get(i)).getAlfaArt());
                    ((XmlSerializer)localObject2).attribute(null, "prezzo", Integer.toString(((ArticoliComanda)this._elencoart.get(i)).getPrezzoArtInt()));
                    ((XmlSerializer)localObject2).attribute(null, "iva", Integer.toString(((ArticoliComanda)this._elencoart.get(i)).getIvaArtInt()));
                    if (((ArticoliComanda)this._elencoart.get(i)).getElencoVarianti().size() > 0)
                    {
                        ((XmlSerializer)localObject2).startTag(null, "varianti");
                        int j = 0;
                        while (j < ((ArticoliComanda)this._elencoart.get(i)).getElencoVarianti().size())
                        {
                            ((XmlSerializer)localObject2).startTag(null, "variante");
                            String[] arrayOfString = ((String)((ArticoliComanda)this._elencoart.get(i)).getElencoVarianti().get(j)).split("\\|");
                            ((XmlSerializer)localObject2).attribute(null, "codice", arrayOfString[0]);
                            ((XmlSerializer)localObject2).attribute(null, "prezzo", arrayOfString[2]);
                            ((XmlSerializer)localObject2).text(arrayOfString[1]);
                            ((XmlSerializer)localObject2).endTag(null, "variante");
                            j += 1;
                        }
                        ((XmlSerializer)localObject2).endTag(null, "varianti");
                    }
                    ((XmlSerializer)localObject2).endTag(null, "articolo");
                    i += 1;
                }
                ((XmlSerializer)localObject2).endTag(null, "ElencoArticoli");
            }
            ((XmlSerializer)localObject2).endTag(null, "comanda");
            ((XmlSerializer)localObject2).endDocument();
            ((XmlSerializer)localObject2).flush();
            ((ByteArrayOutputStream)localObject1).close();
            localObject2 = new streamoutresult(0, (OutputStream)localObject1, "");
            return (streamoutresult)localObject2;
        }
        catch (Exception localException)
        {
            localObject1 = new streamoutresult(-1, (OutputStream)localObject1, localException.getMessage());
            Log.e("Exception", "Exception occured in wroting");
        }
        return (streamoutresult)localObject1;
    }

    public String getAlfaOperatore()
    {
        return this._alfaOperatore;
    }

    public String getCodiceOperatore()
    {
        return this._codiceOperatore;
    }

    public String getIdComanda()
    {
        return this._idComanda;
    }

    public String getIdTerminale()
    {
        return this._idTerminale;
    }

    public String getNominativoPreno()
    {
        return this._nominativo_preno;
    }

    public int getNumeroCoperti()
    {
        return this._numeroCoperti;
    }

    public int getNumeroSala()
    {
        return this._numeroSala;
    }

    public int getNumeroTavolo()
    {
        return this._numeroTavolo;
    }

    public String getOrarioPreno()
    {
        return this._orario_oreno;
    }

    public void setAlfaOperatore(String paramString)
    {
        this._alfaOperatore = paramString;
    }

    public void setCodiceOperatore(String paramString)
    {
        this._codiceOperatore = paramString;
    }

    public void setIdComanda(String paramString)
    {
        this._idComanda = paramString;
    }

    public void setIdTerminale(String paramString)
    {
        this._idTerminale = paramString;
    }

    public void setNominativoPreno(String paramString)
    {
        this._nominativo_preno = paramString;
    }

    public void setNumeroCoperti(int paramInt)
    {
        this._numeroCoperti = paramInt;
    }

    public void setNumeroSala(int paramInt)
    {
        this._numeroSala = paramInt;
    }

    public void setNumeroTavolo(int paramInt)
    {
        this._numeroTavolo = paramInt;
    }

    public void setOrarioPreno(String paramString)
    {
        this._orario_oreno = paramString;
    }
}
