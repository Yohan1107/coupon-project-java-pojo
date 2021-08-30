package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * ConnectionPool class - manage our connection with the data base
 */
public class ConnectionPool {
    private static final int NUM_OF_CONS = 15;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    /**
     * Constructor private for singleton class
     *
     * @throws SQLException enable to throw exception
     */
    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    /**
     * Method to get instance of this class, if we already have an instance, it's will return this instance
     *
     * @return connectionPool object
     */
    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException err) {
                        System.out.println(err.getMessage());
                    }
                }
            }
        }
        return instance;
    }

    /**
     * Method to get a connection
     *
     * @return connection object
     * @throws InterruptedException enable to throw exception
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * Method to return a connection
     *
     * @param connection the connection we returned
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    /**
     * Method to open all the connections
     *
     * @throws SQLException enable to throw exception
     */
    private void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONS; index += 1) {
            //load the configuration file and update the data for connection
            GrooponDataBaseManager.getConfiguration();
            //make the connection ......
            Connection connection = DriverManager.getConnection(GrooponDataBaseManager.url, GrooponDataBaseManager.username, GrooponDataBaseManager.password);
            connections.push(connection);
        }
    }

    /**
     * Method to close all the connections
     *
     * @throws InterruptedException enable to throw exception
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            connections.removeAllElements();
            instance = null;
        }
    }
}
