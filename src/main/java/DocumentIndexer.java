import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.File;
import java.util.List;


/**
 * This terminal application creates an Apache Lucene index in a folder and adds files into this index
 * based on the input of the user.
 */
public class DocumentIndexer {
    private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
    private IndexWriter writer;
    /**
     * Constructor
     * @param indexDir the name of the folder in which the index should be created
     * @throws java.io.IOException when exception creating index.
     */
    DocumentIndexer(String indexDir) throws IOException {
        // the boolean true parameter means to create a new index everytime,
        // potentially overwriting any existing files there.
        FSDirectory dir = FSDirectory.open(new File(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
        writer = new IndexWriter(dir, config);
    }

    /**
     * Indexes a file or directory
     * @param fileName the name of a text file or a folder we wish to add to the index
     * @throws java.io.IOException when exception
     */
    public void indexFile(String fileName) throws IOException {
        //===================================================
        //gets the list of files in a folder (if user has submitted
        //the name of a folder) or gets a single file name (is user
        //has submitted only the file name)
        //===================================================
        XMLParser xmlData = new XMLParser(fileName);

        int originalNumDocs = writer.numDocs();
        List<String> tweets = xmlData.getData("text");
        List<String> names = xmlData.getData("name");

        for (int i=0; i<tweets.size(); i++)
        {
            Document doc = new Document();
            //===================================================
            // add contents
            //===================================================
            doc.add(new TextField("text", tweets.get(i), Field.Store.YES));
            doc.add(new StringField("name", names.get(i), Field.Store.YES));
            writer.addDocument(doc);
        }

        int newNumDocs = writer.numDocs();
        System.out.println("");
        System.out.println("************************");
        System.out.println((newNumDocs - originalNumDocs) + " documents added.");
        System.out.println("************************");
    }

    public void closeIndex() throws IOException {
        writer.close();
    }
}