"# githubmetric" 
# Install:
	Run mvn compile

	Run mvn exec:java -Dexec.mainClass="com.quodai.githubmetric.main.HealthScoreCalculator" -Dexec.args="arg0 arg1" with arg0 is start time to download, arg1 is end time to download. Example: mvn exec:java -Dexec.mainClass="com.quodai.githubmetric.main.HealthScoreCalculator" -Dexec.args="2019-01-01T00:00:00Z 2019-01-01T05:00:00Z"
# Technical decision
1. Libraries:

		org.apache.httpcomponents.httpclient to call rest
		org.apache.commons.commons-csv to print csv file
		com.fasterxml.jackson.core.jackson-databind to parse String to object
		commons-io to handle file, org.apache.commons.commons-lang3 to handle String
2. Performance decision:
 
   Due to big file downloaded, I decide to download all the file first, then extract it second, then handle file to have raw data, then calculate score and finnaly print csv. The result of before step is the input of later step.
   Raw data is handled by read file line by line, add data to an hashmap with key is repo id, and value is all data collected for this repo, additional, I also collect max commit by getting commit of current repo compared with current max commit
   
   In HealthScoreCalculationService, I create a data with TreeMap to return and a temporary HashMap. Both this TreeMap and HashMap is have same structure, key is score, value is a batch of repo with same score. TreeMap is used to sort, HashMap is used to check repo same score or not, and get repo by using score. With sorting, TreeMap have Log(N), while HashMap is Log(1) for finding. 
   
