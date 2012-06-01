
# similarity

Similarity is an image similarity search server built on top of Lire.
The images can be filtered using a query and are afterwards optically ranked.
Similarity provides an easy to use REST interface and returns the search results as XML.
If you're familiar with Ruby and the RestClient gem, check out the following examples.

First, you need to start the server:

<pre>
$ sh start.sh
</pre>

Afterwards you can index some images using e.g. Ruby and the RestClient gem:

<pre>
gem install rest-client

$ irb
irb> require "rubygems"
irb> require "rest-client"
irb> RestClient.post("http://localhost:8984/uploads", :file => File.new("test.jpg"), :text => "keywords", :id => 1) 
</pre>

Finally, you can search for similar images:

<pre>
irb> RestClient.post("http://localhost:8984/search", :file => File.new("reference.jpg"), :q => "keywords", :start => 0, :limit => 2)
=> "&lt;response num='291'&gt;\n&lt;result&gt;&lt;id&gt;1&lt;/id&gt;&lt;identifier&gt;test.jpg&lt;/identifier&gt;&lt;text&gt;keywords&lt;/text&gt;&lt;score&gt;0.46990407&gt;/score&gt;&lt;/result&gt;...&lt;/response&gt;\n"
</pre>

