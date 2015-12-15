package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class VariantiXML {
    protected String alfavariante = "";
    protected String codvariante = "";
    protected double prezzovariante = 0.0D;
    protected boolean variantepertutti = false;

    public String getAlfaVariante()
    {
        return this.alfavariante;
    }

    public String getCodVariante()
    {
        return this.codvariante;
    }

    public double getPrezzoVariante()
    {
        return this.prezzovariante;
    }

    public int getPrezzoVarianteInt()
    {
        return (int)(this.prezzovariante * 100.0D);
    }

    public boolean getVariantePerTutti()
    {
        return this.variantepertutti;
    }

    public void setAlfaVariante(String paramString)
    {
        this.alfavariante = paramString;
    }

    public void setCodVariante(String paramString)
    {
        this.codvariante = paramString;
    }

    public void setPrezzoVariante(double paramDouble)
    {
        this.prezzovariante = paramDouble;
    }

    public void setPrezzoVarianteInt(int paramInt)
    {
        this.prezzovariante = (paramInt / 100.0D);
    }

    public void setVariantePerTutti(boolean paramBoolean)
    {
        this.variantepertutti = paramBoolean;
    }

    public String toString()
    {
        return "VariantiXML [codvariante=" + this.codvariante + ", alfavariante=" + this.alfavariante + "]";
    }
}
