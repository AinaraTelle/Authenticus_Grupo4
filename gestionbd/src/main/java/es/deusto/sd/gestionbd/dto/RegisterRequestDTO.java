package es.deusto.sd.gestionbd.dto;



public class RegisterRequestDTO {

    private String nombre;
    private String email;
    private String password;
    private int tel;

    public RegisterRequestDTO(){
    }

    public RegisterRequestDTO( String nombre, String email, String password, int tel) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTel() {
        return tel;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "RegisterRequestDTO [nombre=" + nombre + ", email=" + email + ", password=" + password
                + ", tel=" + tel + "]";
    }
}

