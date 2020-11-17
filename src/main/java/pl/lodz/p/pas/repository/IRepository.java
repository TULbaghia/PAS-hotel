package pl.lodz.p.pas.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IRepository<T> {
    
    public void add (T item);
    public T get (String id);
    public List<T> getAll ();
    public void update (String id, T item);
    public void delete (String id);
    
}
