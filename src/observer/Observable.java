/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

/**
 *
 * @author Formation
 */
public interface Observable <T> {
    
    public void addObserver(Observer obj);

    public void removeObserver(Observer obj);

    public void notifyObserver(T obj);
    
    
}
