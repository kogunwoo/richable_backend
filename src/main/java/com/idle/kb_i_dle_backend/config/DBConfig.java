package com.idle.kb_i_dle_backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.idle.kb_i_dle_backend.domain.**.repository")
@PropertySource({"classpath:/application.properties"})
public class DBConfig {

    @Autowired
    ApplicationContext applicationContext; // Context-웹자체

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String dbUrl;

    @Value("${jdbc.username}")
    private String dbUser;

    @Value("${jdbc.password}")
    private String dbPass;

    @Value("${hibernate.ddl-auto}")
    private String ddlAuto;

    /**
     * 히카리 풀 DB와 연결, 프로세스를 여러 개 미리 연결해 둔 뒤 가지고 활용함.
     * DB URL 설정, 최대 커넥션 풀 설정.
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setMaximumPoolSize(2);
        return new HikariDataSource(config);
    }

    /**
     * 트랜잭션 매니저 등록.
     * MyBatis와 JPA를 혼용했을 때의 DB 무결성 문제는 없을지 확인 중.
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * 엔티티 매니저 팩토리 설정 - Hibernate 설정.
     *
     * @param dataSource
     * @param ddlAuto
     * @param dialect
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       @Value("${hibernate.ddl-auto}") String ddlAuto,
                                                                       @Value("${jpa.dialect}") String dialect) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.idle.kb_i_dle_backend.**.entity");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto); // 현재 validate로 설정
        properties.setProperty("hibernate.dialect", dialect); // MySQL 9방식, 나중에 Oracle로 바꿀 수 있음
        bean.setJpaProperties(properties);

        return bean;
    }
}
