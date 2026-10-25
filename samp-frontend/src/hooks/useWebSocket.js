import { useEffect } from "react";
import { useSelector } from "react-redux";
import toast from "react-hot-toast";
import {
  initWebSocket,
  addNotificationListener,
  addQueryUpdateListener,
} from "../services/websocket";

export const useWebSocket = (onQueryUpdate) => {
  const { token, user } = useSelector((state) => state.auth);

  useEffect(() => {
    if (!token || !user) return;

    // Initialize shared STOMP WebSocket connection
    initWebSocket(token);

    const handleNotification = (notif) => {
      // Do not display toast notification to the user who sent the message
      if (user?.id && (notif?.senderId === user.id || notif?.senderEmail === user.email)) {
        return;
      }

      const toastId = `toast_${notif.queryId}_${notif.timestamp || notif.latestMessage}`;
      toast.success(
        `🔔 ${notif.subject || "Query Update"}: ${notif.latestMessage || "New message received!"}`,
        {
          id: toastId,
          duration: 3500,
          position: "top-right",
        }
      );
    };

    // Register global toast listener
    const removeNotifListener = addNotificationListener(handleNotification);

    // Register component-specific query update listener if provided
    let removeQueryListener;
    if (onQueryUpdate) {
      removeQueryListener = addQueryUpdateListener(onQueryUpdate);
    }

    return () => {
      removeNotifListener();
      if (removeQueryListener) removeQueryListener();
    };
  }, [token, user, onQueryUpdate]);
};
