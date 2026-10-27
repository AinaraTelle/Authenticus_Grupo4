package es.deusto.sd.user_interface.service;

import es.deusto.sd.user_interface.dto.LoginRequestDTO;
import es.deusto.sd.user_interface.dto.RegisterRequestDTO;
import es.deusto.sd.user_interface.external.AuthenticusGateway;

public class UserService {
    private final AuthenticusGateway gateway = new AuthenticusGateway();

    public synchronized boolean signUp(String name, String email, String password, String phone) {
        if (email == null || email.isEmpty() ) return false;

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(name, email,password,Integer.parseInt(phone));
        
        boolean correcto= gateway.signUp(registerRequestDTO);

        return correcto;
    }

    public boolean login(String email, String password) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email,password);
        try {
            boolean correcto = gateway.login(loginRequestDTO);
            return correcto;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout(String userEmailLogueado) {
        try {
            String token = gateway.getToken(userEmailLogueado);
            boolean correcto = gateway.logout(token);
            return correcto;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
     }

    // public synchronized boolean removeCurrentUser() {
    //     if (currentUser == null) return false;
    //     users.remove(currentUser.getEmail());
    //     currentUser = null;
    //     return true;
    // }

    // public synchronized UserItem getCurrentUser() { return currentUser; }

    // public synchronized List<UserItem> listUsers() { return new ArrayList<>(users.values()); }
}