package util;

import entity.Login;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;


/**
 * @author DanujaV
 * @created 03/11/2021 - 9:54 AM
 */
public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;


    private FactoryConfiguration() {
        try {

            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("property.properties"));

            /*First Method I Use (Save For Backup)*/
            /*
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/sipsewanaInstitute");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "1234");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "update");
*/

            sessionFactory = new Configuration().setProperties(properties)
                    .addAnnotatedClass(TrainingProgram.class).addAnnotatedClass(Student.class).addAnnotatedClass(StudentProgramDetail.class).addAnnotatedClass(Login.class)
                    .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("build SeesionFactory failed :" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration()
                : factoryConfiguration;
    }
    public Session getSession() {
        return sessionFactory.openSession();
    }

}