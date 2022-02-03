package ru.spb.reshenie.vaadindemo.data.service;

import org.springframework.stereotype.Service;
import ru.spb.reshenie.vaadindemo.data.entity.Company;
import ru.spb.reshenie.vaadindemo.data.entity.Contact;
import ru.spb.reshenie.vaadindemo.data.entity.Status;
import ru.spb.reshenie.vaadindemo.data.repository.CompanyRepository;
import ru.spb.reshenie.vaadindemo.data.repository.ContactRepository;
import ru.spb.reshenie.vaadindemo.data.repository.StatusRepository;

import java.util.List;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}
