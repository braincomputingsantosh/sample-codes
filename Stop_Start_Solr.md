## Help Guide: Starting and Stopping Solr using Windows Services

This guide will help you start and stop the Solr service using Windows Services on a Windows Server machine. Before we dive into the steps, ensure you have the necessary administrative privileges on the server to manage services.

### 1. Logging into the Server:

1. **Using Remote Desktop (RDP)**
   - Click on the Windows Start button, search for “Remote Desktop Connection”, and open it.
   - Enter the IP address or hostname of your Windows Server.
   - Provide your username and password when prompted.
   - Click "Connect".

2. **Using Direct Access (If you are at the physical location)**
   - Simply power on the machine if it's not already running.
   - Log in using your username and password.

### 2. Accessing Windows Server Services:

1. Once logged in, press `Windows + R` keys simultaneously to open the Run dialog.
   
2. Type `services.msc` and press `Enter`. This will open the Windows Services Manager.

### 3. Locate Solr Service:

1. In the Windows Services Manager, you'll see a list of all the services installed on the server.
   
2. Scroll through the list or type `Solr` to quickly navigate and locate the "Solr" service. It might be named something slightly different like "Apache Solr" or "Solr x.x.x" based on the version and installation specifics.

### 4. Start Solr Service:

If Solr is currently stopped and you want to start it:

1. Right-click on the Solr service.
   
2. From the context menu, select "Start".

3. You'll see the status column change to "Running" once the service is successfully started.

### 5. Stop Solr Service:

If Solr is currently running and you want to stop it:

1. Right-click on the Solr service.
   
2. From the context menu, select "Stop".

3. You'll see the status column change to "Stopped" or it might remain blank once the service is successfully stopped.

### 6. (Optional) View Logs:

If you want to check Solr logs:

1. Navigate to the Solr installation directory (e.g., `C:\solr-8.x.x` or wherever Solr is installed).
   
2. Open the `logs` directory.
   
3. Here, you'll find various log files. `solr.log` is the primary log file that records most of the operations and potential issues.

### Note:

- Always ensure that other dependent services or applications are aware when you're stopping or starting the Solr service. 
- Regularly back up your Solr data and configurations to avoid any potential data loss.

That's it! You've now learned how to start and stop Solr using Windows Services on a Windows Server. Make sure to always monitor the service and check logs periodically to ensure smooth operation.
