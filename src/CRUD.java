import org.json.JSONObject;

public class CRUD {
    private JSONObject json;

    public CRUD(JSONObject json){
        this.json = json;
    }

    public JSONObject getJson(){
        return json;
    }

    public int add(String userName,String age,String score,String level, String listName){
        if(userName.equals(null)) return -1;
        json.getJSONArray(listName).put(json.getJSONArray(listName).length(),new JSONObject(new User(userName,age,score,level)));
        return 0;
    }

    public String get(int index, String listName){
        return index>json.getJSONArray(listName).length()-1? null : json.getJSONArray(listName).getJSONObject(index).toString();
    }

    public int edit(int index, String listName, String userName,String age,String score,String level){
        if(index>json.getJSONArray(listName).length()-1)  return -1;
        if(userName.equals(null)) return -1;
        json.getJSONArray(listName).put(index,new JSONObject(new User(userName,age,score,level)));
        return index;
    }

    public int remove(int index,String listName){
        if(index>json.getJSONArray(listName).length()) return -1;

        json.getJSONArray(listName).remove(index);
        return index;
    }

}
