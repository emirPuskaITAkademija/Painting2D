package ba.paint.nasa.app.xml;

@FunctionalInterface
public interface Refreshable {
    public void refresh();
    
    static final Refreshable NO_REFRESH =()->{};
}
