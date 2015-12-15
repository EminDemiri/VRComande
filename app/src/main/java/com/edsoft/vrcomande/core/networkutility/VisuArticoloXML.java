package com.edsoft.vrcomande.core.networkutility;

import java.util.ArrayList;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class VisuArticoloXML {

    protected ArrayList<String> Varianti = new ArrayList();
    protected String alfa = "";
    protected int qta = 0;

    public void AddLinkVariante(String paramString)
    {
        this.Varianti.add(paramString);
    }

    public String getAlfaArticolo()
    {
        return this.alfa;
    }

    public ArrayList<String> getElencoVarianti()
    {
        return this.Varianti;
    }

    public int getQtaArticolo()
    {
        return this.qta;
    }

    public void setAlfaArticolo(String paramString)
    {
        this.alfa = paramString;
    }

    public void setElencoVarianti (ArrayList<String> paramArrayList)
    {
        this.Varianti = paramArrayList;
    }

    public void setQtaArticolo(int paramInt)
    {
        this.qta = paramInt;
    }
}
