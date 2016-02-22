import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by vijaysm on 2/21/16.
 */
public class DocumentFind {
    private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
    IndexSearcher searcher = null;
    TopScoreDocCollector collector = null;

    DocumentFind(String indexLocation) throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexLocation)));
        searcher = new IndexSearcher(reader);
        collector = TopScoreDocCollector.create(5, true);
    }

    public void printQuery(String query) {
        Query q = null;
        try {
            q = new QueryParser(Version.LUCENE_46, "text", analyzer).parse(query);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            System.out.println("Found " + hits.length + " hits.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". " + d.get("name") + " \t " + d.get("text")+ " score=" + hits[i].score);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
