package ru.spb.reshenie.vaadindemo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.reshenie.vaadindemo.data.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
