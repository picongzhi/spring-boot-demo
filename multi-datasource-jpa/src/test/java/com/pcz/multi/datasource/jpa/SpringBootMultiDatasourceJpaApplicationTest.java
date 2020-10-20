package com.pcz.multi.datasource.jpa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.pcz.multi.datasource.jpa.entity.primary.PrimaryMultiTable;
import com.pcz.multi.datasource.jpa.entity.second.SecondMultiTable;
import com.pcz.multi.datasource.jpa.repository.primary.PrimaryMultiTableRepository;
import com.pcz.multi.datasource.jpa.repository.second.SecondMultiTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMultiDatasourceJpaApplicationTest {
    @Autowired
    private PrimaryMultiTableRepository primaryMultiTableRepository;

    @Autowired
    private SecondMultiTableRepository secondMultiTableRepository;

    @Autowired
    private Snowflake snowflake;

    @Test
    public void testInsert() {
        PrimaryMultiTable primaryMultiTable = new PrimaryMultiTable(snowflake.nextId(), "测试1");
        primaryMultiTableRepository.save(primaryMultiTable);

        SecondMultiTable secondMultiTable = new SecondMultiTable();
        BeanUtil.copyProperties(primaryMultiTable, secondMultiTable);
        secondMultiTableRepository.save(secondMultiTable);
    }

    @Test
    public void testUpdate() {
        primaryMultiTableRepository.findAll().forEach(primaryMultiTable -> {
            primaryMultiTable.setName("updated" + primaryMultiTable.getName());
            primaryMultiTableRepository.save(primaryMultiTable);

            SecondMultiTable secondMultiTable = new SecondMultiTable();
            BeanUtil.copyProperties(primaryMultiTable, secondMultiTable);
            secondMultiTableRepository.save(secondMultiTable);
        });
    }

    @Test
    public void testDelete() {
        primaryMultiTableRepository.deleteAll();
        secondMultiTableRepository.deleteAll();
    }

    @Test
    public void testSelect() {
        List<PrimaryMultiTable> primaryMultiTables = primaryMultiTableRepository.findAll();
        log.info("[primary] = {}", primaryMultiTables);

        List<SecondMultiTable> secondMultiTables = secondMultiTableRepository.findAll();
        log.info("[second] = {}", secondMultiTables);
    }
}
