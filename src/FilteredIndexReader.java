
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.index.TermVectorMapper;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class FilteredIndexReader extends IndexReader {
	private IndexReader reader;
	private ScoreDoc[] docs;
	
	public FilteredIndexReader(String q, int maxDocs) throws CorruptIndexException, IOException, ParseException {
		reader = IndexReader.open(FSDirectory.open(new File("data")), true);
		
		QueryParser parser = new QueryParser(Version.LUCENE_34, "text", new WhitespaceAnalyzer());
		Searcher searcher = new IndexSearcher(FSDirectory.open(new File("data")));
		TopScoreDocCollector collector = TopScoreDocCollector.create(maxDocs, true);
		searcher.search(parser.parse(q), collector);
		docs = collector.topDocs().scoreDocs;
	}
	
	protected void doClose() throws IOException {
	}

	protected void doCommit(Map<String, String> arg0) throws IOException {
	}

	protected void doDelete(int arg0) throws CorruptIndexException, IOException {
	}

	protected void doSetNorm(int arg0, String arg1, byte arg2)
			throws CorruptIndexException, IOException {
	}

	protected void doUndeleteAll() throws CorruptIndexException, IOException {
	}

	public int docFreq(Term arg0) throws IOException {
		return 0;
	}

	public Document document(int i, FieldSelector arg1)
			throws CorruptIndexException, IOException {
		return reader.document(docs[i].doc);
	}

	public Collection<String> getFieldNames(FieldOption arg0) {
		return null;
	}

	public TermFreqVector getTermFreqVector(int arg0, String arg1)
			throws IOException {
		return null;
	}

	public void getTermFreqVector(int arg0, TermVectorMapper arg1)
			throws IOException {
	}

	public void getTermFreqVector(int arg0, String arg1, TermVectorMapper arg2)
			throws IOException {
	}

	public TermFreqVector[] getTermFreqVectors(int arg0) throws IOException {
		return null;
	}

	public boolean hasDeletions() {
		return false;
	}

	public boolean isDeleted(int arg0) {
		return false;
	}

	public int maxDoc() {
		return docs.length;
	}

	public byte[] norms(String arg0) throws IOException {
		return null;
	}

	public void norms(String arg0, byte[] arg1, int arg2) throws IOException {
	}

	public int numDocs() {
		return docs.length;
	}

	public TermDocs termDocs() throws IOException {
		return null;
	}

	public TermPositions termPositions() throws IOException {
		return null;
	}

	public TermEnum terms() throws IOException {
		return null;
	}

	public TermEnum terms(Term arg0) throws IOException {
		return null;
	}
}
