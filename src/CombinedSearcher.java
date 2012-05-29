
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

import org.apache.lucene.queryParser.ParseException;

public class CombinedSearcher {
    public static final int MAX_RESULTS = 1000000;

    public ImageSearchHits search(InputStream reference, String q) throws IOException, ParseException {
    	FilteredIndexReader filter = new FilteredIndexReader(q, MAX_RESULTS);
    	
    	ImageSearcher searcher = ImageSearcherFactory.createColorLayoutImageSearcher(MAX_RESULTS);
    	
    	BufferedImage image = ImageIO.read(reference);

    	return searcher.search(image, filter);
    }
}
