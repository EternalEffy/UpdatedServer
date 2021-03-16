import org.json.JSONObject;

public class CRUD {
    private JSONObject json;

    public CRUD(JSONObject json){
        this.json = json;
    }

    public JSONObject getJson(){
        return json;
    }

    public int add(String user, String listName){
        if(user.equals(null)) return -1;
        json.getJSONArray(listName).put(json.getJSONArray(listName).length(),new JSONObject(user));
        return 0;
    }

    public String get(final int index,String listName){
        return index>json.getJSONArray(listName).length()-1? null : json.getJSONArray(listName).getJSONObject(index).toString();
    }

    public int edit(final int index, String listName, String user){
        if(index>json.getJSONArray(listName).length()-1)  return -1;
        if(user.equals(null)) return -1;
        json.getJSONArray(listName).put(index,new JSONObject(user));
        return index;
    }

    public int remove(final int index,String listName){
        if(index>json.getJSONArray(listName).length()) return -1;

        json.getJSONArray(listName).remove(index);
        return index;
    }

}
