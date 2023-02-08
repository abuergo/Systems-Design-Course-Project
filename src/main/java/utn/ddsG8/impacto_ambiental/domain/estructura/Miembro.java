package utn.ddsG8.impacto_ambiental.domain.estructura;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "miembro")
public class Miembro   {

    @Id
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_doc")
    private TipoDoc tipoDoc;

    @Column(name = "documento")
    private String documento;

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY)
    private List<Sector> sectores;

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Trayecto> trayectos;

    public Miembro(){}

    public Miembro(String nombre, String apellido, TipoDoc tipoDoc, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.sectores = new ArrayList<Sector>();
        this.trayectos = new ArrayList<Trayecto>();
    }

    public void agregarSector(Sector sector) {
        this.sectores.add(sector);
        sector.agregarMiembro(this);
    }

    // TODO: estoy suponiendo que en una direccion solo hay una org
    public Organizacion EstaYendoAOrganizacion(Direccion unaDireccion){
        for (Sector unSector:this.sectores) {
            Organizacion org = unSector.getOrganizacion();
            Direccion dir = org.getDireccion();
            if(dir == unaDireccion ){
                return org;
            }

        }
        return null;
    }

    public void unirseAOrg(Organizacion org, Sector sector) {
        org.solicitudNuevoMiembro(this, sector);
    }

    public void sumarseATrayecto(int id, Organizacion org) {
        Trayecto trayecto = org.getTrayectos().stream().filter(t -> t.getId() == id)
                .findAny()
                .orElse(null);

        trayecto.agregarMiembro(this);
    }

    public void agregarATrayecto(Trayecto trayecto) {
        trayecto.agregarMiembro(this);
    }
}
