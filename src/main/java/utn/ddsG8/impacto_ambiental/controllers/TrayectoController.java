package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayectoController {

    private static Repositorio<Parada> paradaRepo = FactoryRepositorio.get(Parada.class);
    private static Repositorio<Trayecto> trayectosRepo = FactoryRepositorio.get(Trayecto.class);
    private static Repositorio<TransportePublico> transportesPublicos = FactoryRepositorio.get(TransportePublico.class);
    private static Repositorio<Transporte> transportesRepo = FactoryRepositorio.get(Transporte.class);
    private static List<Localidad> localidadList = FactoryRepositorio.get(Localidad.class).buscarTodos();

    public static ModelAndView crearTrayectoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembroInURL(request);

        List<Transporte> transportesDeMiembro = transportesRepo.query("from transporte where duenio = " + miembro.getId());
        List<TransportePublico> tpublicos = transportesPublicos.buscarTodos();
        List<Parada> paradas = paradaRepo.buscarTodos();


        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidades", localidadList);
        parametros.put("transportesPublicos", tpublicos);
        parametros.put("vehiculosParticular", transportesDeMiembro);
        parametros.put("paradas", paradas);
        return new ModelAndView(parametros, "/trayecto/newTrayecto.hbs");
    }

    public static ModelAndView mostrarTrayectosMiembro(Request request, Response response) {
//        List<Trayecto> trayectos = trayectosRepo.buscarTodos().stream().filter(p -> {
//            return !p.getMiembros().contains(miembros.buscar(request.params("id")));
//        }).collect(Collectors.toList());
//        List<Trayecto> trayectos =  trayectosRepo.query("from Trayecto where miembros ='" + request.params("id") + "'");
        String query = "from Trayecto t join t.miembros tm where tm.id = '"+ request.params("id") + "'";
        List<Trayecto> trayectos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
//        trayectos.forEach(tr -> System.out.println(tr.getId()));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayecto", trayectos);

        return new ModelAndView(parametros, "trayecto/trayectosMiembro.hbs");
    }
}