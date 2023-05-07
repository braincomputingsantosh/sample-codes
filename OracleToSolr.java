import java.sql.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.ContentStreamBase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OracleToSolr {

  private static final int BATCH_SIZE = 10000; // Number of records to process in each batch
  private static final int NUM_THREADS = 5; // Number of threads to use for indexing

  public static void main(String[] args) {
    // Set up Solr client
    String solrUrl = "http://localhost:8983/solr/my_collection";
    SolrClient solr = new HttpSolrClient.Builder(solrUrl).build();

    // Set up Oracle connection
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORCL";
    String user = "my_username";
    String password = "my_password";
    try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
         Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
         ResultSet rs = stmt.executeQuery("SELECT * FROM my_table")) {

      // Set up thread pool for indexing
      ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

      // Loop through result set and create Solr documents for each record
      int count = 0;
      while (rs.next()) {
        String id = rs.getString("id");
        String title = rs.getString("title");
        String content = rs.getString("content");

        // Create Solr document and add fields
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("title", title);
        doc.addField("content", content);

        // Add document to Solr using a separate thread
        executor.submit(() -> {
          try {
            ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update");
            req.addContentStream(new ContentStreamBase.StringStream(doc.toString()));
            req.setParam("commit", "true");
            solr.request(req);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });

        count++;
        if (count % BATCH_SIZE == 0) {
          solr.commit(); // Commit changes in batches
        }
      }

      // Shut down thread pool and commit any remaining changes
      executor.shutdown();
      solr.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      solr.close();
    }
  }
}
