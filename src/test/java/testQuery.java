import java.io.IOException;

/**
 * Created by vijaysm on 2/21/16.
 */
public class testQuery {
    public static void main(String[] args) throws IOException {
        String indexLocation = "/tmp/testIndex";
        DocumentFind documentFind = new DocumentFind(indexLocation);
        documentFind.printQuery("Leonardo");
    }
}
