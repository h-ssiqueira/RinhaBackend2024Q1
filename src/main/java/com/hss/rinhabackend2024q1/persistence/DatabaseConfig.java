package com.hss.rinhabackend2024q1.persistence;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(transactionManagerRef = "transactionManagerRinha",
//                       entityManagerFactoryRef = "entityManagerFactoryRinha",
//                       basePackages = "com.hss.rinhabackend2024q1.persistence")
public class DatabaseConfig {

//    @Bean(name = "entityManagerFactoryRinha")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSourceRinha") DataSource dataSource) {
//        var entityManager = new LocalContainerEntityManagerFactoryBean();
//        entityManager.setDataSource(dataSource);
//        entityManager.setPackagesToScan("com.hss.rinhabackend2024q1.persistence");
//        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        return entityManager;
//    }
//
//    @Bean("transactionManagerRinha")
//    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryRinha") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceRinha() {
        return DataSourceBuilder.create().build();
    }

    @Bean("liquibaseProperties")
    @ConfigurationProperties(prefix = "spring.liquibase")
    public LiquibaseProperties getLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase getSpringLiquibaseProperties(@Qualifier("dataSourceRinha") DataSource dataSource,
                                                        @Qualifier("liquibaseProperties") LiquibaseProperties liquibaseProperties) {
        var config = new SpringLiquibase();
        config.setChangeLog(liquibaseProperties.getChangeLog());
        config.setDataSource(dataSource);
        config.setShouldRun(liquibaseProperties.isEnabled());
        config.setTestRollbackOnUpdate(false);
        return config;
    }

}
