package com.pcz.sharding.jdbc.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.KeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author picongzhi
 */
@Configuration
public class DataSourceShardingConfig {
    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, 1);

    @Bean
    public DataSourceTransactionManager transactionManager(
            @Qualifier("datasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "datasource")
    @Primary
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // 设置分库策略
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // 设置规则适配的表
        shardingRuleConfiguration.getBindingTableGroups().add("t_order");
        // 设置分表策略
        shardingRuleConfiguration.getTableRuleConfigs().add(orderTableRule());
        shardingRuleConfiguration.setDefaultDataSourceName("ds0");
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");

        return ShardingDataSourceFactory.createDataSource(
                dataSourceMap(),
                shardingRuleConfiguration,
                new ConcurrentHashMap<>(16),
                properties);
    }

    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(16);

        HikariDataSource ds0 = new HikariDataSource();
        ds0.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds0.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds0.setUsername("root");
        ds0.setPassword("123456");

        HikariDataSource ds1 = new HikariDataSource();
        ds1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo-2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds1.setUsername("root");
        ds1.setPassword("123456");

        dataSourceMap.put("ds0", ds0);
        dataSourceMap.put("ds1", ds1);

        return dataSourceMap;
    }

    private TableRuleConfiguration orderTableRule() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        // 设置逻辑表名
        tableRuleConfiguration.setLogicTable("t_order");
        tableRuleConfiguration.setActualDataNodes("ds${0..1}.t_order_${0..2}");
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(
                "order_id", "t_order_$->{order_id % 3}"));
        tableRuleConfiguration.setKeyGenerator(customKeyGenerator());
        tableRuleConfiguration.setKeyGeneratorColumnName("order_id");

        return tableRuleConfiguration;
    }

    /**
     * 自定义主键生成器
     *
     * @return KeyGenerator
     */
    private KeyGenerator customKeyGenerator() {
        return new CustomSnowflakeKeyGenerator(SNOWFLAKE);
    }
}
