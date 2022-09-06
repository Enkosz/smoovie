package it.unimib.smoovie.core;

public class Event<T> {
    private boolean _handled = false;
    private T content;

    public Event(T content) {
        this.content = content;
    }

    /**
     * getContent permette di recuperare il valore conservato nell'evento una sola volta
     * @return T content
     */
    public T getContent() {
        if (_handled)
            return null;
        else {
            _handled = true;
            return content;
        }
    }
    /**
     * peekContent permette di recuperare il valore conservato nell'evento sempre, senza consumare l'evento
     * @return T content
     */
    public T peekContent() {
        return content;
    }
}
