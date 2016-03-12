package com.mobiledev.money.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mobiledev.money.controller.ConverterController;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("currencies")
public class CurrencyService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrenciesList() {
        Response response;
        
        try {
            String json = ConverterController.getInstance().getCurrencies();
            response = Response.ok(json).build();
        } catch (JsonProcessingException ex) {
            response = Response.serverError().build();
            Logger.getLogger(CurrencyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
}
