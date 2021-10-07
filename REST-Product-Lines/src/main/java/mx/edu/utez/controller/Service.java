package mx.edu.utez.controller;

import mx.edu.utez.model.DaoProduct_Lines;
import mx.edu.utez.model.Product_Lines;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/product")
public class Service {


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product_Lines> getProductLine(){
        return new DaoProduct_Lines().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product_Lines getProductLine(@PathParam("id") String productLine){
        return new DaoProduct_Lines().findById(productLine);
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Product_Lines save(MultivaluedMap<String, String> formParams){
        String productLine = formParams.get("productLine").get(0);
        if(new DaoProduct_Lines().insertProductLine(getParams(productLine, formParams), true))
            return new DaoProduct_Lines().findById(productLine);
        return null;
    }

    @POST
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Product_Lines save(@PathParam("id") String productLine, MultivaluedMap<String, String> formParams){
        if(new DaoProduct_Lines().insertProductLine(getParams(productLine, formParams), false))
            return new DaoProduct_Lines().findById(productLine);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String productLine){
        return new DaoProduct_Lines().delete(productLine);
    }

    private Product_Lines getParams(String productLine, MultivaluedMap<String, String> formParams) {
        String textDescription = formParams.get("textDescription").get(0);
        String htmlDescription = formParams.get("htmlDescription").get(0);
        String image = formParams.get("image").get(0);

        Product_Lines productLines = new Product_Lines(productLine,textDescription,htmlDescription, image);
        System.out.println(productLines);
        return productLines;
    }
}
