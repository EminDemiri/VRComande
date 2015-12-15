package com.edsoft.vrcomande.core.networkutility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class ArticoliXML {
    protected String alfaart = "";
    protected String codart = "";
    protected double ivaart = 0.0D;
    protected ArrayList<String> linkvarianti = new ArrayList();
    protected int posizione = 0;
    protected double prezzoart = 0.0D;

    public void AddLinkVariante(String paramString)
    {
        this.linkvarianti.add(paramString);
    }

    public void AddLinkVariante(String[] paramArrayOfString)
    {
        Collections.addAll(this.linkvarianti, paramArrayOfString);
    }

    public String getAlfaArt()
    {
        return this.alfaart;
    }

    public String getCodArt()
    {
        return this.codart;
    }

    public ArrayList<String> getElencoVarianti()
    {
        return this.linkvarianti;
    }

    public double getIvaArt()
    {
        return this.ivaart;
    }

    public int getIvaArtInt()
    {
        return (int)(this.ivaart * 100.0D);
    }

    public int getPosizione()
    {
        return this.posizione;
    }

    public double getPrezzoArt()
    {
        return this.prezzoart;
    }

    public int getPrezzoArtInt()
    {
        return (int)(this.prezzoart * 100.0D);
    }

    public void setAlfaArt(String paramString)
    {
        this.alfaart = paramString;
    }

    public void setCodArt(String paramString)
    {
        this.codart = paramString;
    }

    public void setElencoVarianti(ArrayList<String> paramArrayList)
    {
        this.linkvarianti = paramArrayList;
    }

    public void setIvaArt(double paramDouble)
    {
        this.ivaart = paramDouble;
    }

    public void setIvaArtInt(int paramInt)
    {
        this.ivaart = (paramInt / 100.0D);
    }

    public void setPosizione(int paramInt)
    {
        this.posizione = paramInt;
    }

    public void setPrezzoArt(double paramDouble)
    {
        this.prezzoart = paramDouble;
    }

    public void setPrezzoArtInt(int paramInt)
    {
        this.prezzoart = (paramInt / 100.0D);
    }

    public String toString()
    {
        return "ArticoliXML [codart=" + this.codart + ", alfaart=" + this.alfaart + ", prezzoart=" + this.prezzoart + ", ivaart=" + this.ivaart + "]";
    }
}
