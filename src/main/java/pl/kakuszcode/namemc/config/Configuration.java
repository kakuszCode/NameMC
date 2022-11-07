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
    private DatabaseEnum databaseEnum = DatabaseEnum.H2;
    @Variable("settings.database.h2.jdbc")
    @CustomKey("settings.database.h2.jdbc")
    private String h2Jdbc = "jdbc:h2:~/namemc";
    @Variable("settings.database.mysql.jdbc")
    @CustomKey("settings.database.mysql.jdbc")
    private String mysqlJdbc = "jdbc:mysql://localhost:3306/nanemc";
    @Variable("settings.database.mysql.username")
    @CustomKey("settings.database.mysql.username")
    private String mysqlUsername = "root";
    @Variable("settings.database.mysql.password")
    @CustomKey("settings.database.mysql.password")
    private String mysqlPassword = "";
    @Variable("settings.database.mongodb.jdbc")
    @CustomKey("settings.database.mongodb.jdbc")
    private String mongodbJdbc = "mongodb://localhost";
    @Variable("settings.database.postgresql.jdbc")
    @CustomKey("settings.database.postgresql.jdbc")
    private String postGreSQLJdbc = "jdbc:postgresql://localhost:5432/nanemc";
    @Variable("settings.database.postgresql.username")
    @CustomKey("settings.database.postgresql.username")
    private String postGreSQLUsername = "root";
    @Variable("settings.database.postgresql.password")
    @CustomKey("settings.database.postgresql.password")
    private String postGreSQLPassword = "";
    @Variable("settings.namemc.serverip")
    @CustomKey("settings.namemc.serverip")
    private String serverIP = "shinemc.pl";

    @Variable("settings.nanemc.isReward")
    @CustomKey("settings.nanemc.isReward")
    private String messageIsReward = "&4Błąd: &cJuż posiadasz nagrodę!";
    @Variable("settings.namemc.isPending")
    @CustomKey("settings.namemc.isPending")
    private String messageIsPending = "&4Błąd: &cTwoje zgłoszenie wcześniejsze jest w oczekiwaniu!";
    @Variable("settings.namemc.isAccept")
    @CustomKey("settings.namemc.isAccept")
    private String messageIsAccept = "&2Sukces: &aNagroda została przyznana za NameMC";
    @Variable("settings.nanemc.isNotPremium")
    @CustomKey("settings.nanemc.isNotPremium")
    private String messageIsNotPremium = "&4Błąd: &cNie jestes graczem premium!";
    @Variable("settings.namemc.isNotLiked")
    @CustomKey("settings.namemc.isNotLiked")
    private String messageIsNotLiked = "&4Błąd: &cNie masz polubienia na NameMC";
    @Variable("settings.namemc.commandsOnAccept")
    @CustomKey("settings.namemc.commandsOnAccept")
    @Comment({"Placeholder:", "%player%"})
    private List<String> commandsOnAccept = Arrays.asList("broadcast %player% odebrał nagrodę za NameMC", "give %player% diamond 64");

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getMessageIsNotLiked() {
        return messageIsNotLiked;
    }

    public void setMessageIsNotLiked(String messageIsNotLiked) {
        this.messageIsNotLiked = messageIsNotLiked;
    }

    public String getMessageIsNotPremium() {
        return messageIsNotPremium;
    }

    public void setMessageIsNotPremium(String messageIsNotPremium) {
        this.messageIsNotPremium = messageIsNotPremium;
    }

    public String getMongodbJdbc() {
        return mongodbJdbc;
    }

    public void setMongodbJdbc(String mongodbJdbc) {
        this.mongodbJdbc = mongodbJdbc;
    }

    public String getPostGreSQLUsername() {
        return postGreSQLUsername;
    }

    public void setPostGreSQLUsername(String postGreSQLUsername) {
        this.postGreSQLUsername = postGreSQLUsername;
    }

    public String getPostGreSQLPassword() {
        return postGreSQLPassword;
    }

    public void setPostGreSQLPassword(String postGreSQLPassword) {
        this.postGreSQLPassword = postGreSQLPassword;
    }

    public String getMysqlUsername() {
        return mysqlUsername;
    }

    public void setMysqlUsername(String mysqlUsername) {
        this.mysqlUsername = mysqlUsername;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }

    public String getPostGreSQLJdbc() {
        return postGreSQLJdbc;
    }

    public void setPostGreSQLJdbc(String postGreSQLJdbc) {
        this.postGreSQLJdbc = postGreSQLJdbc;
    }

    public String getMysqlJdbc() {
        return mysqlJdbc;
    }

    public void setMysqlJdbc(String mysqlJdbc) {
        this.mysqlJdbc = mysqlJdbc;
    }

    public DatabaseEnum getDatabaseEnum() {
        return databaseEnum;
    }

    public void setDatabaseEnum(DatabaseEnum databaseEnum) {
        this.databaseEnum = databaseEnum;
    }

    public String getH2Jdbc() {
        return h2Jdbc;
    }

    public void setH2Jdbc(String h2Jdbc) {
        this.h2Jdbc = h2Jdbc;
    }

    public String getMessageIsReward() {
        return messageIsReward;
    }

    public void setMessageIsReward(String messageIsReward) {
        this.messageIsReward = messageIsReward;
    }

    public String getMessageIsPending() {
        return messageIsPending;
    }

    public void setMessageIsPending(String messageIsPending) {
        this.messageIsPending = messageIsPending;
    }

    public String getMessageIsAccept() {
        return messageIsAccept;
    }

    public void setMessageIsAccept(String messageIsAccept) {
        this.messageIsAccept = messageIsAccept;
    }

    public List<String> getCommandsOnAccept() {
        return commandsOnAccept;
    }

    public void setCommandsOnAccept(List<String> commandsOnAccept) {
        this.commandsOnAccept = commandsOnAccept;
    }
}
