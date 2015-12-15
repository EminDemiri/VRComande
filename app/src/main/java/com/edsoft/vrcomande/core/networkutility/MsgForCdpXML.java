package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class MsgForCdpXML {
    protected String codCDP = "";
    protected String codMSG = "";
    protected String testoMSG = "";

    public String getCodCDP()
    {
        return this.codCDP;
    }

    public String getCodMSG()
    {
        return this.codMSG;
    }

    public String getTestoMSG()
    {
        return this.testoMSG;
    }

    public void setCodCDP(String paramString)
    {
        this.codCDP = paramString;
    }

    public void setCodMSG(String paramString)
    {
        this.codMSG = paramString;
    }

    public void setTestoMSG(String paramString)
    {
        this.testoMSG = paramString;
    }

    public String toString()
    {
        return "InfoCdpXML [codMSF=" + this.codMSG + ", testoMSG=" + this.testoMSG + ", codCDP=" + this.codCDP + "]";
    }
}
