package sunposition.springdays.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sunposition.springdays.model.Country;
import sunposition.springdays.model.Day;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres?serverTimezone=Europe/Minsk&useSSL=false")
                .applySetting("hibernate.connection.username", "postgres")
                .applySetting("hibernate.connection.password", "123")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .build();

        return new MetadataSources(registry)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Day.class)
                .buildMetadata()
                .buildSessionFactory();
    }
}
