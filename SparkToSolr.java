import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.util.Properties;

public class SparkToSolr {

    public static void main(String[] args) {
        // Define Spark configuration
        SparkConf conf = new SparkConf()
                .setAppName("OracleToSolr")
                .setMaster("local[*]"); // Replace with Spark master URL in production

        // Create Spark context and session
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // Define Oracle DB connection properties
        String jdbcUrl = "jdbc:oracle:thin:@//host:port/service";
        String dbUser = "username";
        String dbPassword = "password";

        // Define Solr connection properties
        String zkHost = "localhost:9983"; // Replace with Solr ZooKeeper URL in production
        String collection = "collection1";

        // Connect to Oracle DB and read data using SQL queries
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", dbUser);
        connectionProperties.put("password", dbPassword);

        Dataset<Row> df1 = spark.read().jdbc(jdbcUrl, "(SELECT * FROM table1 WHERE column1 = 'value1') t1", connectionProperties);
        Dataset<Row> df2 = spark.read().jdbc(jdbcUrl, "(SELECT * FROM table2 WHERE column2 = 'value2') t2", connectionProperties);

        // Join the two DataFrames using a common column
        Dataset<Row> joinedDF = df1.join(df2, df1.col("common_column").equalTo(df2.col("common_column")));

        // Create a new DataFrame by selecting required columns
        Dataset<Row> finalDF = joinedDF.select(df1.col("column1"), df1.col("column2"), df2.col("column3"));

        // Convert DataFrame to an RDD of SolrInputDocument
        JavaRDD<SolrInputDocument> solrRDD = finalDF.toJavaRDD().map(row -> {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("field1", row.getString(0));
            doc.addField("field2", row.getString(1));
            doc.addField("field3", row.getString(2));
            return doc;
        });

        // Connect to Solr
        CloudSolrClient solrClient = new CloudSolrClient.Builder()
                .withZkHost(zkHost)
                .build();
        solrClient.setDefaultCollection(collection);
        System.out.println("Connected to Solr.");

        // Ingest data into Solr
        solrClient.add(solrRDD.collect());
        solrClient.commit();
        System.out.println("Data successfully ingested to Solr.");

        // Close connections and Spark context
        try {
            solrClient.close();
            spark.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
