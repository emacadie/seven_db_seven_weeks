package info.shelfunit.hbase

import groovy.util.CliBuilder
import groovy.util.ConfigSlurper

import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.EnhancedPatternLayout
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

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
        runStuff001( log )
        
        
        
    } // end method main
    
} // end class HBaseRunner

