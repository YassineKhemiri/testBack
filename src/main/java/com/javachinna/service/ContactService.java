package com.javachinna.service;

import com.javachinna.model.Branche;
import com.javachinna.model.Contact;

import java.util.List;

public interface ContactService {
    public List<Contact> getAllContacts();

    public Contact getContactById(Long id);

    public void deleteContact(Long id);

    public Contact updateContact(Contact b);

    public Contact addContact(Contact b);
}
