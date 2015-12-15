package com.edsoft.vrcomande.core.networkutility;

import java.util.ArrayList;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class RepartiXML {
    private String alfarep = "";
    private String codrep = "";
    private ArrayList<ArticoliXML> elencoart = new ArrayList();
    private int posizione = 0;

    public void AddArt(ArticoliXML paramArticoliXML)
    {
        this.elencoart.add(paramArticoliXML);
    }

    public String getAlfaRep()
    {
        return this.alfarep;
    }

    public String getCodRep()
    {
        return this.codrep;
    }

    public ArrayList<ArticoliXML> getElencoArt()
    {
        return this.elencoart;
    }

    public int getPosizione()
    {
        return this.posizione;
    }

    public void setAlfaRep(String paramString)
    {
        this.alfarep = paramString;
    }

    public void setCodRep(String paramString)
    {
        this.codrep = paramString;
    }

    public void setElencoArt(ArrayList<ArticoliXML> paramArrayList)
    {
        this.elencoart = paramArrayList;
    }

    public void setPosizione(int paramInt)
    {
        this.posizione = paramInt;
    }

    public String toString()
    {
        return "RepartiXML [codrep=" + this.codrep + ", alfarep=" + this.alfarep + "]";
    }
}
