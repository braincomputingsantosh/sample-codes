# ZooKeeper UI Guide

This README provides instructions on setting up and accessing the ZooKeeper UI for managing your ZooKeeper ensemble.

## Setting Up

### 1. Edit Configuration

Before starting the ZooKeeper UI, ensure that the nodes for all three servers are added to the configuration.

**File**: `config.cfg`

```bash
# Add your ZooKeeper nodes in the following format
server.1=<ZK_NODE1_IP>:2888:3888
server.2=<ZK_NODE2_IP>:2888:3888
server.3=<ZK_NODE3_IP>:2888:3888
```

Replace `<ZK_NODE1_IP>`, `<ZK_NODE2_IP>`, and `<ZK_NODE3_IP>` with the respective IP addresses of your ZooKeeper nodes.

### 2. Start the ZooKeeper UI

Once the configuration is set up, you can start the ZooKeeper UI using the following command:

```bash
java -jar zkui.jar
```

Ensure that you run this command from the directory containing `zkui.jar`.

### 3. Access the UI

Open your preferred web browser and navigate to:

```
http://localhost:9090
```

This will bring up the ZooKeeper UI interface.

### 4. Login to ZooKeeper UI

Use the following credentials to log in:

- **Username**: `admin`
- **Password**: `manager`

Once logged in, you can manage your ZooKeeper ensemble and perform various operations.

## Notes:

- Ensure that the servers specified in the `config.cfg` are up and running before accessing the UI.
- Always handle the `config.cfg` file with care, especially in production environments, as it contains sensitive information.
- Regularly backup your ZooKeeper data and configurations.
- For security reasons, consider changing the default login credentials and restricting access to the UI.

---

That's it! You're now set up and ready to manage your ZooKeeper ensemble using the UI. Safe management!
