package utn.ddsG8.impacto_ambiental.model.movilidad.transportes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("bici")
public class Bicicleta extends TransportePrivado {

    public Bicicleta() {
        this.nombreFE = "Bici";
    }


}
