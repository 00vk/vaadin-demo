package ru.spb.reshenie.vaadindemo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.reshenie.vaadindemo.data.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
