package ru.spb.reshenie.vaadindemo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.reshenie.vaadindemo.data.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
