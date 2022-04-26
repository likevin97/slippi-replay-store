#!/bin/sh

# Set the metadata server to the get projct id
PROJECTID=$(curl -s "http://metadata.google.internal/computeMetadata/v1/project/project-id" -H "Metadata-Flavor: Google")
BUCKET=$(curl -s "http://metadata.google.internal/computeMetadata/v1/instance/attributes/BUCKET" -H "Metadata-Flavor: Google")

echo "Project ID: ${PROJECTID} Bucket: ${BUCKET}"

# Get the files we need
gsutil cp gs://${BUCKET}/*.jar .

# Set current public IP to domain
IP=$(curl -s api.ipify.org) 
gcloud dns record-sets delete www.slippi-replay.store. \
    --type=A \
    --zone=slippi-replay-store-zone
gcloud dns record-sets create www.slippi-replay.store. \
    --rrdatas=$IP \
    --ttl=TTL \
    --type=A \
    --zone=slippi-replay-store-zone

# Install dependencies
apt-get update
apt-get -y --force-yes install openjdk-11-jdk

# Make Java 11 default
update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64/jre/bin/java

# Start server
authbind --deep java -jar slippi-replay-store-0.0.1-SNAPSHOT.jar
