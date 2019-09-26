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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
public class LegumeRepository {

    @Inject
    EntityManager manager;

    public void remove(final String legumeId) {
        manager.remove(find(legumeId));
    }

    public List<Legume> list() {
        return manager.createQuery("SELECT l FROM Legume l").getResultList();
    }

    public Legume find(final String legumeId) {
        return manager.find(Legume.class, legumeId);
    }

    public Legume add(final Legume legumeToAdd) {
        return manager.merge(legumeToAdd);
    }
}
