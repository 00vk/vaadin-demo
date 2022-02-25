package ru.spb.reshenie.vaadindemo.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import ru.spb.reshenie.vaadindemo.data.entity.Club;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;
import ru.spb.reshenie.vaadindemo.data.repository.ClubRepository;
import ru.spb.reshenie.vaadindemo.data.repository.ModeratorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringComponent
public class DataGenerator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public CommandLineRunner loadData(ModeratorRepository moderatorRepository,
                                      ClubRepository clubRepository) {

        return args -> {
            if (moderatorRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 99;

            logger.info("Generating demo data");

            List<Club> clubs = generateClubs(seed);
            List<Moderator> moderators = generateModerators(clubs, seed);

            clubRepository.saveAll(clubs);
            moderatorRepository.saveAll(moderators);

            logger.info("Generated demo data");
        };
    }

    private List<Club> generateClubs(int seed) {
        ExampleDataGenerator<Club> clubGenerator = new ExampleDataGenerator<>(Club.class, LocalDateTime.now());
        clubGenerator.setData(Club::setName, DataType.COMPANY_NAME);
        return clubGenerator.create(5, seed);
    }

    private List<Moderator> generateModerators(List<Club> clubs,
                                               int seed) {
        logger.info("... generating 10 Moderator entities...");
        ExampleDataGenerator<Moderator> moderatorGenerator;
        moderatorGenerator = new ExampleDataGenerator<>(Moderator.class, LocalDateTime.now());
        moderatorGenerator.setData(Moderator::setFirstName, DataType.FIRST_NAME);
        moderatorGenerator.setData(Moderator::setLastName, DataType.LAST_NAME);
        moderatorGenerator.setData(Moderator::setEmail, DataType.EMAIL);
        Random r = new Random(seed);

        return moderatorGenerator.create(10, seed)
                                 .stream()
                                 .peek(moderator -> moderator.setClub(clubs.get(r.nextInt(clubs.size()))))
                                 .collect(Collectors.toList());
    }

}
