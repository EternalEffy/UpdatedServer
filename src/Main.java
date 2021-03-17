
public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        if(server.loadFile("test.json")==0){
            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
        }
        else System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_NO);
        server.setPort(3310);
        server.loadServer();
        if(server.saveFile()==0){
            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
        }
        else System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_NO);
    }
}
