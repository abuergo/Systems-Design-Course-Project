package utn.ddsG8.impacto_ambiental.domain.movilidad;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.converters.DistanciaConverter;
import utn.ddsG8.impacto_ambiental.db.converters.LocalTimeAttributeConverter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@Table(name = "trayecto")
public class Trayecto extends Persistable {

    @Column(name = "fecha")
    @Convert(converter = LocalTimeAttributeConverter.class)
    protected LocalDate fecha;

    @ManyToMany
    @JoinTable(
            name = "trayecto_miembro",
            joinColumns = @JoinColumn(name = "trayecto_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trayecto_org",
            joinColumns = @JoinColumn(name = "trayecto_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id")
    )
    private Set<Organizacion> organizaciones;

    // es la forma fea de hacer u one-to-many unidireccional
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto", referencedColumnName = "id")
    private List<Tramo> tramos;

    @Column(name = "distancia")
    @Convert(converter = DistanciaConverter.class)
    private Distancia distancia;


    @Column(name = "huella")
    private double huella;

    public Trayecto() {
        this.miembros = new ArrayList<Miembro>() ;
        this.organizaciones = new HashSet<>();
        this.tramos = new ArrayList<Tramo>() ;
        this.fecha = LocalDate.now();
        this.setDistancia();
    }

    public Distancia getDistancia() {
        if (distancia == null) this.setDistancia();
        return distancia;
    }

    public Distancia getDistanciaDeTramo(int index) {
        return tramos.get(index).getDistancia();
    }



    private void setDistancia() {
        if (tramos.size() == 0) setDistancia(new Distancia(0, "KM"));
        else {
            double valor = tramos.stream().mapToDouble(t -> t.getDistancia().valor) .sum();
            final String unidad = tramos.get(0).getDistancia().unidad;
            this.distancia = new Distancia(valor, unidad);
        }
    }

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        this.setDistancia();
    }

    public void agregarTramos(Tramo ... tramo) {
        Stream.of(tramo).forEach(t -> tramos.add(t));
    }

    public  void agregarOrganizacion(Organizacion unaOrg) {
        unaOrg.getTrayectos().add((this));
        this.organizaciones.add(unaOrg);
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public double getHuella() {
        return calcularHC();
    }

    public void setHuella() {
        this.tramos.forEach(Tramo::setHuella);
        this.huella = calcularHC();
    }

    public double calcularHC(){
        double hc = 0;

        for ( Tramo tramo: tramos) {
            hc += tramo.calcularHC();
        }
        this.huella = (double) Math.round(hc*100)/100;
        return this.huella;
    }


//    public double CalcularHCTrayectoMensual(CalcularHC calculador, int mes, int anio){
//        double hc = 0;
//
//        for ( Tramo tramo: tramos) {
//            hc += tramo.calcularHCMensual(mes,anio);
//        }
//        return hc;
//
//    }
//    public double CalcularHCTrayectoAnual(CalcularHC calculador, int anio){
//        double hc = 0;
//
//        for ( Tramo tramo: tramos) {
//            hc += tramo.calcularHCAnual(anio);
//        }
//        return hc;
//
//    }

    public boolean formaParte(Organizacion organizacion) {
//        return organizaciones.contains(organizacion);
        return organizaciones.stream().anyMatch(o -> o.getId() == organizacion.getId());
    }
}
