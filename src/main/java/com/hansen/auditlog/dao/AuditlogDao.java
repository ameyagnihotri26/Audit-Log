package com.hansen.auditlog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansen.auditlog.model.Auditlog;


public interface AuditlogDao extends PagingAndSortingRepository<Auditlog, Long> {
	@Query(value = "SELECT * FROM auditlog Where entity_json LIKE %?% ", nativeQuery = true)
	Iterable<Auditlog> findByEntityJson(String keyword);
	
	
	@Query(value = "SELECT * FROM auditlog Where modification_date LIKE %?% ", nativeQuery = true)
	Iterable<Auditlog> findByModificationDate(String keyword);
	
	@Query(value = "SELECT * FROM auditlog Where operation_type LIKE %?% ", nativeQuery = true)
	Iterable<Auditlog> findByOperationType(String keyword);

}
