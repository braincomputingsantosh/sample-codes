package com.example;

import org.mule.runtime.core.api.event.BaseEvent;

public class SharePointFileProcessor {

    public BaseEvent processFile(BaseEvent event) {
        // Extract file content from the event
        InputStream fileContent = event.getMessage().getPayload().getValue();

        // Process the InputStream as required (e.g., save it to a local directory)

        // Return the event (or modify as needed)
        return event;
    }
}
