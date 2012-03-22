
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
    	FilteredIndexReader filter = new FilteredIndexReader(q, 10);
    	
    	ImageSearcher searcher = ImageSearcherFactory.createColorLayoutImageSearcher(10);
    	
    	BufferedImage image = ImageIO.read(reference);

    	return searcher.search(image, filter);
    }
    
    public static void main(String[] args) {
    	try {
    		ImageSearchHits hits = new CombinedSearcher().search(new FileInputStream("/home/hkf/lay-p8910001.jpg"), "blubb");
    		
    		System.out.println(hits.doc(0));
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
