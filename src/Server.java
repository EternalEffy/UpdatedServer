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

    public void loadServer() {
        FileData myData = new FileData("userData");
        myData.loadJSON("test.json");
        CRUD crud = new CRUD(myData.getJson());
        try {
            server = new ServerSocket(3320);
            System.out.println("Сервер запущен");
            clientSocket = server.accept();
            inStream= new DataInputStream(clientSocket.getInputStream());
            outStream=new DataOutputStream((clientSocket.getOutputStream()));
            if (clientSocket.isConnected()) {
                System.out.println(ServerMessages.messageAccess + clientSocket.getInetAddress());
                outStream.writeUTF(inStream.readUTF()+ServerMessages.userMessageAccess);
                outStream.flush();
            }

            while (!clientSocket.isClosed()) {
                String request = inStream.readUTF();
                if (request.equals(Requests.add)){
                    System.out.println(ServerMessages.messageAdd);
                    crud.add(inStream.readUTF(), myData.getListName());
                    System.out.println(ServerMessages.messageUserInfo + ServerMessages.messageResultYes);
                    outStream.writeUTF(ServerMessages.messageUserInfo + crud.get(Integer.valueOf(inStream.readUTF()), myData.getListName()));
                    outStream.flush();
                }
                else if(request.equals(Requests.get)){
                        System.out.println(ServerMessages.messageGet);
                        int index = Integer.parseInt(inStream.readUTF());
                        crud.get(index, myData.getListName());
                        System.out.println(ServerMessages.messageUserInfo + ServerMessages.messageResultYes);
                        outStream.writeUTF(ServerMessages.messageUserInfo + crud.get(index, myData.getListName()));
                        outStream.flush();
                    }
                    else if (request.equals(Requests.edit)){
                            System.out.println(ServerMessages.messageEdit);
                            int index = Integer.parseInt(inStream.readUTF());
                            crud.edit(index, myData.getListName(), inStream.readUTF());
                            System.out.println(ServerMessages.messageUserInfo + ServerMessages.messageResultYes);
                            outStream.writeUTF(ServerMessages.messageUserInfo + crud.get(index, myData.getListName()));
                            outStream.flush();
                        }
                        else if(request.equals(Requests.remove)){
                            System.out.println(ServerMessages.messageRemove);
                            int index = Integer.parseInt(inStream.readUTF());
                            crud.remove(index, myData.getListName());
                            System.out.println(ServerMessages.messageUserInfo + ServerMessages.messageResultYes);
                            outStream.writeUTF(ServerMessages.messageUserInfo + crud.get(index, myData.getListName()));
                            outStream.flush();
                            }
                else outStream.writeUTF(ServerMessages.messageError);
            }
        }
        catch (IOException e) {
            System.out.println(ServerMessages.messageEnd);
        }
        System.out.println("Пытаюсь сохранить файл");
        myData.saveJSON(crud.getJson(), "test.json");
    }
}
