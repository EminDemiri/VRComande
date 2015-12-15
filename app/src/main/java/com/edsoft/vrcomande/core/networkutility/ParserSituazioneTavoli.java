package com.edsoft.vrcomande.core.networkutility;

import android.util.Log;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;



/**
 * Created by EDSoft on 10/12/2015.
 */
public class ParserSituazioneTavoli {

    ArrayList<TavoloXML> _tavoliOppupati = new ArrayList();

    static void eDebug(String paramString)
    {
        Log.e("DomParsing", paramString + "\n");
    }

    private TavoloXML leggiTavoli(Element paramElement)
    {
        TavoloXML localTavoloXML = new TavoloXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                if (((Element)localObject2).getChildNodes().getLength() > 0)
                {
                    localObject1 = ((Element)localObject2).getNodeName();
                    localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                    if (((String)localObject1).equals("sala")) {
                        localTavoloXML.sala = ((String)localObject2);
                    }
                    if (((String)localObject1).equals("numero")) {
                        localTavoloXML.numero = Integer.parseInt((String)localObject2);
                    }
                    if (((String)localObject1).equals("situazione")) {
                        localTavoloXML.situazione = Integer.parseInt((String)localObject2);
                    }
                    if (((String)localObject1).equals("coperti")) {
                        localTavoloXML.coperti = Integer.parseInt((String)localObject2);
                    }
                    if (((String)localObject1).equals("orarioPreno")) {
                        localTavoloXML.orariopreno = ((String)localObject2);
                    }
                    if (((String)localObject1).equals("nominativoPreno")) {
                        localTavoloXML.nominativopreno = ((String)localObject2);
                    }
                }
            }
            i += 1;
        }
        return localTavoloXML;
    }

    static void vDebug(String paramString)
    {
        Log.v("DomParsing", paramString + "\n");
    }

    public ArrayList<TavoloXML> getTavoliOccupati()
    {
        return this._tavoliOppupati;
    }

    public networkresult parseXml(String paramString)
    {
        localnetworkresult = new networkresult(0, "", "");
        if (paramString != null) {}
        try
        {
            if (paramString.compareTo("") != 0)
            {
                paramString = new ByteArrayInputStream(paramString.getBytes("UTF-8"));
                paramString = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(paramString).getDocumentElement().getChildNodes();
                int i = 0;
                while (i < paramString.getLength())
                {
                    Object localObject = paramString.item(i);
                    if (((Node)localObject).getNodeType() == 1)
                    {
                        localObject = (Element)localObject;
                        if (((Element)localObject).getNodeName().equals("tavolo")) {
                            this._tavoliOppupati.add(leggiTavoli((Element)localObject));
                        }
                    }
                    i += 1;
                }
            }
            return localnetworkresult;
        }
        catch (SAXException paramString)
        {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = paramString.toString();
            eDebug(localnetworkresult.errMesg);
            return localnetworkresult;
        }
        catch (IOException paramString)
        {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = paramString.toString();
            eDebug(localnetworkresult.errMesg);
            return localnetworkresult;
        }
        catch (ParserConfigurationException paramString)
        {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = paramString.toString();
            eDebug(localnetworkresult.errMesg);
            return localnetworkresult;
        }
        catch (FactoryConfigurationError paramString)
        {
            localnetworkresult.result = -1;
            localnetworkresult.errMesg = paramString.toString();
            eDebug(localnetworkresult.errMesg);
        }
    }
}
