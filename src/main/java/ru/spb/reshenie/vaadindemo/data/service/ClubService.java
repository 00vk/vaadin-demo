package ru.spb.reshenie.vaadindemo.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.reshenie.vaadindemo.data.entity.Club;
import ru.spb.reshenie.vaadindemo.data.repository.ClubRepository;

import java.util.Collection;

/**
 * Created by vkondratiev on 28.02.2022
 * Description:
 */
@Service
public class ClubService {

    @Autowired
    private final ClubRepository repository;

    public ClubService(ClubRepository repository) {
        this.repository = repository;
    }

    public Collection<Club> findAll() {
        return repository.findAll();
    }

    public Club save(Club club) {
        return repository.save(club);
    }

    public void delete(Club club) {
        repository.delete(club);
    }
}
