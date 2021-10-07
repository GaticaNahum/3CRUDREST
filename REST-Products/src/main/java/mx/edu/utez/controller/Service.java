package mx.edu.utez.controller;

import mx.edu.utez.model.DaoProducts;
import mx.edu.utez.model.Products;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/products")
public class Service {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Products> getOffices(){
        return new DaoProducts().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Products getOffices(@PathParam("id") String productCode){
        return new DaoProducts().findById(productCode);
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Products save(MultivaluedMap<String, String> formParams){
        String productCode = formParams.get("officeCode").get(0);
        if(new DaoProducts().insertProduct(getParams(productCode, formParams), true))
            return new DaoProducts().findById(productCode);
        return null;
    }

    @POST
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Products save(@PathParam("id") String productCode, MultivaluedMap<String, String> formParams){
        if(new DaoProducts().insertProduct(getParams(productCode, formParams), false))
            return new DaoProducts().findById(productCode);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String productCode){
        return new DaoProducts().delete(productCode);
    }

    private Products getParams(String productCode, MultivaluedMap<String, String> formParams) {
        String productName = formParams.get("productName").get(0);
        String productLine = formParams.get("productLine").get(0);
        String productScale = formParams.get("productScale").get(0);
        String productVendor = formParams.get("productVendor").get(0);
        String productDescription = formParams.get("productDescription").get(0);
        String quantityInStock = formParams.get("quantityInStock").get(0);
        String buyPrice = formParams.get("buyPrice").get(0);
        String MSRP = formParams.get("MSRP").get(0);

        Products products = new Products(productCode,productName,productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice,MSRP);
        System.out.println(products);
        return products;
    }


}
