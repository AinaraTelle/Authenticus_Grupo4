package es.deusto.sd.authenticus.Clases;
import java.util.ArrayList;
import java.util.HashMap;

public class Estado {
    ArrayList<Usuario> listUsersLogIn;
    ArrayList<Usuario> listUsersLogOut;

    ArrayList< HashMap< Usuario,String>> listMap_UserToken;
    ArrayList< HashMap< Usuario,ArrayList<Caso>>> listMap_UserCases;


    public ArrayList<Usuario> getListUsersLogIn() {
        return listUsersLogIn;
    }
    public ArrayList<Usuario> getListUsersLogOut() {
        return listUsersLogOut;
    }
    public ArrayList<HashMap<Usuario, String>> getListMap_UserToken() {
        return listMap_UserToken;
    }
    public ArrayList<HashMap<Usuario, ArrayList<Caso>>> getListMap_UserCases() {
        return listMap_UserCases;
    }
    public void setListUsersLogIn(ArrayList<Usuario> listUsersLogIn) {
        this.listUsersLogIn = listUsersLogIn;
    }
    public void setListUsersLogOut(ArrayList<Usuario> listUsersLogOut) {
        this.listUsersLogOut = listUsersLogOut;
    }
    public void setListMap_UserToken(ArrayList<HashMap<Usuario, String>> listMap_UserToken) {
        this.listMap_UserToken = listMap_UserToken;
    }
    public void setListMap_UserCases(ArrayList<HashMap<Usuario, ArrayList<Caso>>> listMap_UserCases) {
        this.listMap_UserCases = listMap_UserCases;
    }    

    


}
