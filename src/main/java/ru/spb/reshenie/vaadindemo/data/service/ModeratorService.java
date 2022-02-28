package ru.spb.reshenie.vaadindemo.data.service;

import org.springframework.stereotype.Service;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;
import ru.spb.reshenie.vaadindemo.data.repository.ModeratorRepository;

import java.util.Collection;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
@Service
public class ModeratorService {

    private final ModeratorRepository repository;

    public ModeratorService(ModeratorRepository repository) {
        this.repository = repository;
    }

    public long countModerators() {
        return repository.count();
    }

    public Collection<Moderator> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public Moderator save(Moderator moderator) {
        return repository.save(moderator);
    }

    public void delete(Moderator moderator) {
        repository.delete(moderator);
    }
}
