package DB_Layer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John Rey Juele
 */
public interface Dao<T> {
    public T    get(int id);
    public void add(T n);
    public void delete(int id);
    public void update(int id);
}

