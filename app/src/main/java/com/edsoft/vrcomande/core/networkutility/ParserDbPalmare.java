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
public class ParserDbPalmare {

    ArrayList<RepartiXML> parsedData = new ArrayList();
    ArrayList<InfoCdpXML> parsedInfoCdp = new ArrayList();
    ArrayList<MsgForCdpXML> parsedMsgForCdp = new ArrayList();
    ArrayList<OperatoriXML> parsedOperatori = new ArrayList();
    ArrayList<SaleXML> parsedSale = new ArrayList();
    ArrayList<VariantiXML> parsedVarianti = new ArrayList();

    static void eDebug(String paramString)
    {
        Log.e("DomParsing", paramString + "\n");
    }

    private InfoCdpXML leggiCDP(Element paramElement)
    {
        InfoCdpXML localInfoCdpXML = new InfoCdpXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                localObject1 = ((Element)localObject2).getNodeName();
                localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                if (((String)localObject1).equals("Codice")) {
                    localInfoCdpXML.setCodCDP((String)localObject2);
                }
                if (((String)localObject1).equals("AlfaCdp")) {
                    localInfoCdpXML.setAlfaCDP((String)localObject2);
                }
            }
            i += 1;
        }
        return localInfoCdpXML;
    }

    private MsgForCdpXML leggiMagCDP(Element paramElement)
    {
        MsgForCdpXML localMsgForCdpXML = new MsgForCdpXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                localObject1 = ((Element)localObject2).getNodeName();
                localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                if (((String)localObject1).equals("DatiMSG"))
                {
                    localObject1 = ((String)localObject2).split("\\|");
                    if (localObject1.length >= 3)
                    {
                        localMsgForCdpXML.setCodMSG(localObject1[0]);
                        localMsgForCdpXML.setTestoMSG(localObject1[1]);
                        localMsgForCdpXML.setCodCDP(localObject1[2]);
                    }
                }
            }
            i += 1;
        }
        return localMsgForCdpXML;
    }

    private OperatoriXML leggiOperatore(Element paramElement)
    {
        OperatoriXML localOperatoriXML = new OperatoriXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                localObject1 = ((Element)localObject2).getNodeName();
                localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                if (((String)localObject1).equals("codop")) {
                    localOperatoriXML.setCodOp((String)localObject2);
                }
                if (((String)localObject1).equals("alfaop")) {
                    localOperatoriXML.setAlfaOp((String)localObject2);
                }
                if (((String)localObject1).equals("loginop")) {
                    localOperatoriXML.setLoginOp((String)localObject2);
                }
                if (((String)localObject1).equals("pwdop")) {
                    localOperatoriXML.setPwdOp((String)localObject2);
                }
            }
            i += 1;
        }
        return localOperatoriXML;
    }

    private RepartiXML leggiReparto(Element paramElement)
    {
        RepartiXML localRepartiXML = new RepartiXML();
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
                if (((String)localObject2).equals("codrep")) {
                    localRepartiXML.setCodRep((String)localObject3);
                }
                if (((String)localObject2).equals("alfarep")) {
                    localRepartiXML.setAlfaRep((String)localObject3);
                }
                if (((String)localObject2).equals("Articolo"))
                {
                    localObject1 = ((Node)localObject1).getChildNodes();
                    localObject2 = new ArticoliXML();
                    int j = 0;
                    while (j < ((NodeList)localObject1).getLength())
                    {
                        localObject3 = ((NodeList)localObject1).item(j);
                        if (((Node)localObject3).getNodeType() == 1)
                        {
                            Object localObject4 = (Element)localObject3;
                            localObject3 = ((Element)localObject4).getNodeName();
                            localObject4 = ((Element)localObject4).getFirstChild().getNodeValue();
                            vDebug("______Dettaglio:" + (String)localObject3);
                            vDebug("______Contenuto Dettaglio:" + (String)localObject4);
                            vDebug("");
                            if (((String)localObject3).equals("codart")) {
                                ((ArticoliXML)localObject2).setCodArt((String)localObject4);
                            }
                            if (((String)localObject3).equals("alfaart")) {
                                ((ArticoliXML)localObject2).setAlfaArt((String)localObject4);
                            }
                            if (((String)localObject3).equals("LinkVariante")) {
                                ((ArticoliXML)localObject2).AddLinkVariante((String)localObject4);
                            }
                            if (((String)localObject3).equals("prezzoart")) {
                                ((ArticoliXML)localObject2).setPrezzoArt(Double.parseDouble((String)localObject4) / 100.0D);
                            }
                            if (((String)localObject3).equals("ivaart")) {
                                ((ArticoliXML)localObject2).setIvaArtInt(Integer.parseInt((String)localObject4));
                            }
                        }
                        j += 1;
                    }
                    ((ArticoliXML)localObject2).setPosizione(localRepartiXML.getElencoArt().size() + 1);
                    localRepartiXML.AddArt((ArticoliXML)localObject2);
                }
            }
            i += 1;
        }
        vDebug("");
        localRepartiXML.setPosizione(this.parsedData.size());
        return localRepartiXML;
    }

    private SaleXML leggiSala(Element paramElement)
    {
        SaleXML localSaleXML = new SaleXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                localObject1 = ((Element)localObject2).getNodeName();
                localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                if (((String)localObject1).equals("codsala")) {
                    localSaleXML.setCodSala((String)localObject2);
                }
                if (((String)localObject1).equals("alfasala")) {
                    localSaleXML.setAlfaSala((String)localObject2);
                }
                if (((String)localObject1).equals("numtavoli")) {
                    localSaleXML.setNumeroTavoli(Integer.parseInt((String)localObject2));
                }
                if (((String)localObject1).equals("deltatavoli")) {
                    localSaleXML.setDeltaTavoli(Integer.parseInt((String)localObject2));
                }
            }
            i += 1;
        }
        return localSaleXML;
    }

    private VariantiXML leggiVariante(Element paramElement)
    {
        VariantiXML localVariantiXML = new VariantiXML();
        paramElement = paramElement.getChildNodes();
        int i = 0;
        while (i < paramElement.getLength())
        {
            Object localObject1 = paramElement.item(i);
            if (((Node)localObject1).getNodeType() == 1)
            {
                Object localObject2 = (Element)localObject1;
                localObject1 = ((Element)localObject2).getNodeName();
                localObject2 = ((Element)localObject2).getFirstChild().getNodeValue();
                vDebug("______Dettaglio:" + (String)localObject1);
                vDebug("______Contenuto Dettaglio:" + (String)localObject2);
                vDebug("");
                if (((String)localObject1).equals("codvar")) {
                    localVariantiXML.setCodVariante((String)localObject2);
                }
                if (((String)localObject1).equals("alfavar")) {
                    localVariantiXML.setAlfaVariante((String)localObject2);
                }
                if (((String)localObject1).equals("prezzovar")) {
                    localVariantiXML.setPrezzoVarianteInt(Integer.parseInt((String)localObject2));
                }
                if (((String)localObject1).equals("generalvar")) {
                    localVariantiXML.setVariantePerTutti(Boolean.parseBoolean((String)localObject2));
                }
            }
            i += 1;
        }
        return localVariantiXML;
    }

    static void vDebug(String paramString)
    {
        Log.v("DomParsing", paramString + "\n");
    }

    public ArrayList<OperatoriXML> getParsedOperatori()
    {
        return this.parsedOperatori;
    }

    public ArrayList<RepartiXML> getParsedReparti()
    {
        return this.parsedData;
    }

    public ArrayList<SaleXML> getParsedSale()
    {
        return this.parsedSale;
    }

    public ArrayList<VariantiXML> getParsedVarianti()
    {
        return this.parsedVarianti;
    }

    public ArrayList<InfoCdpXML> getparsedInfoCdp()
    {
        return this.parsedInfoCdp;
    }

    public ArrayList<MsgForCdpXML> getparsedMsgForCdp()
    {
        return this.parsedMsgForCdp;
    }

    public networkresult parseXml(String paramString)
    {
        localnetworkresult = new networkresult(0, "", "");
        try
        {
            ArrayList localArrayList = new ArrayList();
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
                    if (((Element)localObject).getNodeName().equals("Operatore")) {
                        this.parsedOperatori.add(leggiOperatore((Element)localObject));
                    }
                    if (((Element)localObject).getNodeName().equals("VarianteArticolo"))
                    {
                        VariantiXML localVariantiXML = leggiVariante((Element)localObject);
                        this.parsedVarianti.add(localVariantiXML);
                        if (localVariantiXML.variantepertutti) {
                            localArrayList.add(localVariantiXML.codvariante);
                        }
                    }
                    if (((Element)localObject).getNodeName().equals("Sala")) {
                        this.parsedSale.add(leggiSala((Element)localObject));
                    }
                    if (((Element)localObject).getNodeName().equals("Reparto")) {
                        this.parsedData.add(leggiReparto((Element)localObject));
                    }
                    if (((Element)localObject).getNodeName().equals("InfoCDP")) {
                        this.parsedInfoCdp.add(leggiCDP((Element)localObject));
                    }
                    if (((Element)localObject).getNodeName().equals("MsgCucina")) {
                        this.parsedMsgForCdp.add(leggiMagCDP((Element)localObject));
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
