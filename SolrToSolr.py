import requests

# Configuration
source_collection = "source_collection"
target_collection = "target_collection"
solr_url = "http://localhost:8983/solr"  # Update with your Solr server URL
batch_size = 1000  # Number of documents to copy in each batch

# Query to retrieve documents from the source collection
query = "*:*"

# Initialize session
session = requests.Session()

# Query and copy documents in batches
offset = 0
while True:
    # Query Solr to get a batch of documents from the source collection
    params = {
        "q": query,
        "start": offset,
        "rows": batch_size,
    }
    source_url = f"{solr_url}/{source_collection}/select"
    response = session.get(source_url, params=params)

    if response.status_code != 200:
        print(f"Failed to retrieve documents from the source collection. Status code: {response.status_code}")
        break

    documents = response.json().get("response", {}).get("docs", [])
    if not documents:
        break

    # Index the batch of documents into the target collection
    target_url = f"{solr_url}/{target_collection}/update"
    headers = {"Content-Type": "application/json"}
    update_data = {"add": documents}
    update_response = session.post(target_url, json=update_data, headers=headers)

    if update_response.status_code != 200:
        print(f"Failed to copy documents to the target collection. Status code: {update_response.status_code}")
        break

    print(f"Copied {len(documents)} documents from offset {offset}")
    offset += batch_size

# Commit the changes to the target collection
commit_url = f"{solr_url}/{target_collection}/update?commit=true"
commit_response = session.post(commit_url)

if commit_response.status_code != 200:
    print(f"Failed to commit changes to the target collection. Status code: {commit_response.status_code}")
else:
    print("Successfully copied and committed documents to the target collection")

# Close the session
session.close()
