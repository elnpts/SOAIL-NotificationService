package project.ms.NotificationService;

import java.util.*;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.ms.NotificationService.model.Notification;

@Service
public class NotificationService {
    private final List<Notification> notifications = new ArrayList<>();

    public void sendNotification(Notification notification) {
        notifications.add(notification);
    }
    
    public List<Notification> getAllNotifications() {
    	return notifications;
    }

    public List<Notification> getNotificationsForUser(String username) {
    	List<Notification> notificationsForUser = new ArrayList<>();
    	for (Notification n : notifications) {
    		if (n.getSender().equals(username) || n.getReceiver().equals(username)) {
    			notificationsForUser.add(n);
    		}
    	}
        return notificationsForUser;
    }
    
    private Notification getNotificationByID(Long id) {
    	for (Notification n : notifications) {
    		if(n.getId().equals(id)) {
    			return n;
    		}
     	}
    	return null;
    }
    
    public void deleteNotification(Long id) {
    	notifications.removeIf(notification -> notification.getId().equals(id));
    }
    
    public void updateNotification(Long id, String newMessage) {
    	Notification n = getNotificationByID(id);
    	if(n != null) {
    		n.setMessage(newMessage);
    	}
    }
}
