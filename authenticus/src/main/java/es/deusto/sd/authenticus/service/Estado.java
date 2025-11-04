package es.deusto.sd.authenticus.service;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import es.deusto.sd.authenticus.entity.Caso;
import es.deusto.sd.authenticus.entity.User;
import es.deusto.sd.authenticus.dto.UserDTO;
// import java.util.UUID;

@Service
public class Estado {
    private final ArrayList<User> listUsersLogIn = new ArrayList<User>();
    private final ArrayList<User> listUsersLogOut = new ArrayList<User>();

    private final HashMap< User,String> map_UserToken= new HashMap< User,String>();
    private final HashMap< User,ArrayList<Caso>> map_UserCases= new HashMap< User,ArrayList<Caso>>();

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    

    



    // Get los Users de logout
    public ArrayList<UserDTO> getAllUsersLogOut(){
        ArrayList<UserDTO> listUsersDTOs = new ArrayList<UserDTO>();
        
        for(User User1: this.getListUsersLogOut()){
            listUsersDTOs.add(convertToDTO(User1));
        }
        return listUsersDTOs;
    }

    

    private UserDTO convertToDTO(User User) {
        return new UserDTO(User.getIDUsuario(), 
        User.getNombre(), User.getEmail(),
        User.getPassword(),User.getTel());
    }

    public ArrayList<User> getListUsersLogIn() {
        return listUsersLogIn;
    }

    public ArrayList<User> getListUsersLogOut() {
        return listUsersLogOut;
    }


    public HashMap<User, String> getMap_UserToken() {
        return map_UserToken;
    }

    public HashMap<User, ArrayList<Caso>> getMap_UserCases() {
        return map_UserCases;
    }

    public UserDTO createUser(UserDTO userDTO) {    	
    	User user = new User(idGenerator.incrementAndGet(),
        userDTO.getNombre(),userDTO.getEmail(), 
        userDTO.getPassword(), userDTO.getTel());

       this.añadirUsuarioNuevoALogout(user);

        return convertToDTO(user);
    }

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    }

    public void añadirUsuarioNuevoALogout(User user){
        this.getListUsersLogOut().add(user);
    }

}