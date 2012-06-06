
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.analysis.WhitespaceAnalyzer;

public class CombinedIndexer {
	private static IndexWriter writer = null;
    private static DocumentBuilder builder = null;
	
    public static final String FIELD_ID = "id";
    public static final String FIELD_TEXT = "text";
	
    public void index(String identifier, InputStream stream, String id, String text) throws IOException {
        if(builder == null)
            builder = DocumentBuilderFactory.getColorLayoutBuilder();

        if(writer == null)
            writer = new IndexWriter(FSDirectory.open(new File("data")), new WhitespaceAnalyzer(), !(new File("data").exists()), IndexWriter.MaxFieldLength.UNLIMITED);

        writer.deleteDocuments(new Term(FIELD_ID, id));
        writer.commit();

        Document doc = builder.createDocument(stream, identifier);
        
        doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
        
        writer.addDocument(doc);
        writer.commit();
    }
}
