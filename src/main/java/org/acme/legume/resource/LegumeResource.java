package org.acme.legume.resource;

import org.acme.legume.data.LegumeItem;
import org.acme.legume.data.LegumeNew;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static javax.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
public class LegumeResource implements LegumeApi {

    private Map<String, LegumeItem> repository = new ConcurrentHashMap<>();

    public Response provision() {
        final LegumeNew carrot = LegumeNew.builder()
                .name("Carrot")
                .description("Root vegetable, usually orange")
                .build();
        final LegumeNew zucchini = LegumeNew.builder()
                .name("Zucchini")
                .description("Summer squash")
                .build();
        return Response.status(CREATED).entity(asList(
                addLegume(carrot),
                addLegume(zucchini))).build();
    }

    public List<LegumeItem> list() {
        return new ArrayList<>(repository.values());
    }

    private LegumeItem addLegume(final @Valid LegumeNew legumeNew) {
        final String id = UUID.randomUUID().toString();

        final LegumeItem legumeToAdd = LegumeItem.builder()
                .id(id)
                .name(legumeNew.getName())
                .description((legumeNew.getDescription()))
                .build();

        repository.put(id, legumeToAdd);
        return repository.get(id);
    }
}
