package com.edsoft.vrcomande.core.scontrin;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.edsoft.vrcomande.core.networkutility.VariantiXML;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class VariantiComanda
        extends VariantiXML
        implements Parcelable
{
    public static final Parcelable.Creator<VariantiComanda> CREATOR = new Parcelable.Creator()
    {
        public VariantiComanda createFromParcel(Parcel paramAnonymousParcel)
        {
            return new VariantiComanda(paramAnonymousParcel);
        }

        public VariantiComanda[] newArray(int paramAnonymousInt)
        {
            return new VariantiComanda[paramAnonymousInt];
        }
    };
    private boolean _getchecked = false;

    public VariantiComanda() {}

    public VariantiComanda(Parcel paramParcel)
    {
        this();
        readFromParcel(paramParcel);
    }

    private void readFromParcel(Parcel paramParcel)
    {
        this.codvariante = paramParcel.readString();
        this.alfavariante = paramParcel.readString();
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
        do
        {
            boolean bool2;
            do
            {
                return bool1;
                if ((paramObject == null) || (paramObject.getClass() != getClass())) {
                    return false;
                }
                paramObject = (VariantiComanda)paramObject;
                bool2 = false;
                bool1 = bool2;
            } while (super.getCodVariante() != ((VariantiComanda)paramObject).getCodVariante());
            bool1 = bool2;
        } while (super.getAlfaVariante() != ((VariantiComanda)paramObject).getAlfaVariante());
        return true;
    }

    public boolean getChecked()
    {
        return this._getchecked;
    }

    public int hashCode()
    {
        int j = super.getCodVariante().hashCode();
        if (super.getAlfaVariante() == null) {}
        for (int i = 0;; i = super.getAlfaVariante().hashCode()) {
            return (j + 217) * 31 + i;
        }
    }

    public void setChecked(boolean paramBoolean)
    {
        this._getchecked = paramBoolean;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.codvariante);
        paramParcel.writeString(this.alfavariante);
    }
}
