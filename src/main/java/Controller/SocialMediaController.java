package Controller;

import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService socialMediaService;

    public SocialMediaController() {
        this.socialMediaService = new SocialMediaService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::regNewUserHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::createNewMesHandler);
        app.get("/messages", this::getAllMesHandler);
        app.get("/messages/{message_id}", this::getMesByIDHandler);
        app.delete("/messages/{message_id}", this::delMesByIDHandler);
        app.patch("/messages/{message_id}", this::upMesByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMesByUserHandler);
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    // 1: Register user
    private void regNewUserHandler(Context context) {
        Account user = context.bodyAsClass(Account.class);
        Account success = socialMediaService.registerUser(user);

        if (success != null) {
            context.json(success);
        } else {
            context.status(400); 
        }
    }
    // 2: Login user
    private void userLoginHandler(Context context) {
        Account user = context.bodyAsClass(Account.class);
        Account result = socialMediaService.loginUser(user);

        if (result != null) {
            context.json(result);
        } else {
            context.status(401); 
        }
    }
    // 3: Create message
    private void createNewMesHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message result = socialMediaService.createMessage(message);

        if (result != null) {
            context.json(result);
        } else {
            context.status(400); 
        }
    }
    // 4: Get all messages
    private void getAllMesHandler(Context context) {
        List<Message> messages = socialMediaService.getAllMessages();
        context.json(messages); 
    }
    // 5: Get message by ID
    private void getMesByIDHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = socialMediaService.getMessageById(messageId);

        if (message != null) {
            context.json(message);
        } else {
            context.json(""); 
        }
    }
    // 6: Delete message by ID
    private void delMesByIDHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message result = socialMediaService.deleteMessage(messageId);

        if (result != null) {
            context.json(result);
        } else {
            context.json(""); 
        }
    }
    // 7: Update message by ID
    private void upMesByIDHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message newMes = context.bodyAsClass(Message.class);
        Message result = socialMediaService.updateMessage(messageId, newMes.getMessage_text());

        if (result != null) {
            context.json(result);
        } else {
            context.status(400); 
        }
    }
    // 8: Get all messages by user ID
    private void getAllMesByUserHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = socialMediaService.getMessagesByUserId(accountId);
        context.json(messages);
    }

}