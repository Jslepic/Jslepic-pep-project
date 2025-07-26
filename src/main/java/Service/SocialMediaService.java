package Service;
import Model.Account;
import Model.Message;
import DAO.SocialMediaDAO;

import java.util.List;

public class SocialMediaService {

    private SocialMediaDAO dao;

    public SocialMediaService() {
        this.dao = new SocialMediaDAO();
    }

    // 1: Register new user 
    public Account registerUser(Account user) {
        if(user.getUsername() == null || user.getUsername().isEmpty()) {
            return null;
        } 
        if(user.getPassword() == null || user.getPassword().length() < 4) {
            return null;
        }
        if(dao.getAccountByUsername(user.getUsername()) != null) {
            return null;
        }
        return dao.regNewUser(user);
    }

     // 2: Login user
    public Account loginUser(Account user) {
        if(user.getUsername() == null) {
            return null;
        } 
        if(user.getPassword() == null) {
            return null;
        }
        return dao.proUserLoginAccount(user);
    }

     // 3: Create message 
    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty()) {
            return null;
        }
        if (message.getMessage_text().length() > 255){
            return null;
        } 
        // Check if user exists
        if (dao.getAccountById(message.getPosted_by()) == null) {
            return null;
        } 

        return dao.createMessage(message);
    }

    // 4: Get all messages
    public List<Message> getAllMessages() {
        return dao.getAllMessages();
    }

    // 5: Get message by ID
    public Message getMessageById(int messageId) {
        return dao.getMessageById(messageId);
    }

    // 6: Delete message
    public Message deleteMessage(int messageId) {
        return dao.deleteMessage(messageId);
    }

    // 7: Update message
    public Message updateMessage(int messageId, String newText) {
        if (newText == null || newText == "" || newText.length() > 255) {
            return null;
        } 
        if (dao.getMessageById(messageId) == null) {
            return null;
        }

        return dao.updateMessage(messageId, newText);
    }

    // 8: Get messages by user
    public List<Message> getMessagesByUserId(int accountId) {
        return dao.getMessagesByUserId(accountId);
    }

}
