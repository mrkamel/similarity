
# similarity

Similarity is an image similarity search server built on top of Lire.
The images can be filtered using a query and are afterwards optically ranked.
Similarity provides an easy to use REST interface and returns the search results as XML.
If you're familiar with Ruby and the RestClient gem, check out the following examples.

First, you need to start the server:

<pre>
$ sh start.sh
</pre>

## Index and query using cURL

After you started similarity, you can index some images using cURL:

<pre>
$ curl http://localhost:8984/uploads -F file=@/path/to/file.jpg -F "text=keyword1 keyword2" -F id=1
...
</pre>

Finally, you can search for similary images:

<pre>
$ curl http://localhost:8984/search -F file=@/path/to/reference.jpg -F q=keyword1 -F start=0 -F limit=10
&lt;response num="2"&gt;
  &lt;result&gt;
    &lt;id&gt;1&lt;/id&gt;
    &lt;identifier&gt;/path/to/file.jpg&lt;/identifier&gt;
    &lt;text&gt;keyword1 keyword2&lt;/text&gt;
    &lt;score&gt;1.0&lt;/score&gt;
  &lt;/result&gt;
  ...
&lt;/response&gt;
</pre>

## Index and query using RestClient

After you started similarity, you can index some images using e.g. Ruby and the RestClient gem:

<pre>
gem install rest-client

$ irb
irb> require "rubygems"
irb> require "rest-client"
irb> RestClient.post("http://localhost:8984/uploads",
  :file => File.new("/path/to/file.jpg"), :text => "keyword1 keyword2 ...", :id => 1) 
...
</pre>

Finally, you can search for similar images:

<pre>
irb> puts RestClient.post("http://localhost:8984/search", :file => File.new("reference.jpg"),
  :q => "keyword1", :start => 0, :limit => 10)
&lt;response num="1"&gt;
  &lt;result&gt;
    &lt;id&gt;1&lt;/id&gt;
    &lt;identifier&gt;/path/to/file.jpg&lt;/identifier&gt;
    &lt;text&gt;keyword1 keyword2f&lt;/text&gt;
    &lt;score&gt;1.0&lt;/score&gt;
  &lt;/result&gt;
  ...
&lt;/response&gt;
=> nil
</pre>

