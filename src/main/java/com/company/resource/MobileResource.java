package com.company.resource;

import com.company.entity.Mobile;
import com.company.exception.ResourceAlreadyPresentException;
import com.company.exception.ResourceNotFoundException;
import com.company.service.MobileService;
import com.company.websocket.MobileWebSocket;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;


@Path("/mobiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MobileResource {

    @Inject
    private MobileService mobileService;



    @GET
    public List<Mobile> getAllMobiles() {
        return Optional.ofNullable(mobileService.getAllMobiles())
                .filter(mobiles -> !mobiles.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("Mobiles list is Empty"));
    }

    @GET
    @Path("/{id}")
    public Mobile getMobileById(@PathParam("id") Long id) {
        return Optional.ofNullable(mobileService.getMobileById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Mobile not found with ID: " + id));
    }

    @POST
    public Response addMobile(Mobile mobile) {
    if (mobileService.findByMobileName(mobile.getMobileName()).isEmpty()) {
        Mobile createdMobile = mobileService.addMobile(mobile);
        // Broadcast message to frontend clients
            MobileWebSocket.broadcast("new-mobile-added");
        return Response.status(Response.Status.CREATED).entity(createdMobile).build();
    } else {
        throw new ResourceAlreadyPresentException("Mobile with Name: " + mobile.getMobileName() + " already exists.");
    }
}

    @PUT
    @Path("/{id}")
    public Mobile updateMobile(@PathParam("id") Long id, Mobile mobile) {
        return Optional.ofNullable(mobileService.getMobileById(id))
                .map(existingMobile -> {
                    Mobile updatedMobile = mobileService.updateMobile(id, mobile);
                    // Broadcast message to frontend clients
                    MobileWebSocket.broadcast("mobile-updated");
                    return updatedMobile;
                })
                .orElseThrow(() -> new ResourceNotFoundException("No Such Mobile Exist with ID: " + id));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMobile(@PathParam("id") Long id) {
        return Optional.ofNullable(mobileService.getMobileById(id))
                .map(mobile -> {
                    mobileService.deleteMobile(id);
                    // Broadcast message to frontend clients
                    MobileWebSocket.broadcast("mobile-deleted");
                    return Response.ok("Deleted successfully").build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("No Such Mobile Exist with ID: " + id));
    }


}









//    @GET
//    public List<Mobile> getAllMobiles() {
//        List<Mobile> allMobiles = mobileService.getAllMobiles();
////        int a =10;
////        int b=0;
////        int c= a/b;
//        if(allMobiles.isEmpty())
//        {
//            throw new ResourceNotFoundException("Mobiles list is Empty");
//        }else {
//            return allMobiles;
//        }
//    }
//
//    @GET
//    @Path("/{id}")
//    public Mobile getMobileById(@PathParam("id") Long id) {
//        Mobile mobile = mobileService.getMobileById(id);
//        if (mobile == null) {
//            throw new ResourceNotFoundException("Mobile not found with ID: " + id);
//        }
//        return mobile;
//    }
//
//
//    @POST
//    public Response addMobile(Mobile mobile) {
//        if (mobileService.findByMobileName(mobile.getMobileName()).isEmpty()) {
//            Mobile createdMobile = mobileService.addMobile(mobile);
//            // Broadcast message to frontend clients
//            MobileWebSocket.broadcast("new-mobile-added");
//            return Response.status(Response.Status.CREATED).entity(createdMobile).build();
//
//        }else{
//            throw new ResourceAlreadyPresentException("Mobile with Name: " + mobile.getMobileName() + " already exists.");
//        }
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Mobile updateMobile(@PathParam("id") Long id, Mobile mobile) {
//        Mobile mobile2 = mobileService.getMobileById(id);
//        if(mobile2 == null)
//        {
//            throw new ResourceNotFoundException("No Such Mobile Exist with ID: "+id);
//        }else {
//            // Broadcast message to frontend clients
//            MobileWebSocket.broadcast("mobile-updated");
//            return mobileService.updateMobile(id, mobile);
//        }
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteMobile(@PathParam("id") Long id) {
//        Mobile mobile = mobileService.getMobileById(id);
//        if(mobile == null)
//        {
//            throw new ResourceNotFoundException("No Such Mobile Exist with ID: "+id);
//        }else {
//            mobileService.deleteMobile(id);
//            // Broadcast message to frontend clients
//            MobileWebSocket.broadcast("mobile-deleted");
//            return Response.ok("Deleted successfully").build();
//        }
//    }

