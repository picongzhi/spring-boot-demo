package com.pcz.multi.datasource.jpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author picongzhi
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = PrimaryJpaConfig.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager")
public class PrimaryJpaConfig {
    static final String REPOSITORY_PACKAGE = "com.pcz.multi.datasource.jpa.repository.primary";
    private static final String ENTITY_PACKAGE = "com.pcz.multi.datasource.jpa.entity.primary";

    /**
     * 扫描spring.jpa.primary开头的配置信息
     *
     * @return jpa配置
     */
    @Primary
    @Bean(name = "primaryJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.primary")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取主库实体管理工厂对象
     *
     * @param primaryDataSource 数据源
     * @param jpaProperties     jpa配置信息
     * @param builder           构造器
     * @return 实体管理工厂
     */
    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                                                       @Qualifier("primaryJpaProperties") JpaProperties jpaProperties,
                                                                       EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource)
                .properties(jpaProperties.getProperties())
                .packages(ENTITY_PACKAGE)
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 实体管理工厂
     * @return 实体管理对象
     */
    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager entityManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 实体管理工厂
     * @return 事务管理对象
     */
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
