package project.ms.NotificationService;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import project.ms.NotificationService.model.Notification;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	private final List<Notification> notifications = new ArrayList<>();
	private RestTemplate restTemplate = new RestTemplate();
	
	public void MSToController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@GetMapping("/universityService")
	public String invokeMS() {
		return restTemplate.getForObject("http://localhost:8980" + "/universityService/connect", String.class);
	}
	
	@PostMapping("/send")
	public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        notifications.add(notification);
        return new ResponseEntity<>("Success: Notification sent!", HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Notification>> getAllNotifications() {
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<List<Notification>> getNotificationsForUser(String username) {
		List<Notification> notificationsForUser = new ArrayList<>();
		for (Notification n : notifications) {
			if (n.getSender().equals(username) || n.getReceiver().equals(username)) {
				notificationsForUser.add(n);
			}
		}
		return new ResponseEntity<>(notificationsForUser, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Notification> getNotificationByID(Long id) {
		Notification n = getNotificationById(id);
		if(n != null) {
			return new ResponseEntity<>(n, HttpStatus.OK);
	 	}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNotification(Long id) {
		if (notifications.removeIf(notification -> notification.getId().equals(id))) {
			return new ResponseEntity<>("Success: Notification deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> updateNotification(Long id, String newMessage) {
		Notification n = getNotificationById(id);
		if(n != null) {
			n.setMessage(newMessage);
		}
		return new ResponseEntity<>("Success: Notification updated!", HttpStatus.OK);
	}
	
	private Notification getNotificationById(Long id) {
    	for (Notification n : notifications) {
    		if(n.getId().equals(id)) {
    			return n;
    		}
     	}
    	return null;
	}
}