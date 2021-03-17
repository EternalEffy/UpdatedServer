import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileData {


        private JSONObject json;
        private String fileName;
        private final String listName = "userData";

        public String getListName() {
            return listName;
        }

    public int loadJSON(String fileName) {
            this.fileName = fileName;
            try {
                json = new JSONObject(new String(new FileInputStream(fileName).readAllBytes()));
            } catch (FileNotFoundException e) {
                try {
                    Files.writeString((Files.createFile(Path.of(fileName))),"{\"" + listName + "\":[]}");
                    json = new JSONObject(new String(new FileInputStream(fileName).readAllBytes()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
            return 0;
        }

        public int saveJSON(JSONObject json,String fileName) {
            try {
                new FileOutputStream(fileName).write(json.toString().getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
            return 0;
        }

        public JSONObject getJson(){
            return json;
        }

}
