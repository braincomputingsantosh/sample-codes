import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.main.Main;

public class DBToSolrIngestion extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Connect to the database and select data to ingest
        from("jdbc:myDataSource?select=SELECT * FROM myTable")
            // Convert the result set to a Solr input document
            .convertBodyTo(org.apache.solr.common.SolrInputDocument.class)
            // Set the Solr core to ingest to
            .setHeader(SolrConstants.PARAM_COLLECTION, constant("mySolrCore"))
            // Ingest the document into Solr
            .to("solr://localhost:8983")
        ;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        // Configure the database connection
        main.bind("myDataSource", getDataSource());
        main.addRouteBuilder(new DBToSolrIngestion());
        // Start the Camel context
        main.run();
    }

    // Create a data source for the database connection
    private static javax.sql.DataSource getDataSource() {
        org.apache.commons.dbcp2.BasicDataSource dataSource = new org.apache.commons.dbcp2.BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/myDB");
        dataSource.setUsername("myUser");
        dataSource.setPassword("myPassword");
        return dataSource;
    }

}
