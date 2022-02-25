package ru.spb.reshenie.vaadindemo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;

import java.util.List;

public interface ModeratorRepository extends JpaRepository<Moderator, String> {

    @Query("select c from Moderator c " +
           "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
           "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Moderator> search(@Param("searchTerm") String searchTerm);
}
