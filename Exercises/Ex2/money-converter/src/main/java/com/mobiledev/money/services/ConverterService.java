package com.mobiledev.money.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mobiledev.money.controller.ConverterController;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("convert")
public class ConverterService {
    /**
     *
     * @param from base currency used to convert to targeted currencies
     * @param amount base currency's value
     * @param to list of targeted currencies
     * @return JSON string
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response convert(
            @DefaultValue("USD") @QueryParam("from") String from,
            @DefaultValue("1") @QueryParam("amount") Double amount,
            @DefaultValue("VND") @QueryParam("to") String to) {
        Response response;
        
        try {
            String json = ConverterController.getInstance().convert(from, amount, to);
            response = Response.ok(json).build();
        } catch (JsonProcessingException | UnsupportedEncodingException ex) {
            response = Response.serverError().build();
            Logger.getLogger(ConverterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }

}
