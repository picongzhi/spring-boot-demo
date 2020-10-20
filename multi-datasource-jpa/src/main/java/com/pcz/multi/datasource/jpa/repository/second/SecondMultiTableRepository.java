package com.pcz.multi.datasource.jpa.repository.second;

import com.pcz.multi.datasource.jpa.entity.second.SecondMultiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author picongzhi
 */
@Repository
public interface SecondMultiTableRepository extends JpaRepository<SecondMultiTable, Long> {
}
