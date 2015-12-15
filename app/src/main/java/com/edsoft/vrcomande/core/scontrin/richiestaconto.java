package com.edsoft.vrcomande.core.scontrin;

import android.util.Log;
import android.util.Xml;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.xmlpull.v1.XmlSerializer;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class richiestaconto {
    String _sala = "";
    String _tavolo = "";

    public streamoutresult ToXml()
    {
        Object localObject1 = new ByteArrayOutputStream();
        Object localObject2 = Xml.newSerializer();
        try
        {
            ((XmlSerializer)localObject2).setOutput((OutputStream)localObject1, "ASCII");
            ((XmlSerializer)localObject2).startDocument(null, Boolean.valueOf(true));
            ((XmlSerializer)localObject2).setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            ((XmlSerializer)localObject2).startTag(null, "richiestaconto");
            ((XmlSerializer)localObject2).startTag(null, "sala");
            ((XmlSerializer)localObject2).text(this._sala);
            ((XmlSerializer)localObject2).endTag(null, "sala");
            ((XmlSerializer)localObject2).startTag(null, "tavolo");
            ((XmlSerializer)localObject2).text(this._tavolo);
            ((XmlSerializer)localObject2).endTag(null, "tavolo");
            ((XmlSerializer)localObject2).endTag(null, "richiestaconto");
            ((XmlSerializer)localObject2).endDocument();
            ((XmlSerializer)localObject2).flush();
            ((ByteArrayOutputStream)localObject1).close();
            localObject2 = new streamoutresult(0, (OutputStream)localObject1, "");
            return (streamoutresult)localObject2;
        }
        catch (Exception localException)
        {
            localObject1 = new streamoutresult(-1, (OutputStream)localObject1, localException.getMessage());
            Log.e("Exception", "Exception occured in wroting");
        }
        return (streamoutresult)localObject1;
    }

    public String getSala()
    {
        return this._sala;
    }

    public String getTavolo()
    {
        return this._tavolo;
    }

    public void setSala(String paramString)
    {
        this._sala = paramString;
    }

    public void setTavolo(String paramString)
    {
        this._tavolo = paramString;
    }
}
