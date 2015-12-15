package com.edsoft.vrcomande.core.dbutility;

/**
 * Created by EDSoft on 09/12/2015.
 */
public class fresul {
    public String errMesg;
    public int result;

    public fresul(int paramInt, String paramString)
    {
        this.result = paramInt;
        this.errMesg = paramString;
    }
}
