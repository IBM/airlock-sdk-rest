package com.ibm.app.services;

import com.google.gson.Gson;
import com.ibm.airlock.rest.facades.ProductFacade;
import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.sdk.AbstractMultiProductManager;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.app.Constants;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/products")
@Api(value = "Products", description = "Airlock product rest API")
public class AirlockProduct {
    private final Logger logger = Logger.getLogger(AirlockProduct.class.toString());
    @Context
    private ServletContext context;


    @POST
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Initializes a product in Airlock. Must be called once before any " +
            "other Airlock function can be used in this product. In case the product is already " +
            "initialized and the product's version or defaults file does not match the previously " +
            "initialized product then all of the product's cached data will be cleared except for user groups. " +
            "Otherwise, this operation will have no affect.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product init completed successfully", response = Product.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response init(@ApiParam(value = "The current version of the application requesting the initialization." +
            " The version range of the provided defaults file must include this version.", example = "1.0.0", required = true) @QueryParam("appVersion") String appVersion,
                         @ApiParam(value = "The product encryption key. The key is intended to protect a product cached data, " +
                                 "is specific for each product and could be downloaded from the Airlock Control Center.") @QueryParam("encryptionKey") String encryptionKey,
                         @ApiParam(value = "The contents of the defaults file downloaded from the Airlock Control Center.", required = true, name = "productDefaults",
                                 examples = @Example(value = {
                                         @ExampleProperty(mediaType = MediaType.APPLICATION_JSON, value = "{}")})) final Object productDefaults) {
        String instanceId = UUID.randomUUID().toString();
        try {
            String defaultFileContentJson = new Gson().toJson(productDefaults);
            com.ibm.airlock.rest.common.Response response = ProductFacade.init(context.getAttribute(Constants.AIRLOCK_CACHE_LOCATION).toString(),
                    defaultFileContentJson, encryptionKey, appVersion, instanceId);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Init failed: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{productInstanceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves a product by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve a product completed successfully", response = Product.class),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response getProductByID(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductFacade.get(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all products instances.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve all products completed successfully", response = Product.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getAllProducts() {
        com.ibm.airlock.rest.common.Response response = ProductFacade.getAll();
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @DELETE
    @Path("/{productInstanceId}")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Removes the specified product completely from the system including all cached data related to it. " +
            "Parallel to uninstalling the application.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete a product completed successfully", response = String.class),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response delete(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductFacade.delete(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    public static Product getProductById(String instanceId) {
        Collection<AirlockMultiProductsManager.ProductMetaData> products = AirlockMultiProductsManager.getInstance().getAllProducts();
        for (AirlockMultiProductsManager.ProductMetaData product : products) {
            if (product.getInstanceId().equals(instanceId)) {
                return new Product(product);
            }
        }
        return null;
    }

    public static String getProductId(String defaultFile) throws JSONException {
        if (defaultFile == null) {
            return null;
        } else {
            JSONObject defaultFileJson = new JSONObject(defaultFile);
            return defaultFileJson.optString("productId");
        }
    }
}
