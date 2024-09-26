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
@EnableJpaRepositories(basePackages = "com.idle.kb_i_dle_backend.**.repository")
@MapperScan(basePackages = {"com.idle.kb_i_dle_backend.member.mapper"})
@PropertySource({"classpath:/application.properties"})
public class DBConfig {

    @Autowired
    ApplicationContext applicationContext; //Context-웹자체

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
     * 히카리 풀 db와 연결, 프로세스를 여러개 미리 연결해둔뒤 가지고 활용함
     * db url설정 , 최대 커넥션 풀 설정
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
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }



    /**
     * 트랜잭션 매니저 등록
     * 아직 고민중;;
     * mybatis는 바로바로 반영하고
     * jpa는 1차 캐시에 저장해서 처리한뒤에 반영함 이를 혼용했을때 DB무결성에 문제가 없는가
     * mybatis는 DataSoucrceManager을 사용하는데 이 DataSourceManager가 JpaTransactionManager안에 있어서 괜찮다는 글을 봐서 일단 이렇게
     * https://wave1994.tistory.com/178
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
     * 엔티티 매니저 팩토리 설정 hibernate설정
     *
     * @param dataSource
     * @param ddlAuto
     * @param dialect
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource , @Value("${hibernate.ddl-auto}") String ddlAuto,
                                                                       @Value("${jpa.dialect}") String dialect) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.idle.kb_i_dle_backend.**.entity");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto); //실행시 entity class에 따라 table을 제작하는 ddl설정 현재 미리 db구축 해뒀으니 validate로
        properties.setProperty("hibernate.dialect", dialect); //간단히 말해 sql 형식을 어떻게 할지, mysql 9방식으로 설정 해뒀고 나중에 oracle로 바꿔도  이값만 바꾸면 oracle에 맞춰서 sql작성해줌
        bean.setJpaProperties(properties);

        return bean;
    }


    /**
     * mybatis설정 파일 위치와
     * mapper파일들 위치 등록
     *
     * @param dataSource
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //환경설정 mybatis-config.xml  에 기본정보들를 세팅 한다
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/mappers/*.xml"));
        return factoryBean;
    }

    /**
     * SqlSessionTemplate는 Mybatis쿼리문을 수행해주는 역할을 합니다.
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        //팩토리에 의해 관리된다.
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
