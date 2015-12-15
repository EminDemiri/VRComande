package com.edsoft.vrcomande.core.networkutility;

import com.edsoft.vrcomande.core.dbutility.fresul;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class networkresult
    extends fresul
{
    public String Dati;

    public networkresult(int paramInt, String paramString1, String paramString2)
    {
        super(paramInt, paramString2);
        this.Dati = paramString1;
    }
}
