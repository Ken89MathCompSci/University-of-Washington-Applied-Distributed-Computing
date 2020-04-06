package Server;

public interface Operations {
    public String del(String key) throws Throwable;
    public String put(String key, String val) throws Throwable;
    public String store() throws Throwable;
    public String get(String key) throws Throwable;
    public String exit() throws Throwable;
}
