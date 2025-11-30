package org.nebulabackend.Controller;

import org.nebulabackend.Model.Contact;
import org.nebulabackend.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contactos")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContact(){
        try {
            List<Contact> contacts = contactService.getAllContacts();
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        }catch ( Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        try {
            Optional<Contact> contact = contactService.getContactById(id);
            if (contact.isPresent()) {
                return new ResponseEntity<>(contact.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact){
        try{
            Contact savedContact=contactService.saveContact(contact);
            return new ResponseEntity<>(savedContact,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id ,@RequestBody Contact contact){
        try {
            Optional<Contact>existingContact = contactService.getContactById(id);
            if (existingContact.isPresent()) {
                contact.setId(id); // Asegurar que el ID coincida
                Contact updatedContact = contactService.saveContact(contact);
                return new ResponseEntity<>(updatedContact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long id) {
        try {
            Optional<Contact> contact = contactService.getContactById(id);
            if (contact.isPresent()) {
                contactService.deleteContact(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
