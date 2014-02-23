Based (heavily) on code from https://github.com/tomstrummer/HBaseBuilder   
To clean:   
gradle clean     
To build:   
gradle build   

From http://permalink.gmane.org/gmane.comp.java.hadoop.hbase.user/15328:    
 Basically, BatchUpdate becomes Put and RowResult becomes Result.   

deprecated javadoc:  http://people.apache.org/~jdcryans/hbase-0.20.5-candidate-3/hbase-0.20.5/docs/api/org/apache/hadoop/hbase/io/BatchUpdate.html   

HBase javadoc: http://hbase.apache.org/apidocs/index.html?org/apache/hadoop/hbase/client/package-summary.html    

Another project: https://github.com/fullcontact/hbase-groovy-tools   

gradle runGroovy -PmainClass="info.shelfunit.hbase.HBaseRunner"  -PmainArgs="-log4jConfigFile  /home/ericm/github/seven_db_seven_weeks/hbase/groovy_hbase_seven_days/src/main/groovy/config.groovy -inputFile Hello"   

To run HBase:   
/home/ericm/tmp/hbase/hbase-0.94.4/bin/start-hbase.sh   
/home/ericm/tmp/hbase/hbase-0.94.4/bin/stop-hbase.sh   
Shell at /home/ericm/tmp/hbase/hbase-0.94.4/bin/hbase shell   
