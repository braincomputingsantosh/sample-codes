import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.microsoft.sharepoint.SPRestService;
import com.microsoft.sharepoint.SPServiceFactory;

public class SharePointConnector implements Callable {

    private String siteUrl;
    private String username;
    private String password;

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        // Create SharePoint REST service instance
        SPServiceFactory factory = new SPServiceFactory();
        SPRestService spService = factory.createRestService(siteUrl, username, password);

        // Perform operations on SharePoint
        // For example, retrieve a list of documents
        String documents = spService.getDocuments();

        return documents;
    }
}
