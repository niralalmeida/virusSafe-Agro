/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tovrestws.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import tovrestws.Choiceoption;

/**
 *
 * @author hoy
 */
@Stateless
@Path("tovrestws.choiceoption")
public class ChoiceoptionFacadeREST extends AbstractFacade<Choiceoption> {

    @PersistenceContext(unitName = "TomatoVirusDBPU")
    private EntityManager em;

    public ChoiceoptionFacadeREST() {
        super(Choiceoption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Choiceoption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Choiceoption entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Choiceoption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Choiceoption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Choiceoption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("quizQuestion/findAllOptionsByChoiceQuestionId/{choiceQuestionId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findAllOptionsByChoiceQuestionId(
            @PathParam("choiceQuestionId") Integer choiceQuestionId){
        TypedQuery dquery = 
                em.createQuery(
                        "SELECT co.choiceOptionId, co.choiceOptionLabel, co.choiceOptionContent "
                        + "FROM Choiceoption AS co "
                        + "WHERE co.choiceOptionQuestionId.choiceQuestionId = :choiceQuestionId",
                        Object.class);
        dquery.setParameter("choiceQuestionId", choiceQuestionId);
        List<Object[]> queryList = dquery.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row : queryList){
            JsonObject choiceOptionObject = Json.createObjectBuilder()
                    .add("choiceOptionId", (Integer)row[0])
                    .add("choiceOptionLabel", (Character)row[1])
                    .add("choiceOptionContent", (String)row[2]).build();
            arrayBuilder.add(choiceOptionObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
}
