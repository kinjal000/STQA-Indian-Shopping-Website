#!/bin/bash
cd "$(dirname "$0")"
echo "Starting ShopEase India..."
mvn clean compile || { echo "Build failed. Check the error above."; read -p "Press Enter to close..."; exit 1; }
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass="stqa.StartWebsite"
