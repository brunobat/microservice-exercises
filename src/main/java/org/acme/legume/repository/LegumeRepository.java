/*
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2019
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 */

package org.acme.legume.repository;

import org.acme.legume.model.Legume;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;


@ApplicationScoped
public class LegumeRepository {

    private Map<String, Legume> manager = new ConcurrentHashMap<>();

    public void remove(final String legumeId) {
        manager.remove(legumeId);
    }

    public List<Legume> list() {
        return new ArrayList<>(manager.values());
    }

    public Legume find(final String legumeId) {
        return manager.get(legumeId);
    }

    public Legume add(final Legume legume) {

        final Legume legumeToAdd = Legume.builder()
                .id(ofNullable(legume.getId())
                        .orElse(UUID.randomUUID().toString()))
                .name(legume.getName())
                .description(legume.getDescription())
                .build();

        manager.put(legumeToAdd.getId(), legumeToAdd);
        return manager.get(legumeToAdd.getId());
    }
}
