package com.javachinna.controller;


import com.javachinna.model.Contact;
import com.javachinna.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/api/contacts")
@CrossOrigin("*")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("All")
    public ResponseEntity<?> getAllContacts()
    {
        return  ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<?> getContactById(@PathVariable(name = "id") Long id)
    {
        return  ResponseEntity.ok(contactService.getContactById(id));
    }


    @DeleteMapping("/deleteContact/{x}")
    public void deleteBrancheById(@PathVariable (name = "x") Long x) {
        contactService.deleteContact(x);
    }


    @PutMapping("editContact")
    public ResponseEntity<?> EditContact(@RequestBody Contact b)
    {
        return  ResponseEntity.ok(contactService.updateContact(b));
    }

    @PostMapping("addContact")
    public ResponseEntity<?> addContact(@RequestBody Contact b)
    {
        return  ResponseEntity.ok(contactService.addContact(b));
    }

}
