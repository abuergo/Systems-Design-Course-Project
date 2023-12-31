package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@DiscriminatorValue("localidad")
public class Localidad extends SectorTerritorial implements Serializable {

    @Column
    public int codPostal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio", referencedColumnName = "id_db")
    public Municipio municipio;

    public Localidad() {}
    public Localidad(int id, String nombre, int codPostal, Municipio municipio) {
        this.id = id;
        this.codPostal = codPostal;
        this.municipio = municipio;
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public SectorTerritorial getPadre() {
        return null;
    }

    @Override
    public boolean tieneDireccion(Direccion direccion) {
        return false;
    }
}
