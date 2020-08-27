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
import tovrestws.Choicequestion;

/**
 *
 * @author hoy
 */
@Stateless
@Path("tovrestws.choicequestion")
public class ChoicequestionFacadeREST extends AbstractFacade<Choicequestion> {

    @PersistenceContext(unitName = "TomatoVirusDBPU")
    private EntityManager em;

    public ChoicequestionFacadeREST() {
        super(Choicequestion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Choicequestion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Choicequestion entity) {
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
    public Choicequestion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Choicequestion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Choicequestion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("quizQuestion/findAllQuestionsByVirusId/{virusId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findAllQuestionsByVirusId(
            @PathParam("virusId") Integer virusId){
        TypedQuery dquery = 
                em.createQuery(
                        "SELECT cq.choiceQuestionId, cq.choiceQuestionContent, cq.choiceQuestionType"
                        + "FROM Choicequestion cq"
                        + "WHERE cq.choiceQuestionVirusId = :virusId",
                        Object.class);
        dquery.setParameter("virusId", virusId);
        List<Object[]> queryList = dquery.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row : queryList){
            JsonObject choiceQuestionObject = Json.createObjectBuilder()
                    .add("choiceQuestionId", (String)row[0])
                    .add("choiceQuestionContent", (String)row[1])
                    .add("choiceQuestionType", (Long)row[2]).build();
            arrayBuilder.add(choiceQuestionObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
}
