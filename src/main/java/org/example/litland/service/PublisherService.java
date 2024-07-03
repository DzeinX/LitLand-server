package org.example.litland.service;

import org.example.litland.model.Genre;
import org.example.litland.model.Publisher;
import org.example.litland.repository.GenreRepository;
import org.example.litland.repository.PublisherRepository;
import org.example.litland.shell.GenreShell;
import org.example.litland.shell.PublisherShell;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherShell> getAllPublishers() {
        List<PublisherShell> publishers = new ArrayList<>();
        publisherRepository.findAll().forEach((publisher) -> {
            publishers.add(this.getPublisher(publisher));
        });

        return publishers;
    }

    private PublisherShell getPublisher(Publisher publisher) {
        return new PublisherShell(publisher.getId().toString(), publisher.getName());
    }
}
