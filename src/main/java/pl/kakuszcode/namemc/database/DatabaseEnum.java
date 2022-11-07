package pl.kakuszcode.namemc.database;

import pl.kakuszcode.namemc.database.provider.H2Provider;
import pl.kakuszcode.namemc.database.provider.MongoDBProvider;
import pl.kakuszcode.namemc.database.provider.MySQLProvider;
import pl.kakuszcode.namemc.database.provider.PostgreSQLProvider;

public enum DatabaseEnum {
    MONGODB(MongoDBProvider.class), MYSQL(MySQLProvider.class), POSTGRESQL(PostgreSQLProvider.class), H2(H2Provider.class);

    private final Class<? extends Database> databaseClass;

    DatabaseEnum(Class<? extends Database> databaseClass) {
        this.databaseClass = databaseClass;
    }

    public Class<? extends Database> getDatabaseClass() {
        return databaseClass;
    }
}
