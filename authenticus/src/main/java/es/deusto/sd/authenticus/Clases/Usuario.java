package es.deusto.sd.authenticus.Clases;

public class Usuario {
    private int IDCaso;
    private String email;
    private String password;
    private String nombre;
    private int tel;


    
    public void setIDCaso(int iDCaso) {
        IDCaso = iDCaso;
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
    public int getIDCaso() {
        return IDCaso;
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

    
}
