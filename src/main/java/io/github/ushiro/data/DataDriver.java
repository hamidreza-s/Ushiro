package io.github.ushiro.data;

import com.datastax.driver.core.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import io.github.ushiro.Config;

public class DataDriver {

    private static Cluster cluster;
    private static Session session;
    private static Map<String, DataModel> localData;

    public static void start() {
        if(Config.isDataPersistent()) {
            if (cluster != null && session != null)
                return;

            cluster = Cluster.builder().addContactPointsWithPorts(getNodes()).build();
            session = cluster.connect();
            createSchema();
        } else {
            localData = new HashMap<String, DataModel>();
        }
    }

    public static void stop() {
        if(Config.isDataPersistent()) {
            session.close();
            cluster.close();
        }
    }

    public static void store(DataModel dataModel) {
        if(Config.isDataPersistent()) {
            String insertString = "INSERT INTO ushiro.url "
                    + "(key_url, long_url, created_at, view_count) "
                    + "VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = session.prepare(insertString);
            BoundStatement boundStatement = new BoundStatement(preparedStatement);
            Statement statement = boundStatement.bind(
                    dataModel.getKeyUrl(),
                    dataModel.getLongUrl(),
                    dataModel.getCreatedAt(),
                    dataModel.getViewCount()
            );
            session.execute(statement);
        } else {
            localData.put(dataModel.getKeyUrl(), dataModel);
        }
    }

    public static DataModel retrieve(String keyUrl) {
        if(Config.isDataPersistent()) {
            String queryString = "SELECT key_url, long_url, created_at, view_count "
                    + "FROM ushiro.url "
                    + "WHERE key_url = ?;";
            PreparedStatement preparedStatement = session.prepare(queryString);
            BoundStatement boundStatement = new BoundStatement(preparedStatement);
            Statement statement = boundStatement.bind(keyUrl);
            ResultSet resultSet = session.execute(statement);
            Row resultRow = resultSet.one();
            if(resultRow != null) {
                String longUrl = resultRow.getString("long_url");
                long createAt = resultRow.getLong("created_at");
                int viewCount = resultRow.getInt("view_count");
                return new DataModel(keyUrl, longUrl, createAt, viewCount);
            } else {
                return null;
            }
        } else {
            return localData.get(keyUrl);
        }
    }

    private static void createSchema() {
        session.execute("CREATE KEYSPACE IF NOT EXISTS ushiro "
                + "WITH replication = "
                + "{'class':'SimpleStrategy', 'replication_factor':1};");

        session.execute("CREATE TABLE IF NOT EXISTS ushiro.url ("
                + "key_url TEXT PRIMARY KEY,"
                + "long_url TEXT,"
                + "created_at BIGINT,"
                + "view_count INT);");
    }

    private static Collection<InetSocketAddress> getNodes() {
        String nodesString = Config.getDataPersistentNodes();
        Collection<InetSocketAddress> nodesList = new ArrayList<InetSocketAddress>();

        for(String server : nodesString.split(",")) {
            String[] address = server.split(":");
            String host = address[0];
            int port = Integer.parseInt(address[1]);
            nodesList.add(new InetSocketAddress(host, port));
        }

        return nodesList;
    }

}
