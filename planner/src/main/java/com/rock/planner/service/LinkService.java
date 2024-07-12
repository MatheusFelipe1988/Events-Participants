package com.rock.planner.service;

import com.rock.planner.model.*;
import com.rock.planner.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinksResponse registerLink(LinksRequest request, Trips trips){
        Link newLink = new Link(request.title(), request.url(), trips);

        this.repository.save(newLink);

        return new LinksResponse(newLink.getId());
    }

    public List<LinkData> getAllLinksFromId(UUID tripsId) {
        return this.repository.findByTripId(tripsId).stream().map(link -> new LinkData(link.getId(), link.getTitle(),
                link.getUrl())).toList();
    }
}
