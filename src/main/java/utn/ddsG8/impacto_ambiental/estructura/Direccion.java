package utn.ddsG8.impacto_ambiental.estructura;

import utn.ddsG8.impacto_ambiental.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.services.distancia.Municipio;

public class Direccion {
    private String nombre;
    private String calle;
    private Integer altura;

    public Direccion(String nombre, String calle, Integer altura, Localidad localidad) {
        this.nombre = nombre;
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
    }

    private Localidad localidad;

    public Localidad getLocalidad() {
        return this.localidad;
    }

    public String getCalle() {
        return this.calle;
    }

    public String getAltura() {
        return this.altura.toString();
    }
}
