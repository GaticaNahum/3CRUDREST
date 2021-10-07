package mx.edu.utez.controller;

import mx.edu.utez.model.DaoOffice;
import mx.edu.utez.model.Office;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.Path;
import java.util.List;

@Path("/offices")
public class Service {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Office> getOffices(){
        return new DaoOffice().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Office getOffices(@PathParam("id") String officeCode){
        return new DaoOffice().findById(officeCode);
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Office save(MultivaluedMap<String, String> formParams){
        String officeCode = formParams.get("officeCode").get(0);
        if(new DaoOffice().insertOffice(getParams(officeCode, formParams), true))
            return new DaoOffice().findById(officeCode);
        return null;
    }

    @POST
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Office save(@PathParam("id") String officeCode, MultivaluedMap<String, String> formParams){
        if(new DaoOffice().insertOffice(getParams(officeCode, formParams), false))
            return new DaoOffice().findById(officeCode);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String officeCode){
        return new DaoOffice().delete(officeCode);
    }

    private Office getParams(String officeCode, MultivaluedMap<String, String> formParams) {
        String city = formParams.get("city").get(0);
        String phone = formParams.get("phone").get(0);
        String address1 = formParams.get("addressLine1").get(0);
        String address2 = formParams.get("addressLine2").get(0);
        String state = formParams.get("state").get(0);
        String postalCode = formParams.get("postalCode").get(0);
        String country = formParams.get("country").get(0);
        String territory = formParams.get("territory").get(0);

        Office office = new Office(officeCode,city,phone, address1, address2, state, country, postalCode,territory);
        System.out.println(office);
        return office;
    }

}
