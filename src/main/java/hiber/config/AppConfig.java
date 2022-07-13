package hiber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource(value = "classpath:db.properties")
@EnableTransactionManagement
public class AppConfig {

   private Environment env;
   @Autowired
   public AppConfig(Environment env) {
      this.env = env;
   }

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getEntityFactory() {
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
      Properties props = new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
      props.put("hibernate.current_session_context_class", "thread");
      bean.setDataSource(getDataSource());
      bean.setPackagesToScan("hiber.model");
      bean.setJpaVendorAdapter(vendorAdapter);
      bean.setJpaProperties(props);
      return bean;
   }

   @Bean
   public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
      JpaTransactionManager manager = new JpaTransactionManager();
      manager.setEntityManagerFactory(getEntityFactory().getObject());
      return manager;
   }
}
