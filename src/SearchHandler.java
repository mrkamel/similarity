
import net.semanticmetadata.lire.DocumentBuilder;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.semanticmetadata.lire.ImageSearchHits;

import org.apache.lucene.queryParser.ParseException;

public class SearchHandler extends HttpServlet {	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		try {
			ImageSearchHits hits = new CombinedSearcher().search(new FileInputStream(request.getAttribute("file").toString()), request.getParameter("q"));
			
			int start = new Integer(request.getParameter("start")).intValue();
			int limit = new Integer(request.getParameter("limit")).intValue();
			
			PrintWriter writer = response.getWriter();
			
			writer.println("<response num='" + hits.length() + "'>");
			
			if(start < hits.length()) {
				for(int i = start; i < Math.min(start + limit, hits.length()); i++) {
					String id = hits.doc(i).getField(CombinedIndexer.FIELD_ID).stringValue();
					String identifier = hits.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
					String text = hits.doc(i).getField(CombinedIndexer.FIELD_TEXT).stringValue();
					
					writer.println("<result>");
						writer.println("<id>" + id + "</id>");
						writer.println("<identifier>" + identifier + "</identifier>");
						writer.println("<text>" + text + "</text>");
						writer.println("<score>" + hits.score(i) + "</score>");
					writer.println("</result>");
				}
			}
			
			writer.println("</response>");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
