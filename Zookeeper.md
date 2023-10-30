## Help Guide: Uploading Configuration to Solr using Zookeeper Commands

Solr can run in standalone mode or in SolrCloud mode with ZooKeeper. In SolrCloud mode, Solr configuration is stored in ZooKeeper. This guide will walk you through the process of uploading configuration to Solr using ZooKeeper commands.

### **Prerequisites**:
1. Ensure Solr is running in SolrCloud mode.
2. Have access to the Solr server and the directory where Solr is installed.
3. Familiarize yourself with the configuration set you wish to upload. This is typically a directory containing `solrconfig.xml`, `schema.xml` or `managed-schema`, and other configuration files.

### **Steps to Upload Configuration**:

1. **Navigate to Solr Directory**:
    Open a terminal or command prompt and navigate to the Solr installation directory:
    ```bash
    cd /path/to/solr
    ```

2. **Use the ZooKeeper Script**:
   Solr comes bundled with a `zkcli.sh` script (or `zkcli.bat` on Windows) to interact with ZooKeeper. This script is typically located in the `server/scripts/cloud-scripts/` directory of your Solr installation.

3. **Upload Configuration Set**:
    Use the following command to upload your configuration set to ZooKeeper:
    ```bash
    server/scripts/cloud-scripts/zkcli.sh -zkhost <ZOOKEEPER_HOST>:<ZOOKEEPER_PORT> -cmd upconfig -confname <CONFIG_NAME> -confdir /path/to/your/configset/
    ```
    
    Replace the placeholders with appropriate values:
    - `<ZOOKEEPER_HOST>`: The hostname or IP address of your ZooKeeper instance. If you're running ZooKeeper ensemble, you can specify multiple hosts separated by commas.
    - `<ZOOKEEPER_PORT>`: The port number on which ZooKeeper is listening (default is `2181`).
    - `<CONFIG_NAME>`: The name you wish to give this configuration set in Solr.
    - `/path/to/your/configset/`: The path to the directory containing your Solr configuration files.

    Example:
    ```bash
    server/scripts/cloud-scripts/zkcli.sh -zkhost 127.0.0.1:2181 -cmd upconfig -confname myConfig -confdir /opt/mySolrConfig/
    ```

4. **Verify the Upload**:
    After uploading, you can use the Solr Admin UI to verify that the configuration has been uploaded correctly. 
    - Navigate to the Solr Admin UI (typically at `http://<SOLR_HOST>:<SOLR_PORT>/solr/#/~cloud`).
    - Click on the “Tree” view under the “Cloud” section on the left sidebar.
    - Look for your `<CONFIG_NAME>` under the `/configs` node in the tree. You should see all your configuration files listed there.

### **Notes**:

- When creating a new collection in Solr, you can now use the `-c` option with the name of the configuration (`<CONFIG_NAME>`) you uploaded.
- Always backup your configurations before making changes.
- It's good practice to first test any new configuration in a development environment before applying it to a production setup.

You've now successfully uploaded a configuration to Solr using ZooKeeper commands!
