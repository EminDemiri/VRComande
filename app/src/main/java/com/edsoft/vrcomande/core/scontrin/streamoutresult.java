package com.edsoft.vrcomande.core.scontrin;

import com.edsoft.vrcomande.core.dbutility.fresul;

import java.io.OutputStream;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class streamoutresult
        extends fresul
{
    public OutputStream Dati;

    public streamoutresult(int paramInt, OutputStream paramOutputStream, String paramString)
    {
        super(paramInt, paramString);
        this.Dati = paramOutputStream;
    }
}
