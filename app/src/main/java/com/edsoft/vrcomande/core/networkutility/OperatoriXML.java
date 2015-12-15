package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class OperatoriXML {
    protected String _alfaoperatore = "";
    protected String _codice = "";
    protected String _login = "";
    protected String _password = "";

    public String getAlfaOp()
    {
        return this._alfaoperatore;
    }

    public String getCodOp()
    {
        return this._codice;
    }

    public String getLoginOp()
    {
        return this._login;
    }

    public String getPwdOp()
    {
        return this._password;
    }

    public void setAlfaOp(String paramString)
    {
        this._alfaoperatore = paramString;
    }

    public void setCodOp(String paramString)
    {
        this._codice = paramString;
    }

    public void setLoginOp(String paramString)
    {
        this._login = paramString;
    }

    public void setPwdOp(String paramString)
    {
        this._password = paramString;
    }
}
