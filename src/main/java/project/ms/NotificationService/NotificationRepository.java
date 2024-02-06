package project.ms.NotificationService;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ms.NotificationService.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
