package org.acme.legume.resource;

import org.acme.legume.data.LegumeItem;
import org.acme.legume.data.LegumeNew;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

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

    public Response add(@Valid final LegumeNew legumeNew) {
        return Response.status(CREATED).entity(addLegume(legumeNew)).build();
    }

    public Response delete(@NotEmpty final String legumeId) {
        return find(legumeId)
                .map(legume -> {
                    repository.remove(legume.getId());
                    return Response.status(NO_CONTENT).build();
                })
                .orElse(Response.status(NOT_FOUND).build());
    }

    public List<LegumeItem> list() {
        return new ArrayList<>(repository.values());
    }

    private Optional<LegumeItem> find(final String legumeId) {
        return Optional.ofNullable(repository.get(legumeId));
    }

    private LegumeItem addLegume(final @Valid LegumeNew legumeNew) {
        final String id = UUID.randomUUID().toString();

        final LegumeItem legumeToAdd = LegumeItem.builder()
                .id(id)
                .name(legumeNew.getName())
                .description((legumeNew.getDescription()))
                .build();

        repository.put(id, legumeToAdd);
        final LegumeItem addedLegume = repository.get(id);

        final LegumeItem legumeItem = LegumeItem.builder()
                .id(addedLegume.getId())
                .name(addedLegume.getName())
                .description(addedLegume.getDescription())
                .build();

        return legumeItem;
    }
}
