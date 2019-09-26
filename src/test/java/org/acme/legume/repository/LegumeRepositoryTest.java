package org.acme.legume.repository;/*
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2019
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 */

import io.quarkus.test.junit.QuarkusTest;
import org.acme.legume.model.Legume;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
class LegumeRepositoryTest {

    @Inject
    LegumeRepository repository;

    @Test
    void remove() {
        final Legume legume = createLegume();
        final Legume added = repository.add(legume);
        assertNotNull(added.getId());

        repository.remove(added.getId());
        assertNull(repository.find(added.getId()));
    }

    @Test
    void list() {
        IntStream.range(0, 100).forEach(value -> repository.add(createLegume()));
        final List<Legume> list = repository.list();
        assertThat(list.size(), greaterThanOrEqualTo(100));
    }

    @Test
    void addAndFind() {
        final Legume legume = createLegume();
        final Legume added = repository.add(legume);
        final Legume found = repository.find(added.getId());

        assertNull(legume.getId());
        assertNotNull(found.getId());
        assertEquals(legume.getName(), found.getName());
    }

    private Legume createLegume() {
        return Legume.builder()
                .name(UUID.randomUUID().toString())
                .description("desc")
                .build();
    }
}