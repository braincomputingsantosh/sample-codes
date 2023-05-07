import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleToSparkToSolr {

    public static void main(String[] args) {
        // Define Spark configuration
        SparkConf conf = new SparkConf()
                .setAppName("OracleToSparkToSolr")
                .setMaster("local[*]"); // Replace with Spark master URL in production

        // Create Spark context and session
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // Define Oracle DB connection properties
        String jdbcUrl = "jdbc:oracle:thin:@//host:port/service";
        String dbUser = "username";
        String dbPassword = "password";

        // Connect to Oracle DB
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            System.out.println("Connected to Oracle DB.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Define Solr connection properties
        String zkHost = "localhost:9983"; // Replace with Solr ZooKeeper URL in production
        String collection = "collection1";

        // Connect to Solr
        CloudSolrClient solrClient = new CloudSolrClient.Builder()
                .withZkHost(zkHost)
                .build();
        solrClient.setDefaultCollection(collection);
        System.out.println("Connected to Solr.");

        // Define Oracle queries to fetch data
        String query1 = "SELECT * FROM table1 WHERE column1 = 'value1'";
        String query2 = "SELECT * FROM table2 WHERE column2 = 'value2'";

        // Execute Oracle queries and ingest data to Solr
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            while (rs.next()) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", rs.getString("id"));
                doc.addField("field1", rs.getString("field1"));
                doc.addField("field2", rs.getString("field2"));
                solrClient.add(doc);
            }
            rs.close();

            rs = stmt.executeQuery(query2);
            while (rs.next()) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", rs.getString("id"));
                doc.addField("field1", rs.getString("field1"));
                doc.addField("field3", rs.getString("field3"));
                solrClient.add(doc);
            }
            rs.close();

            solrClient.commit();
            System.out.println("Data successfully ingested to Solr.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close connections and Spark context
        try {
            conn.close();
            solrClient.close();
            spark.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
