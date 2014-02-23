package info.shelfunit.hbase

import groovy.util.CliBuilder
import groovy.util.ConfigSlurper

import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.EnhancedPatternLayout
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException; // added
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
// import org.apache.hadoop.hbase.client.RowLock;
// import org.apache.hadoop.hbase.client.Scanner;
import org.apache.hadoop.hbase.client.ResultScanner;
// import org.apache.hadoop.hbase.io.BatchUpdate;
import org.apache.hadoop.hbase.client.Put;
// import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.Cell;

// import org.apache.hadoop.hbase.io.RowResult;
import org.apache.hadoop.hbase.client.Result;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;

import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnectionManager
import org.apache.hadoop.hbase.client.HConnection
import org.apache.hadoop.hbase.TableName

class HBaseRunner {

    def static getLogger( options ) {
        println( "Here is options.log4jConfigFile: ${options.log4jConfigFile}" )
        Logger log = Logger.getInstance( getClass() )
        if ( options.log4jConfigFile ) {
            println( "Got some log config" )
            def config = new ConfigSlurper().parse( new File( options.log4jConfigFile ).toURL() )
            PropertyConfigurator.configure( config.toProperties() )
        } else {
            // provide a default logger
            log.level = Level.INFO
            log.addAppender( new DailyRollingFileAppender( new EnhancedPatternLayout( EnhancedPatternLayout.TTCC_CONVERSION_PATTERN ), "xml.transform.log", "'.'yyyy-MM-dd" ) )
        }
        return log
    } // end method getLogger
    
    def static runStuff001( log ) {
        println( "In runStuff001" )
        def hbase = HBaseBuilder.connect()
        log.info( "hbase is a ${hbase.getClass().getName()}" )
    }
    
    def static runStuff002( log ) {
        // Configuration conf = HBaseConfiguration.create();
        
        Configuration conf = HBaseConfiguration.create();
        log.info( "Got the conf" )
       
        HBaseAdmin admin = new HBaseAdmin(conf);
        log.info( "got the admin" )
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("people"));
        log.info( "got the table descriptor" )
        tableDescriptor.addFamily(new HColumnDescriptor("personal"));
        log.info( "added column personal" )
        tableDescriptor.addFamily(new HColumnDescriptor("contactinfo"));
        log.info( "added column contact info" )
        tableDescriptor.addFamily(new HColumnDescriptor("creditcard"));
        log.info( "added column credit card" )
        admin.createTable(tableDescriptor);
        log.info( "created table" )
      
        // HConnection connection = HConnectionManager.createConnection( conf );
        
        HTable table = new HTable(conf, "wiki");
        table.setAutoFlush(false);
        table.setWriteBufferSize(2 * 1024 * 1024); // 2 Mb
        // ... do useful stuff
        table.close()
       
    }
    
    def static void main( String[] args ) {
        def cli = new CliBuilder( usage: 'HBaseRunner' )
        cli.inputFile( args: 1, argName: 'file path', 'The input file, required argument' )
        cli.outputFile( args: 1, argName: 'file path', 'The output file, required argument' )
        cli.log4jConfigFile( args: 1, argName: 'log config file', 'Path to Log4J config file, optional argument' )
        println( "Here are the args: ${args}" )
        cli.usage()
        def options = cli.parse( args )
        println( "Here is options.log4jConfigFile: ${options.log4jConfigFile}" )
        println( "Here is options.inputFile: ${options.inputFile}" )
        
        def log = getLogger( options )
        runStuff002( log )
        def classpath = System.properties["java.class.path"]
        println( "classpath: ${classpath}" )
        
        
    } // end method main
    
} // end class HBaseRunner

