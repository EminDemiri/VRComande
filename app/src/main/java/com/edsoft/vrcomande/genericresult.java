package com.edsoft.vrcomande;

import com.edsoft.vrcomande.core.dbutility.fresul;

public class genericresult
  extends fresul
{
  public Object Dati;
  
  public genericresult(int paramInt, Object paramObject, String paramString)
  {
    super(paramInt, paramString);
    this.Dati = paramObject;
  }
}


/* Location:              C:\android\tool\dex2jar\VROrdina-dex2jar.jar!\novarum\risto\vrordina\genericresult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */