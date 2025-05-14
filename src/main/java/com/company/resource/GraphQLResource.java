package com.company.resource;

import com.company.entity.Mobile;
import com.company.exception.ResourceAlreadyPresentException;
import com.company.exception.ResourceNotFoundException;
import com.company.service.MobileService;
import com.company.websocket.MobileWebSocket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@GraphQLApi
public class GraphQLResource {

    @Inject
    private MobileService mobileService;


    @Query("getAllMobiles") // Expose as a GraphQL query
    public List<Mobile> getAllMobiles() {
        List<Mobile> allMobiles = mobileService.getAllMobiles();
        if(allMobiles.isEmpty())
        {
            throw new ResourceNotFoundException("Mobiles list is Empty");
        }else {
            return allMobiles;
        }
    }

    @Query("getMobileById") // Expose as a GraphQL query
    public Mobile getMobileById(@PathParam("id") Long id) {
        Mobile mobile = mobileService.getMobileById(id);
        if (mobile == null) {
            throw new ResourceNotFoundException("Mobile not found with ID: " + id);
        }
        return mobile;
    }


        @Mutation("addMobile") // Expose as a GraphQL mutation
        public Mobile addMobile(Mobile mobile) {
            if (mobileService.findByMobileName(mobile.getMobileName()).isEmpty()) {
            Mobile createdMobile = mobileService.addMobile(mobile);
            // Broadcast message to frontend clients
            MobileWebSocket.broadcast("new-mobile-added");
            return createdMobile;

        }else{
            throw new ResourceAlreadyPresentException("Mobile with Name: " + mobile.getMobileName() + " already exists.");
        }
    }

    @Mutation("updateMobile") // Expose as a GraphQL mutation
    public Mobile updateMobile(@PathParam("id") Long id, Mobile mobile) {
        Mobile mobile2 = mobileService.getMobileById(id);
        if(mobile2 == null)
        {
            throw new ResourceNotFoundException("No Such Mobile Exist with ID: "+id);
        }else {
            // Broadcast message to frontend clients
            MobileWebSocket.broadcast("mobile-updated");
            return mobileService.updateMobile(id, mobile);
        }
    }

    @Mutation("deleteMobile") // Expose as a GraphQL mutation
    public String deleteMobile(@PathParam("id") Long id) {
        Mobile mobile = mobileService.getMobileById(id);
        if(mobile == null)
        {
            throw new ResourceNotFoundException("No Such Mobile Exist with ID: "+id);
        }else {
            mobileService.deleteMobile(id);
            // Broadcast message to frontend clients
            MobileWebSocket.broadcast("mobile-deleted");
            return "Deleted successfully";
        }
    }
}
