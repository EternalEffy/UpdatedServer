import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static DataInputStream inStream;
    private static DataOutputStream outStream;
    private static FileData myData;
    private static CRUD crud;
    private int index,port;
    private String fileName;
    private String requestFormClient;
    private boolean flag = true;
    private int countRequests = 0;

    public int loadFile(String fileName){
        this.fileName = fileName;
        myData = new FileData();
        System.out.println(ServerMessages.MESSAGE_LOAD_FILE);
        try {
            myData.loadJSON(fileName);
        }catch (Exception e){
            return -1;
        }
        return 0;
    }

    public int saveFile(){
        System.out.println(ServerMessages.MESSAGE_SAVE_FILE);
        try {
            myData.saveJSON(crud.getJson(), fileName);
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    public void setPort(int port){
        this.port = port;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            setPort(port+1);
        }
    }

    public void loadServer() {
        crud = new CRUD(myData.getJson());
        System.out.println("Server started");
        try {
            while(true) {
                clientSocket = server.accept();
                if (clientSocket.isConnected()) {
                    System.out.println(ServerMessages.MESSAGE_ACCESS + clientSocket.getInetAddress());
                    inStream = new DataInputStream(clientSocket.getInputStream());
                    outStream = new DataOutputStream((clientSocket.getOutputStream()));
                    outStream.writeUTF(inStream.readUTF() + ServerMessages.USER_MESSAGE_ACCESS);
                    outStream.flush();
                }

                while (!clientSocket.isClosed()) {
                    if(countRequests == 99){
                        if(saveFile()==0){
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
                        }
                                else System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_NO);

                        countRequests=0;
                    }
                    requestFormClient = inStream.readUTF();
                    countRequests++;
                    index = Integer.parseInt(inStream.readUTF());
                    JSONObject jsonObject = new JSONObject(requestFormClient);

                    switch (jsonObject.getString("request")) {
                        case Requests.add:
                            System.out.println(ServerMessages.MESSAGE_ADD);
                            crud.add(jsonObject.getJSONArray(myData.getListName()), myData.getListName());
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
                            outStream.writeUTF(ServerMessages.MESSAGE_USER_INFO + crud.get(index, myData.getListName()));
                            outStream.flush();
                            break;
                        case Requests.get:
                            System.out.println(ServerMessages.MESSAGE_GET);
                            crud.get(index, myData.getListName());
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
                            outStream.writeUTF(ServerMessages.MESSAGE_USER_INFO + crud.get(index, myData.getListName()));
                            outStream.flush();
                            break;
                        case Requests.edit:
                            System.out.println(ServerMessages.MESSAGE_EDIT);
                            crud.edit(index, myData.getListName(), jsonObject.getJSONArray(myData.getListName()));
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
                            outStream.writeUTF(ServerMessages.MESSAGE_USER_INFO + crud.get(index, myData.getListName()));
                            outStream.flush();
                            break;
                        case Requests.remove:
                            System.out.println(ServerMessages.MESSAGE_REMOVE);
                            crud.remove(index, myData.getListName());
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_YES);
                            outStream.writeUTF(ServerMessages.MESSAGE_USER_INFO + crud.get(index, myData.getListName()));
                            outStream.flush();
                            break;
                        case Requests.stop:
                            server.close();
                            inStream.close();
                            outStream.close();
                            flag = false;
                            break;
                        default:
                            outStream.writeUTF(ServerMessages.MESSAGE_ERROR);
                            outStream.flush();
                            clientSocket.close();
                            System.out.println(ServerMessages.MESSAGE_USER_INFO + ServerMessages.MESSAGE_RESULT_NO);
                    }
                }
            }
        }
        catch (IOException e) {
            if(flag==true) {
                loadServer();
            }
            else System.out.println(ServerMessages.MESSAGE_END);
        }
    }


}
