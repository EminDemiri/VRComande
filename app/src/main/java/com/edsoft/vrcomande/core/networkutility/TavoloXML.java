package com.edsoft.vrcomande.core.networkutility;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class TavoloXML {
    public static final int LIBERO = 0;
    public static final int OCCUPATO = 1;
    public static final int PRENOTATO = 2;
    public static final int RICHIESTO_CONTO = 3;
    protected int coperti = 0;
    protected String nominativopreno = "";
    protected int numero = 0;
    protected String orariopreno = "";
    protected String sala = "";
    protected int situazione = 0;

    public boolean equals(Object paramObject)
    {
        boolean bool1;
        if (this == paramObject) {
            bool1 = true;
        }
        do
        {
            boolean bool2;
            do
            {
                return bool1;
                if ((paramObject == null) || (paramObject.getClass() != getClass())) {
                    return false;
                }
                paramObject = (TavoloXML)paramObject;
                bool2 = false;
                bool1 = bool2;
            } while (getSala().hashCode() != ((TavoloXML)paramObject).getSala().hashCode());
            bool1 = bool2;
        } while (getNumero() != ((TavoloXML)paramObject).getNumero());
        return true;
    }

    public int getCoperti()
    {
        return this.coperti;
    }

    public int getNumero()
    {
        return this.numero;
    }

    public String getOrarioPrenoFormattato()
    {
        String str1 = "00:00";
        if (getorarioPreno().length() >= 14)
        {
            str1 = getorarioPreno().substring(8, 10);
            String str2 = getorarioPreno().substring(10, 12);
            str1 = str1 + ":" + str2;
        }
        return str1;
    }

    public String getSala()
    {
        return this.sala;
    }

    public int getSituazione()
    {
        return this.situazione;
    }

    public String getnominativoPreno()
    {
        return this.nominativopreno;
    }

    public String getorarioPreno()
    {
        return this.orariopreno;
    }

    public int hashCode()
    {
        return (getSala().hashCode() + 217) * 31 + Integer.toString(getNumero()).hashCode();
    }

    public void setCoperti(int paramInt)
    {
        this.coperti = paramInt;
    }

    public void setNumero(int paramInt)
    {
        this.numero = paramInt;
    }

    public void setSala(String paramString)
    {
        this.sala = paramString;
    }

    public void setSituazione(int paramInt)
    {
        this.situazione = paramInt;
    }

    public void setnominativoPreno(String paramString)
    {
        this.nominativopreno = paramString;
    }

    public void setorarioPreno(String paramString)
    {
        this.orariopreno = paramString;
    }

    public String toString()
    {
        return "TavoloXML [sala=" + this.sala + ", numero tavolo=" + Integer.toString(this.numero) + ", situazione=" + Integer.toString(this.situazione) + ", nominativopreno=" + this.nominativopreno + ", orariopreno=" + this.orariopreno + "]";
    }
}
