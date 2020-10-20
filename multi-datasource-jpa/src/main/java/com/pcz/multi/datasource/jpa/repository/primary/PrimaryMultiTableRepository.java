package com.pcz.multi.datasource.jpa.repository.primary;

import com.pcz.multi.datasource.jpa.entity.primary.PrimaryMultiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author picongzhi
 */
@Repository
public interface PrimaryMultiTableRepository extends JpaRepository<PrimaryMultiTable, Long> {
}
