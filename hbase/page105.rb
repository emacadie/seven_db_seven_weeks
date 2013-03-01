import 'org.apache.hadoop.hbase.client.HTable'
import 'org.apache.hadoop.hbase.client.Put'

def jbytes( *args )
  args.map { |arg| arg.to_s.to_java_bytes }
end

#########################################################################3
def put_many( table_name, row, column_values )
    table = HTable.new( @hbase.configuration, table_name )
    p = Put.new( *jbytes( row ) )
    puts "column_values is a #{column_values.class}"
    column_values.each do |k,v|
        puts "Key is #{k} value is #{v}"
        p.add( *jbytes( k.split(":")[0], k.split(":")[1], v ) )
    end
    table.put( p )
end
=begin
2. Define your put_many() function by pasting it in the HBase shell, and then
call it like so:
hbase> put_many 'wiki', 'Some title', {
"text:" => "Some article text",
"revision:author" => "jschmoe",
"revision:comment" => "no comment" }
=end

