package com.edsoft.vrcomande.core.scontrin;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import com.edsoft.vrcomande.core.networkutility.ArticoliXML;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class ArticoliComanda
        extends ArticoliXML
        implements Parcelable
{
    public static final Parcelable.Creator<ArticoliComanda> CREATOR = new Parcelable.Creator()
    {
        public ArticoliComanda createFromParcel(Parcel paramAnonymousParcel)
        {
            return new ArticoliComanda(paramAnonymousParcel);
        }

        public ArticoliComanda[] newArray(int paramAnonymousInt)
        {
            return new ArticoliComanda[paramAnonymousInt];
        }
    };
    private boolean _getchecked = false;
    int _qtaInComanda = 0;

    public ArticoliComanda() {}

    public ArticoliComanda(Parcel paramParcel)
    {
        this();
        readFromParcel(paramParcel);
    }

    private void readFromParcel(Parcel paramParcel)
    {
        this.codart = paramParcel.readString();
        this.alfaart = paramParcel.readString();
        this.prezzoart = paramParcel.readDouble();
        this.ivaart = paramParcel.readDouble();
        this._qtaInComanda = paramParcel.readInt();
        paramParcel.readStringList(this.linkvarianti);
    }

    public int describeContents()
    {
        return 0;
    }

    public boolean equals(Object paramObject)
    {
        boolean bool1;
        if (this == paramObject) {
            bool1 = true;
        }
        String str2;
        String str4;
        do
        {
            String str1;
            String str3;
            do
            {
                return bool1;
                if ((paramObject == null) || (paramObject.getClass() != getClass())) {
                    return false;
                }
                paramObject = (ArticoliComanda)paramObject;
                bool2 = false;
                str1 = super.getCodArt();
                str2 = super.getAlfaArt();
                str3 = ((ArticoliComanda)paramObject).getCodArt();
                str4 = ((ArticoliComanda)paramObject).getAlfaArt();
                bool1 = bool2;
            } while (str1.compareTo(str3) != 0);
            bool1 = bool2;
        } while (str2.compareTo(str4) != 0);
        boolean bool2 = true;
        if (super.getElencoVarianti().size() != ((ArticoliComanda)paramObject).getElencoVarianti().size()) {
            return false;
        }
        int i = 0;
        for (;;)
        {
            bool1 = bool2;
            if (i >= super.getElencoVarianti().size()) {
                break;
            }
            if (((String)super.getElencoVarianti().get(i)).compareTo((String)((ArticoliComanda)paramObject).getElencoVarianti().get(i)) != 0) {
                return false;
            }
            i += 1;
        }
    }

    public boolean getChecked()
    {
        return this._getchecked;
    }

    public int getQta()
    {
        return this._qtaInComanda;
    }

    public int hashCode()
    {
        int j = super.getCodArt().hashCode();
        if (super.getAlfaArt() == null) {}
        for (int i = 0;; i = super.getAlfaArt().hashCode())
        {
            j = (j + 217) * 31 + i;
            i = 0;
            while (i < super.getElencoVarianti().size())
            {
                j = j * 31 + ((String)super.getElencoVarianti().get(i)).hashCode();
                i += 1;
            }
        }
        return j;
    }

    public void setChecked(boolean paramBoolean)
    {
        this._getchecked = paramBoolean;
    }

    public void setQta(int paramInt)
    {
        this._qtaInComanda = paramInt;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.codart);
        paramParcel.writeString(this.alfaart);
        paramParcel.writeDouble(this.prezzoart);
        paramParcel.writeDouble(this.ivaart);
        paramParcel.writeInt(this._qtaInComanda);
        paramParcel.writeStringList(this.linkvarianti);
    }
}

