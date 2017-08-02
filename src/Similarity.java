
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.MultiPartFilter;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Similarity {
	public static void main(String[] args) throws Exception {
    Server server = new Server(8984);
    
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");
    server.setHandler(context);
    
    context.addServlet(new ServletHolder(new UploadHandler()), "/uploads");
    FilterHolder uploadsFilter = new FilterHolder(new MultiPartFilter());
    uploadsFilter.setInitParameter("deleteFiles", "true");
    context.addFilter(uploadsFilter, "/uploads", null);
    
    context.addServlet(new ServletHolder(new SearchHandler()), "/search");
    FilterHolder searchFilter = new FilterHolder(new MultiPartFilter());
    searchFilter.setInitParameter("deleteFiles", "true");
    context.addFilter(searchFilter, "/search", null);
    
    server.start();
    server.join();
	}
}

