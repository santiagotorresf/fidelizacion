/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.configuration;

import fttg.commons.spring.web.util.scope.ViewScope;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author jvillanueva
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(
    basePackages = {"fttg.*"}
)
@PropertySources({
    @PropertySource({"classpath:application.properties"}), 
    @PropertySource({"classpath:authentication.properties"}), 
    @PropertySource({"classpath:integration.properties"}),
    @PropertySource({"classpath:ldap.properties"})
})
public class BackEndConfiguration {

    @Autowired
    private Environment environment;
    
    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource() {
        var dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setPoolPreparedStatements(TRUE);
        dataSource.setInitialSize(1);
        dataSource.setMaxTotal(2);
        dataSource.setDefaultAutoCommit(FALSE);
        return dataSource;
    }
    
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource= new LdapContextSource();
        contextSource.setUrl(environment.getRequiredProperty("ldap.url"));
        contextSource.setBase(environment.getRequiredProperty("ldap.base"));
        contextSource.setUserDn(environment.getRequiredProperty("ldap.user"));
        contextSource.setPassword(environment.getRequiredProperty("ldap.password"));
        return contextSource;
    }
    
    public Properties additionalProperties() {
        var properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
            environment.getRequiredProperty("sessionFactory.packagesToScan")
        );
        sessionFactory.setHibernateProperties(additionalProperties());
        return sessionFactory;
    }
    
    @Bean
    public LdapTemplate ldapTemplate() {
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource());
        ldapTemplate.setIgnorePartialResultException(true);
        ldapTemplate.setContextSource(contextSource());
        return ldapTemplate;     
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("view", new ViewScope());
        var bean = new CustomScopeConfigurer();
        bean.setScopes(scopes);
        return bean;
    }
    
}
