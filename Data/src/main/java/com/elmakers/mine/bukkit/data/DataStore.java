package com.elmakers.mine.bukkit.data;

import java.util.List;
import java.util.logging.Logger;

/**
 * An abstract class representing a data store.
 * 
 * @author NathanWolf
 * 
 */
public abstract class DataStore {
    protected static Logger log = Logger.getLogger("Minecraft");

    protected static boolean logStoreAccess = false;

    public static Logger getLogger() {
        return log;
    }

    public static void logStoreAccess(String message) {
        if (logStoreAccess) {
            log.info("Persistence: " + message);
        }
    }

    public static void logStoreAccess(String message, int rowCount) {
        if (logStoreAccess) {
            log.info("Persistence: " + String.format(message, rowCount));
        }
    }

    protected String schema;

    public DataStore(String schema) {
        this.schema = schema;
    }

    /**
     * Clear a table, except for the objects contained in the specified
     * DataTable
     * 
     * This is used to remove deleted objects from a data store.
     * 
     * @param table The table to clear, containing the rows to keep
     * 
     * @return true if success
     */
    public abstract boolean clear(DataTable table);

    /**
     * Clear a table of data for the specified ids, keeping the data included in
     * "table".
     * 
     * This is used to clear list sub-tables in order to handle removed items.
     * 
     * Only rows with a matching primary id will be dropped, and then only if
     * they are not specified in the passed-in table.
     * 
     * @param table The table to clear, containing the rows to keep
     * @param ids A list of primary ids of objects to clear
     * 
     * @return true if success
     */
    public abstract boolean clearIds(DataTable table, List<Object> ids);

    /**
     * Connect to the data store represented by this store's schema
     * 
     * @return true if success
     */
    public abstract boolean connect();

    public void copyTable(String sourceTable, String destinationTable) {}

    /**
     * Will attempt to create a table- check to see if the table exists before
     * calling this.
     * 
     * @param table The table definition
     * 
     * @return true if success
     * 
     * @see #tableExists(DataTable)
     * @see #validateTable(DataTable)
     * 
     */
    public abstract boolean create(DataTable table);

    /**
     * Disconnect from the connected store
     */
    public abstract void disconnect();

    /**
     * Completely drop a table, allowing it to be re-created.
     * 
     * @param tableName the name of the table to drop
     * @return true on success
     */
    public abstract boolean drop(String tableName);

    /**
     * Return the table header (column definitions) without querying the table
     * for data.
     * 
     * @param tableName The table to get the header of
     * 
     * @return a DataTable containing one DataRow, representing this table's columns
     */
    public abstract DataTable getTableHeader(String tableName);

    /**
     * Initialize this data store.
     * 
     * This is called internally by Persistence.
     * 
     * @param schema The schema this data store connects to
     * @param p The Persistence instance this data store should use.
     */
    public void initialize(String schema) {
        this.schema = schema;
    }

    /**
     * Check to see if this is a read-only data store
     * 
     * @return true if the store cannot be written to
     */
    public boolean isReadOnly() {
        return false;
    }

    /**
     * Load a table into memory.
     * 
     * Assumes that the table already exists.
     * 
     * @param table The table to load
     * 
     * @return true if success
     */
    public abstract boolean load(DataTable table);

    /**
     * Save a table to the data store.
     * 
     * Assumes that the table already exists.
     * 
     * @param table the table
     * 
     * @return true if success
     */
    public abstract boolean save(DataTable table);

    /**
     * Check to see if the specified table exists.
     * 
     * @param tableName The name of the table to check
     * 
     * @return true if the table exists
     */
    public abstract boolean tableExists(String tableName);

}
