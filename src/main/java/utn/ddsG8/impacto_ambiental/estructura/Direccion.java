package utn.ddsG8.impacto_ambiental.estructura;

import utn.ddsG8.impacto_ambiental.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.services.distancia.Municipio;

public class Direccion {
    private String nombre;
    private String calle;
    private Integer altura;

    //todo va la provincia o no?
    private Provincia provincia;
    //todo va la Municipalidad o no?
    private Municipio municipio;

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