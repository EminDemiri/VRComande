package com.edsoft.vrcomande.core.networkutility;


import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by EDSoft on 10/12/2015.
 */
public class ParserComanda {

    ArrayList<VisuArticoloXML> parsedArticoli = new ArrayList();
    double totaleScontrino = 0.0D;

    static void eDebug(String paramString)
    {
        Log.e("DomParsing", paramString + "\n");
    }

    private VisuArticoloXML leggiArticolo(Element paramElement)
    {
        VisuArticoloXML localVisuArticoloXML = new VisuArticoloXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject3 = (Element)localObject1;
                Object localObject2 = ((Element)localObject3).getNodeName();
                localObject3 = ((Element)localObject3).getFirstChild().getNodeValue();
                vDebug("______Dettaglio:" + (String)localObject2);
                vDebug("______Contenuto Dettaglio:" + (String)localObject3);
                vDebug("");
                if (((String)localObject2).equals("Alfa")) {
                    localVisuArticoloXML.setAlfaArticolo((String)localObject3);
                }
                if (((String)localObject2).equals("Qta")) {
                    localVisuArticoloXML.setQtaArticolo(Integer.parseInt((String)localObject3));
                }
                if (((String)localObject2).equals("variante"))
                {
                    localObject1 = ((Node)localObject1).getChildNodes();
                    int j = 0;
                    while (j < ((NodeList)localObject1).getLength())
                    {
                        localObject2 = ((NodeList)localObject1).item(j);
                        if (((Node)localObject2).getNodeType() == 1)
                        {
                            localObject3 = (Element)localObject2;
                            localObject2 = ((Element)localObject3).getNodeName();
                            localObject3 = ((Element)localObject3).getFirstChild().getNodeValue();
                            vDebug("______Dettaglio:" + (String)localObject2);
                            vDebug("______Contenuto Dettaglio:" + (String)localObject3);
                            vDebug("");
                            if (((String)localObject2).equals("alfavar")) {
                                localVisuArticoloXML.AddLinkVariante((String)localObject3);
                            }
                        }
                        j += 1;
                    }
                }
            }
            i += 1;
        }
        return localVisuArticoloXML;
    }

    static void vDebug(String paramString)
    {
        Log.v("DomParsing", paramString + "\n");
    }

    public ArrayList<VisuArticoloXML> getParsedArticoli()
    {
        return this.parsedArticoli;
    }

    public double getTotaleScontrino()
    {
        return this.totaleScontrino;
    }

    public networkresult parseXml(String paramString)
    {
        localnetworkresult = new networkresult(0, "", "");
        try
        {
            paramString = new ByteArrayInputStream(paramString.getBytes("UTF-8"));
            paramString = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(paramString).getDocumentElement();
            vDebug("Root element :" + paramString.getNodeName());
            vDebug("");
            paramString = paramString.getChildNodes();
            int i = 0;
            while (i < paramString.getLength())
            {
                Object localObject = paramString.item(i);
                if (((Node)localObject).getNodeType() == 1)
                {
                    localObject = (Element)localObject;
                    if (((Element)localObject).getNodeName().equals("TotaleScontrino")) {
                        this.totaleScontrino = (Double.parseDouble(((Element)localObject).getFirstChild().getNodeValue()) / 100.0D);
                    }
                    if (((Element)localObject).getNodeName().equals("Articolo")) {
                        this.parsedArticoli.add(leggiArticolo((Element)localObject));
                    }
                }
                i += 1;
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
