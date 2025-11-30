package org.nebulabackend.Repository;

import org.nebulabackend.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    List<Contact> findByNombre(String nombre);

    // También añade el método que usas en el Service
    List<Contact> findByEmail(String email);
}
