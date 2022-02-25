package ru.spb.reshenie.vaadindemo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.reshenie.vaadindemo.data.entity.Club;

public interface ClubRepository extends JpaRepository<Club, String> {

}
