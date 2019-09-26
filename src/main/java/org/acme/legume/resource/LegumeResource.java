package org.acme.legume.resource;

import org.acme.legume.data.LegumeItem;
import org.acme.legume.data.LegumeNew;
import org.acme.legume.model.Legume;
import org.acme.legume.repository.LegumeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class LegumeResource implements LegumeApi {

    @Inject
    LegumeRepository repository;

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
        return repository.list().stream()
                .map(legume -> LegumeItem.builder()
                        .id(legume.getId())
                        .name(legume.getName())
                        .description(legume.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    private Optional<Legume> find(final String legumeId) {
        return Optional.ofNullable(repository.find(legumeId));
    }

    private LegumeItem addLegume(final @Valid LegumeNew legumeNew) {
        final Legume legumeToAdd = Legume.builder()
                .name(legumeNew.getName())
                .description((legumeNew.getDescription()))
                .build();

        final Legume addedLegume = repository.add(legumeToAdd);

        final LegumeItem legumeItem = LegumeItem.builder()
                .id(addedLegume.getId())
                .name(addedLegume.getName())
                .description(addedLegume.getDescription())
                .build();

        return legumeItem;
    }
}
