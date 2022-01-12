package gitflow.ui;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class NotifyUtil
{
    private static final NotificationGroup TOOLWINDOW_NOTIFICATION = NotificationGroupManager.getInstance()
            .getNotificationGroup("Gitflow Errors WINDOW");
    private static final NotificationGroup STICKY_NOTIFICATION = NotificationGroupManager.getInstance()
            .getNotificationGroup("Gitflow Errors STICKY");
    private static final NotificationGroup BALLOON_NOTIFICATION = NotificationGroupManager.getInstance()
        .getNotificationGroup("Gitflow Notifications");

    public static void notifySuccess(Project project, String title, String message) {
        notify(NotificationType.INFORMATION, BALLOON_NOTIFICATION, project, title, message);
    }

	public static void notifyInfo(Project project, String title, String message) {
		notify(NotificationType.INFORMATION, TOOLWINDOW_NOTIFICATION, project, title, message);
	}

    public static void notifyError(Project project, String title, String message) {
        notify(NotificationType.ERROR, TOOLWINDOW_NOTIFICATION, project, title, message);
    }

    public static void notifyError(Project project, String title, Exception exception) {
        notify(NotificationType.ERROR, STICKY_NOTIFICATION, project, title, exception.getMessage());
    }

    private static void notify(NotificationType type, NotificationGroup group, Project project, String title, String message) {
        try {
            group.createNotification(title, message, type).notify(project);
        } catch (NoSuchMethodError e) {
            //兼容旧版本
            group.createNotification(title, message, type, null).notify(project);
        }
    }
}
