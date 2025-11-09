package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.User;

@Service
public class Estado {
    private final ArrayList<User> listUsersLogIn = new ArrayList<User>();
    private final ArrayList<User> listUsersRegistrados = new ArrayList<User>();

    private final HashMap< User,String> map_UserToken= new HashMap< User,String>();
    private final HashMap< User,ArrayList<Caso>> map_UserCases= new HashMap< User,ArrayList<Caso>>();

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    
    public ArrayList<User> getListUsersLogIn() {
        return listUsersLogIn;
    }

    public ArrayList<User> getListUsersRegistrados() {
        return listUsersRegistrados;
    }

    public HashMap<User, String> getMap_UserToken() {
        return map_UserToken;
    }

    public HashMap<User, ArrayList<Caso>> getMap_UserCases() {
        return map_UserCases;
    }   

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    } 


}