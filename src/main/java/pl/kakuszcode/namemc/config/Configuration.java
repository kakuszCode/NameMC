package pl.kakuszcode.namemc.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Variable;
import pl.kakuszcode.namemc.database.DatabaseEnum;

import java.util.Arrays;
import java.util.List;

public class Configuration extends OkaeriConfig {
    @Variable("settings.databaseType")
    @CustomKey("settings.databaseType")
    @Comment({"Są: ", "H2", "MongoDB", "MySQL", "PostgreSQL"})
    private final DatabaseEnum databaseEnum = DatabaseEnum.H2;
    @Variable("settings.database.h2.jdbc")
    @CustomKey("settings.database.h2.jdbc")
    private final String h2Jdbc = "jdbc:h2:~/namemc";
    @Variable("settings.database.mysql.jdbc")
    @CustomKey("settings.database.mysql.jdbc")
    private final String mysqlJdbc = "jdbc:mysql://localhost:3306/nanemc";
    @Variable("settings.database.mysql.username")
    @CustomKey("settings.database.mysql.username")
    private final String mysqlUsername = "root";
    @Variable("settings.database.mysql.password")
    @CustomKey("settings.database.mysql.password")
    private final String mysqlPassword = "";
    @Variable("settings.database.mongodb.jdbc")
    @CustomKey("settings.database.mongodb.jdbc")
    private final String mongodbJdbc = "mongodb://localhost";
    @Variable("settings.database.postgresql.jdbc")
    @CustomKey("settings.database.postgresql.jdbc")
    private final String postGreSQLJdbc = "jdbc:postgresql://localhost:5432/nanemc";
    @Variable("settings.database.postgresql.username")
    @CustomKey("settings.database.postgresql.username")
    private final String postGreSQLUsername = "root";
    @Variable("settings.database.postgresql.password")
    @CustomKey("settings.database.postgresql.password")
    private final String postGreSQLPassword = "";
    @Variable("settings.namemc.serverip")
    @CustomKey("settings.namemc.serverip")
    private final String serverIP = "shinemc.pl";

    @Variable("settings.nanemc.isReward")
    @CustomKey("settings.nanemc.isReward")
    private final String messageIsReward = "&4Błąd: &cJuż posiadasz nagrodę!";
    @Variable("settings.namemc.isPending")
    @CustomKey("settings.namemc.isPending")
    private final String messageIsPending = "&4Błąd: &cTwoje zgłoszenie wcześniejsze jest w oczekiwaniu!";
    @Variable("settings.namemc.isAccept")
    @CustomKey("settings.namemc.isAccept")
    private final String messageIsAccept = "&2Sukces: &aNagroda została przyznana za NameMC";
    @Variable("settings.nanemc.isNotPremium")
    @CustomKey("settings.nanemc.isNotPremium")
    private final String messageIsNotPremium = "&4Błąd: &cNie jestes graczem premium!";
    @Variable("settings.namemc.isNotLiked")
    @CustomKey("settings.namemc.isNotLiked")
    private final String messageIsNotLiked = "&4Błąd: &cNie masz polubienia na NameMC";
    @Variable("settings.namemc.commandsOnAccept")
    @CustomKey("settings.namemc.commandsOnAccept")
    @Comment({"Placeholder:", "%player%"})
    private final List<String> commandsOnAccept = Arrays.asList("broadcast %player% odebrał nagrodę za NameMC", "give %player% diamond 64");

    public String getServerIP() {
        return serverIP;
    }


    public String getMessageIsNotLiked() {
        return messageIsNotLiked;
    }


    public String getMessageIsNotPremium() {
        return messageIsNotPremium;
    }


    public String getMongodbJdbc() {
        return mongodbJdbc;
    }


    public String getPostGreSQLUsername() {
        return postGreSQLUsername;
    }


    public String getPostGreSQLPassword() {
        return postGreSQLPassword;
    }


    public String getMysqlUsername() {
        return mysqlUsername;
    }


    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public String getPostGreSQLJdbc() {
        return postGreSQLJdbc;
    }


    public String getMysqlJdbc() {
        return mysqlJdbc;
    }


    public DatabaseEnum getDatabaseEnum() {
        return databaseEnum;
    }


    public String getH2Jdbc() {
        return h2Jdbc;
    }


    public String getMessageIsReward() {
        return messageIsReward;
    }


    public String getMessageIsPending() {
        return messageIsPending;
    }


    public String getMessageIsAccept() {
        return messageIsAccept;
    }



    public List<String> getCommandsOnAccept() {
        return commandsOnAccept;
    }

}
