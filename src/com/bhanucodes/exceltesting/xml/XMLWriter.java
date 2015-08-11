package com.bhanucodes.exceltesting.xml;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLWriter {
	
	public static void main(String[] args){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("transactions");
			doc.appendChild(rootElement);
			
			Element transactionRecord = doc.createElement("transactionRecord");
			rootElement.appendChild(transactionRecord);
			
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode("11-00123"));
			transactionRecord.appendChild(id);
			
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(new Date().toString()));
			transactionRecord.appendChild(date);
			
			Element products = doc.createElement("products");
			
			Element productId = doc.createElement("productId");
			productId.appendChild(doc.createTextNode("Acer_12"));
			products.appendChild(productId);
			
			productId = doc.createElement("productId");
			productId.appendChild(doc.createTextNode("Dell_21"));
			products.appendChild(productId);
			
			transactionRecord.appendChild(products);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult("transactions.xml");
			
			transformer.transform(source, result);
			
			System.out.println("Done Write \n Now Read");
			
			Document docRead = docBuilder.parse(new File("transactions.xml"));
			
			docRead.getDocumentElement().normalize();

			System.out.println("Root element :" + docRead.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("transactionRecord");
					
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("ID : " + eElement.getElementsByTagName("id").item(0).getTextContent());
					System.out.println("Date : " + eElement.getElementsByTagName("date").item(0).getTextContent());
					NodeList nodeList =  eElement.getElementsByTagName("products").item(0).getChildNodes();
					 for (int count = 0; count < nodeList.getLength(); count++) {
						 Node tempNode = nodeList.item(count);
						 System.out.println("ProductId : " + tempNode.getTextContent());
					 }
					
					
					
				}
			}
			
			
			
		}catch(Exception ex){
			System.out.println("Error !! " + ex.getLocalizedMessage());
		}
	}

}
