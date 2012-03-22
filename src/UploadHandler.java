
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;

public class UploadHandler extends HttpServlet {	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		FileInputStream stream = new FileInputStream(request.getAttribute("file").toString());
		
		new CombinedIndexer().index(request.getParameter("file"), stream, request.getParameter("id"), request.getParameter("text"));
	}
}
