package pl.lodz.p.pas.repository;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public  abstract class Repository<T extends IdTrait> {
    private final List<T> items = new ArrayList<>();

    public T get(UUID id) {
        return filter(x -> x.getId().equals(id)).stream().findFirst().orElse(null);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public synchronized void add(@NonNull T item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
        if (get(item.getId()) == null) {
            items.add(item);
        } else {
            throw new RepositoryException("objectAlreadyExist");
        }
    }

    @SneakyThrows
    public synchronized void update(@NonNull T item) {
        if(item.getId() == null) {
            throw new RepositoryException("objectIdIsNull");
        }
        T old = get(item.getId());
        if (old != null) {
            BeanUtils.copyProperties(old, item);
        } else {
            throw new RepositoryException("objectDoesNotExist");
        }
    }

    public synchronized void delete(@NonNull UUID id) {
        T old = get(id);
        if (old != null) {
            items.remove(old);
        }
    }

    public List<T> filter(Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }

}
