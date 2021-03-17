
public class Main {
    public static void main(String[] args) {
        Server server = new Server("test.json","userData");
        server.loadFile();
        server.setPort(3310);
        server.loadServer();
        server.saveFile();
    }
}
