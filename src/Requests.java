import org.json.JSONObject;

public class Requests {
    private JSONObject json;
    public static final String add = "add", remove = "remove", edit = "edit", get = "get";
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }



    public String toString(){
        return "{\"request\":\""+request+"\"}";
    }

}
