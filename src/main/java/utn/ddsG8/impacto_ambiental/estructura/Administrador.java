package utn.ddsG8.impacto_ambiental.estructura;

import utn.ddsG8.impacto_ambiental.movilidad.transportes.publico.*;

public class Administrador {
    private String usuario;
    private String contrasenia;


    public void CrearColectivo(String nombre){
        Colectivo unColectivo = new Colectivo(nombre);

    }
    public void CrearSubte(String nombre){
        Subte unColectivo = new Subte(nombre);

    }
    public void CrearTren(String nombre){
        Tren unColectivo = new Tren(nombre);

    }
    //String nombre, TransportePublico transportePublico, Direccion direccion, float distanciaProxima, float distanciaAnterior, int i
    public void AgregarParadaTransportePublico(String nombre, TransportePublico transportePublico, Direccion direccion,
                                               float distanciaProxima, float distanciaAnterior, int i ){
        // TODO: Ver si se le saca la distancia entre paradas entre el constructor.
        Parada nuevaParada = new Parada(nombre,direccion);
        transportePublico.agregarParada(nuevaParada,distanciaProxima,distanciaAnterior,i);
    }


    // TODO: LEVANTAR ARCHIVO O PONER FIJO CADA FE DE LA TABLA DE LA ENTREGA 2

    public  void ModificarFactirEmision (CalcularHC calculador, String actividad, String tipoConsumo, double factorEmision){
        //TODO : MODIFICA UNO.
        // buscar en la lsita cual es
        // modificar por el factor de emision nuevo.

    }
}
