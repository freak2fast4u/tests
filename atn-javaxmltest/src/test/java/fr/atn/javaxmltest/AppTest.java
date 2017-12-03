package fr.atn.javaxmltest;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	@Test
	public void testXmlA() {
		
		try {
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			File fXmlFile = new File("sample.xml");
			
			Document doc = dBuilder.parse(fXmlFile);
			
			filterIntoFile("/company/staff[@id='2001']", doc, new File("output.xml"));
			
			
			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println(
							"First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println(
							"Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println(
							"Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void filterIntoFile(String xpathExpression, Document doc, File file) throws XPathExpressionException, ParserConfigurationException {
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xpath1 = xpf.newXPath();
		NodeList nodeList = (NodeList) xpath1.evaluate(xpathExpression, doc, XPathConstants.NODESET);
		System.out.println(nodeList.toString());
		
		DocumentBuilder db = dbFactory.newDocumentBuilder();
		Document docOutput = db.newDocument();
		Element rootE = docOutput.createElement("root");
		docOutput.appendChild(rootE);
		
		for(int i=0; i <= nodeList.getLength(); i++) {
			rootE.appendChild(nodeList.item(i));
		}
		
		
		
		
	}

}
