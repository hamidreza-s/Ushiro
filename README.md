# Ushiro

Ushiro is a URL shortener service prototype. It supports the following operations:
- **Create**: Take a long URL, generate a short URL and return it in response.
- **Lookup**: Redirect to the original long URL by getting an HTTP request of short URL.
- **Info**: Give information about the short URL, such as view-count and creation time.

## Quick Start

The service is written in Java 1.8, using [Jetty](https://en.wikipedia.org/wiki/Jetty_(web_server)) 
as the embedded HTTP server library and [Cassandra](https://en.wikipedia.org/wiki/Apache_Cassandra)
as the database. [Maven](https://en.wikipedia.org/wiki/Apache_Maven) is used as build tool.

After cloning the repository, using following commands the service can be built and tested. 
The commands are wrapped in a Makefile for simplicity.

```bash
$ git clone https://github.com/hamidreza-s/Ushiro.git
$ cd ushiro
$ make compile
$ make test
#=> ...
#=> Results :
#=> Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
```

Then, using the following commands a `jar` package of the project will be created and started.

```bash
$ make package
$ make run
#=> Ushiro has been started on 8080 port!
```

Now, using an HTTP client the availability of the service can be tested by following ping command.

```bash
$ curl -i localhost:8080/ping
#=> HTTP/1.1 200 OK
#=> Date: Sun, 25 Feb 2018 22:20:59 GMT
#=> Content-Length: 4
#=> Server: Jetty(9.4.z-SNAPSHOT)
```

## URL Shortener API

### Create
- Address: `http://{server-address}/create`
- Method: POST
- Request Body Parameter: `long-url`
- Response: JSON Object

Example:

```
$ curl -XPOST -d 'long-url=http://www.ea.com/en-gb/games/base-games' http://localhost:8080/create
#=> {
#=>     "key-url": "yGuhHD",
#=>     "long-url": "http://www.ea.com/en-gb/games/base-games",
#=>     "short-url": "http://localhost:8080/yGuhHD"
#=> }
```

### Lookup
- Address: `http://{server-address}/{short-url}`
- Method: GET
- Response: Redirect Code

Example:

```
$ curl -i http://localhost:8080/yGuhHD
#=> HTTP/1.1 302 Found
#=> Date: Sun, 25 Feb 2018 22:37:29 GMT
#=> Location: http://www.ea.com/en-gb/games/base-games
#=> Content-Length: 0
#=> Server: Jetty(9.4.z-SNAPSHOT)
```

### Info
- Address: `http://{server-address}/info/{short-url}`
- Method: GET
- Response: JSON Object

Example:

```
curl http://localhost:8080/info/yGuhHD
#=> {
#=>     "created-at": 1519597914951,
#=>     "key-url": "yGuhHD",
#=>     "long-url": "http://www.ea.com/en-gb/games/base-games",
#=>     "view-count": 1
#=> }
```

## Configuration

The service can be configured in `ushiro.properties` file which is located in the resource directory.
The testing scope has its own config file which is used by maven test command.

- HTTP Server
  - `http.port`: Listening HTTP port
  - `http.host`: Listening HTTP host
  - `http.idle.timeout`: Idle timeout of the HTTP listener
  
- Long URL
  - `long.url.valid.regex`: Regex pattern of the accepted long URLs
  - `long.url.valid.length.min`: Minimum length of the accepted long URLs
  - `long.url.valid.length.max`: Maximum length of the accepted long URLs

- Short URL
  - `short.url.chars`: Accepted character list of the short URLs
  - `short.url.length`: Accepted length of the short URLs

- Data
  - `data.persistent`: Boolean specifying if data must be persisted in database
  - `data.persistent.nodes`: Comma-separated list of Cassandra cluster nodes
  - `data.cache.size`: Size of the cache for URL data records
  - `data.cache.eviction.strategy`: Eviction strategy of data records cache

## Source Structure

The project contains four main categories; *server*, *data*, *cache*, and *utils*. In the source root
there is the *Application* class which is responsible for starting the service, and also the *Config* 
class which loads the configuration.

```
$ tree ushiro/src/main/java/io/github/ushiro/
  src/main/java/io/github/ushiro/
  ├── Application.java
  ├── Config.java
  ├── cache
  │   ├── AbstractCache.java
  │   ├── Cache.java
  │   ├── CacheFactory.java
  │   ├── LRUCache.java
  │   └── MRUCache.java
  ├── data
  │   ├── DataCache.java
  │   ├── DataDriver.java
  │   └── DataModel.java
  ├── server
  │   ├── CreateHandler.java
  │   ├── HttpServer.java
  │   ├── InfoHandler.java
  │   ├── LookupHandler.java
  │   └── PingHandler.java
  └── utils
      ├── RandomGenerator.java
      └── UrlValidator.java
```

- Server folder contains the HTTP server and API handlers.
- Data folder contains the model, cache instance, and database driver.
- Cache folder contains a cache interface with MRU and LRU implementations.
- Utils folder contains two class for generating and validating URLs.

## High Availability

To make the service high available, it can be deployed on more than one instance, connecting to
a Cassandra cluster, and sitting behind an HTTP load balancer. Since the service is state-less and
everything is persisted in the database, adding more nodes increases the availability of the service. 
Here is a simple proposal for the service deployment.

```
                    +------------+     +----------+
        +-----------+ Ushiro # 1 <-----+          |
        |           +------------+     |          |
+-------v----+                         |          |
| ------------+     +------------+     |   HTTP   |
| | Cassandra <-----+ Ushiro # 2 <-----+   Load   |
+++  Cluster  |     +------------+     | Balancer |
  +-----^-----+                        |          |
        |           +------------+     |          |
        +-----------+ Ushiro # N <-----+          |
                    +------------+     +----------+ 

```

## Name Origin

Ushiro is a Japanese name meaning "behind" and "back". Also, Its pronunciation reminds me of **U**rl **Sh**ortener.
