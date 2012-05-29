
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CombinedIndexer {
	private static IndexWriter writer = null;
	private static DocumentBuilder builder = null;
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_TEXT = "text";
	
    public void index(String identifier, InputStream stream, String id, String text) throws IOException {
    	if(CombinedIndexer.builder == null)
    		CombinedIndexer.builder = DocumentBuilderFactory.getColorLayoutBuilder();
     
    	if(CombinedIndexer.writer == null)
    		CombinedIndexer.writer = new IndexWriter(FSDirectory.open(new File("data")), new WhitespaceAnalyzer(), !(new File("data").exists()), IndexWriter.MaxFieldLength.UNLIMITED);

        Document doc = CombinedIndexer.builder.createDocument(stream, identifier);
        
        doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
        
        CombinedIndexer.writer.addDocument(doc);
        
        CombinedIndexer.writer.commit();
    }
}
