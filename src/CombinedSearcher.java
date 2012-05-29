
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import org.apache.lucene.document.Document;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.FSDirectory;

public class CombinedSearcher {
    public ImageSearchHits search(InputStream reference, String q) throws IOException, ParseException {
    	FilteredIndexReader filter = new FilteredIndexReader(q, 1000000);
    	
    	ImageSearcher searcher = ImageSearcherFactory.createColorLayoutImageSearcher(1000000);
    	
    	BufferedImage image = ImageIO.read(reference);

    	return searcher.search(image, filter);
    }
}
