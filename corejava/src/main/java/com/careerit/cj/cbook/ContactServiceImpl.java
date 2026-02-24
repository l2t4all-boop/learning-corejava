package com.careerit.cj.cbook;

import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService{

    private List<Contact> contacts = new ArrayList<>();

    @Override
    public Contact addContact(Contact contact) {
        contact.setId(IdGenerator.generateId());
        contacts.add(contact);
        return contact;
    }

    @Override
    public Contact getContact(String id) {
        Contact contact = null;
        for(Contact ele: contacts){
            if(ele.getId().equalsIgnoreCase(id)){
                contact = ele;
                break;
            }
        }
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }

    @Override
    public Contact updateContact(Contact contact) {
        return null;
    }

    @Override
    public boolean deleteContact(String id) {
        return false;
    }

    @Override
    public List<Contact> searchContacts(String keyword) {
        return List.of();
    }

    @Override
    public String sendBirthdayGreetings() {

        return "";
    }
}
