"# githubmetric" 
# Install:
Run mvn compile, then run mvn exec:java -Dexec.mainClass="com.quodai.githubmetric.main.HealthScoreCalculator" -Dexec.args="arg0 arg1" with arg0 is request url, arg1 is folder to store file csv file as well as downloaded file. Example: mvn exec:java -Dexec.mainClass="com.quodai.githubmetric.main.HealthScoreCalculator" -Dexec.args="https://data.gharchive.org/2019-01-01-15.json.gz  'D:\New folder\'"
# Technical decision
1. Libraries:

		org.apache.httpcomponents.httpclient to call rest
		org.apache.commons.commons-csv to print csv file
		com.fasterxml.jackson.core.jackson-databind to parse String to object
		commons-io to handle file, org.apache.commons.commons-lang3 to handle String
2. Performance decision:
i. Due to big file downloaded, I decide to download all the file first, then extract it, then read 1 by one line.
ii. 
