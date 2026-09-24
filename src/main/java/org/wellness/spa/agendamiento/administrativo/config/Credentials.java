package main.java.org.wellness.spa.agendamiento.administrativo.config;

public class Credentials {

    public static final String DATA_BASE = System.getenv("DATA_BASE");
    public static final String URL_MYSQL_DB =System.getenv("DB_URL") + "spa_agendamiento_administrativo_in4bm";
    public static final String USER_DB =System.getenv("DB_USER");
    public static final String PASS_DB =System.getenv("DB_PASSWORD");

}
