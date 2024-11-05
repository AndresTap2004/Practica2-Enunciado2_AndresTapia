package com.example.rest;

import controller.Dao.servicies.GeneradorServicies;
import models.Generador;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

@Path("generador")
public class GeneradorApi {
    private GeneradorServicies gs = new GeneradorServicies();
    private Gson gson = new Gson();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGeneradores() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "Ok");
        map.put("data", gs.listAll().toArray());
        if (gs.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenerador(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Generador generador = gs.get(id);
            if (generador != null) {
                map.put("msg", "Ok");
                map.put("data", generador);
                return Response.ok(map).build();
            } else {
                map.put("msg", "No se encontró generador con ese identificador");
                return Response.status(Status.NOT_FOUND).entity(map).build();
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGenerador(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Generador generador = new Generador(0, 
                map.get("nombre").toString(), 
                Double.parseDouble(map.get("costo").toString()),
                Double.parseDouble(map.get("consumoPorHora").toString()),
                Integer.parseInt(map.get("potencia").toString()),
                map.get("uso").toString());
                
            gs.setGenerador(generador);
            gs.save();

            res.put("msg", "Ok");
            res.put("data", "Generador guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGenerador(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Integer id = Integer.parseInt(map.get("idGenerador").toString());
            Generador generador = gs.get(id);
            if (generador != null) {
                generador.setNombre(map.get("nombre").toString());  
                generador.setCosto(Double.parseDouble(map.get("costo").toString()));
                generador.setConsumoPorHora(Double.parseDouble(map.get("consumoPorHora").toString()));
                generador.setPotencia(Integer.parseInt(map.get("potencia").toString()));
                generador.setUso(map.get("uso").toString());

                gs.setGenerador(generador);
                gs.update();

                res.put("msg", "Ok");
                res.put("data", "Generador actualizado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Generador no encontrado");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGenerador(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            System.out.println("aqui try");
            Integer id = Integer.parseInt(map.get("idGenerador").toString());
            if (gs.delete(id)) {
                res.put("msg", "Ok");
                res.put("data", "Generador eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Generador no encontrado");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("aqui cathc");
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


}
