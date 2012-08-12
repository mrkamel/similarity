
import net.semanticmetadata.lire.DocumentBuilder;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.semanticmetadata.lire.ImageSearchHits;

import org.apache.lucene.queryParser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SearchHandler extends HttpServlet {	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		try {
			ImageSearchHits hits = new CombinedSearcher().search(new FileInputStream(request.getAttribute("file").toString()), request.getParameter("q"));
			
			int start = new Integer(request.getParameter("start")).intValue();
			int limit = new Integer(request.getParameter("limit")).intValue();
			
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				
			Element response_element = document.createElement("response");
			response_element.setAttribute("num", new Integer(hits.length()).toString());
			document.appendChild(response_element);

			if(start < hits.length()) {
				for(int i = start; i < Math.min(start + limit, hits.length()); i++) {
					String id = hits.doc(i).getField(CombinedIndexer.FIELD_ID).stringValue();
					String identifier = hits.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
					String text = hits.doc(i).getField(CombinedIndexer.FIELD_TEXT).stringValue();
						
					Element result_element = document.createElement("result");
					response_element.appendChild(result_element);
						
					Element id_element = document.createElement("id");
					id_element.appendChild(document.createTextNode(id));
					result_element.appendChild(id_element);
						
					Element identifier_element = document.createElement("identifier");
					identifier_element.appendChild(document.createTextNode(identifier));
					result_element.appendChild(identifier_element);
						
					Element text_element = document.createElement("text");
					text_element.appendChild(document.createTextNode(text));
					result_element.appendChild(text_element);
						
					Element score_element = document.createElement("score");
					score_element.appendChild(document.createTextNode(new Float(hits.score(i)).toString()));
					result_element.appendChild(score_element);
				}
			}

			PrintWriter writer = response.getWriter();
				
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			StringWriter string_writer = new StringWriter();
			StreamResult stream_result = new StreamResult(string_writer);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, stream_result);
	            
			writer.println(string_writer.toString());
		} catch(ParseException e) {
			e.printStackTrace();
		} catch(TransformerConfigurationException e) {
			e.printStackTrace();
		} catch(TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch(TransformerException e) {
			e.printStackTrace();
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
