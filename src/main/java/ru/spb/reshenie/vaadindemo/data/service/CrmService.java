package ru.spb.reshenie.vaadindemo.data.service;

import org.springframework.stereotype.Service;
import ru.spb.reshenie.vaadindemo.data.entity.Club;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;
import ru.spb.reshenie.vaadindemo.data.repository.ClubRepository;
import ru.spb.reshenie.vaadindemo.data.repository.ModeratorRepository;

import java.util.List;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
@Service
public class CrmService {

    private final ModeratorRepository moderatorRepository;
    private final ClubRepository clubRepository;

    public CrmService(ModeratorRepository moderatorRepository,
                      ClubRepository clubRepository) {
        this.moderatorRepository = moderatorRepository;
        this.clubRepository = clubRepository;
    }

    public List<Moderator> findAllModerators(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return moderatorRepository.findAll();
        } else {
            return moderatorRepository.search(stringFilter);
        }
    }

    public long countModerators() {
        return moderatorRepository.count();
    }

    public void deleteModerator(Moderator moderator) {
        moderatorRepository.delete(moderator);
    }

    public void saveModerator(Moderator moderator) {
        if (moderator == null) {
            System.err.println("Moderator is null. Are you sure you have connected your form to the application?");
            return;
        }
        moderatorRepository.save(moderator);
    }

    public List<Club> findAllCompanies() {
        return clubRepository.findAll();
    }
}
