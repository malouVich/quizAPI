package app.config;

import app.entities.Answer;
import app.entities.Question;
import app.security.entities.Role;
import app.security.entities.User;
import app.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateConfig {

    private static EntityManagerFactory emf;
    private static EntityManagerFactory emfTest;
    private static Boolean isTest = false;

    public static void setTest(Boolean test) {
        isTest = test;
    }

    public static Boolean getTest() {
        return isTest;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            emf = createEMF(getTest());
        return emf;
    }

    public static EntityManagerFactory getEntityManagerFactoryForTest() {
        if (emfTest == null){
            setTest(true);
            emfTest = createEMF(getTest());  // No DB needed for test
        }
        return emfTest;
    }

    // TODO: IMPORTANT: Add Entity classes here for them to be registered with Hibernate
    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Answer.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
    }

    private static EntityManagerFactory createEMF(boolean forTest) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            // Set the properties
            setBaseProperties(props);
            if (forTest) {
                props = setTestProperties(props);
            } else if (System.getenv("DEPLOYED") != null) {
                props=setDeployedProperties(props);
            } else {
                props = setDevProperties(props);
            }
            System.out.println("FINAL JDBC URL: " + props.getProperty("hibernate.connection.url"));
            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            EntityManagerFactory emf = sf.unwrap(EntityManagerFactory.class);
            return emf;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Properties setBaseProperties(Properties props) {
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "true");
        return props;
    }


//    private static Properties setDeployedProperties(Properties props) {
//        props.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        String DBName = System.getenv("DB_NAME");
//        props.setProperty("hibernate.connection.url", System.getenv("CONNECTION_STR") + DBName);
//        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
//        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
//        System.out.println("CONNECTION_STR: " + System.getenv("CONNECTION_STR"));
//        System.out.println("DB_NAME: " + DBName);
//
//        return props;
//    }

    private static Properties setDeployedProperties(Properties props) {
        String connectionStr = System.getenv("CONNECTION_STR");
        String DBName = System.getenv("DB_NAME");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        // Debug-udskrifter for at bekræfte indholdet
        System.out.println("CONNECTION_STR: " + connectionStr);
        System.out.println("DB_NAME: " + DBName);
        System.out.println("FINAL JDBC URL: " + connectionStr + DBName);

        props.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.setProperty("hibernate.connection.url", connectionStr + DBName);
        props.setProperty("hibernate.connection.username", username);
        props.setProperty("hibernate.connection.password", password);

        return props;
    }



    private static Properties setDevProperties(Properties props) {
        String DBName = Utils.getPropertyValue("DB_NAME", "config.properties");
        String DB_USERNAME = Utils.getPropertyValue("DB_USERNAME", "config.properties");
        String DB_PASSWORD = Utils.getPropertyValue("DB_PASSWORD", "config.properties");
        String url = Utils.getPropertyValue("CONNECTION_STR", "config.properties");
        props.put("hibernate.connection.url", url + DBName);
        props.put("hibernate.connection.username", DB_USERNAME);
        props.put("hibernate.connection.password", DB_PASSWORD);
        return props;
    }

    private static Properties setTestProperties(Properties props) {
        //props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop"); // update for production
        return props;
    }
}
