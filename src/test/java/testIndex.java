import java.io.IOException;

/**
 * Created by vijaysm on 2/21/16.
 */
public class testIndex {
    public static void main(String[] args) throws IOException {

        String indexLocation = "/tmp/testIndex";

        DocumentIndexer indexer = null;
        try {
            indexer = new DocumentIndexer(indexLocation);
        } catch (Exception ex) {
            System.out.println("Cannot create index..." + ex.getMessage());
            System.exit(-1);
        }


        String xmldocument = "./data/tweets.xml";
        indexer.indexFile(xmldocument);

        //===================================================
        //after adding, we always have to call the
        //closeIndex, otherwise the index is not created
        //===================================================
        indexer.closeIndex();
    }

}
