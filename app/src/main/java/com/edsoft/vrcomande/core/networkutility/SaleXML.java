package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class SaleXML {
    private String alfasala = "";
    private String codsala = "";
    private int deltatavoli = 0;
    private int numerotavoli = 0;

    public String getAlfaSala()
    {
        return this.alfasala;
    }

    public String getCodSala()
    {
        return this.codsala;
    }

    public int getDeltaTavoli()
    {
        return this.deltatavoli;
    }

    public int getNumeroTavoli()
    {
        return this.numerotavoli;
    }

    public void setAlfaSala(String paramString)
    {
        this.alfasala = paramString;
    }

    public void setCodSala(String paramString)
    {
        this.codsala = paramString;
    }

    public void setDeltaTavoli(int paramInt)
    {
        this.deltatavoli = paramInt;
    }

    public void setNumeroTavoli(int paramInt)
    {
        this.numerotavoli = paramInt;
    }

    public String toString()
    {
        return "RepartiXML [codsala=" + this.codsala + ", alfasala=" + this.alfasala + ", numerotavoli=" + this.numerotavoli + ", deltatavoli=" + this.deltatavoli + "]";
    }
}
