package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class InfoCdpXML {
    protected String alfaCDP = "";
    protected String codCDP = "";

    public String getAlfaCDP()
    {
        return this.alfaCDP;
    }

    public String getCodCDP()
    {
        return this.codCDP;
    }

    public void setAlfaCDP(String paramString)
    {
        this.alfaCDP = paramString;
    }

    public void setCodCDP(String paramString)
    {
        this.codCDP = paramString;
    }

    public String toString()
    {
        return "InfoCdpXML [codCDP=" + this.codCDP + ", alfaCDP=" + this.alfaCDP + "]";
    }
}
