package com.bystrican.Notes;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskRest {

    @Inject
    TaskService taskService;
    //get either all objects or search
    @GET
    @Path("")
    public Response getAll(@QueryParam("search") String search) {
        if(search!=null){
            return Response.ok(taskService.search(search)).build();
        }
        return Response.ok(taskService.read()).build();
    }
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") final int id) {
        Task task = taskService.read(id);
        if(task ==null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(taskService.read(id)).build();
    }

    @POST
    @Path("")
    public Response PostOne(Task task) {
        taskService.insert(task);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response PutOne(@PathParam("id") final int id, Task task) {
        Task updateTask = taskService.read(id);
        if(task.getName()!=null)updateTask.setName(task.getName());
        if(task.getDescription()!=null)updateTask.setDescription(task.getDescription());
        if(task.getPriority()!=null)updateTask.setPriority(task.getPriority());
        if(task.getDone()!=null)updateTask.setDone(task.getDone());
        taskService.update(updateTask);
        return Response.ok().build();
    }
    @DELETE
    @Path("{id}")
    public Response deleteOne(@PathParam("id") final int id) {
        taskService.delete(id);
        return Response.ok().build();
    }

}
