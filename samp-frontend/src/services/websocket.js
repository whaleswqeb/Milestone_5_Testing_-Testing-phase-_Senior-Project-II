import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient = null;
const notificationListeners = new Set();
const queryUpdateListeners = new Set();
const processedEventKeys = new Set();

const isDuplicateEvent = (data) => {
  if (!data || !data.queryId) return false;
  const key = `${data.queryId}_${data.eventType || ""}_${data.latestMessage || ""}_${data.timestamp || ""}`;
  if (processedEventKeys.has(key)) {
    return true;
  }
  processedEventKeys.add(key);
  setTimeout(() => processedEventKeys.delete(key), 4000);
  return false;
};

export const addNotificationListener = (listener) => {
  if (listener) notificationListeners.add(listener);
  return () => notificationListeners.delete(listener);
};

export const addQueryUpdateListener = (listener) => {
  if (listener) queryUpdateListeners.add(listener);
  return () => queryUpdateListeners.delete(listener);
};

export const initWebSocket = (token) => {
  if (stompClient && stompClient.active) {
    return stompClient;
  }

  const socketUrl = import.meta.env.VITE_WS_URL || "http://localhost:8080/api/ws";

  stompClient = new Client({
    webSocketFactory: () => new SockJS(socketUrl),
    connectHeaders: {
      Authorization: `Bearer ${token}`,
    },
    debug: (str) => {
      // console.log("[STOMP Debug]:", str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  stompClient.onConnect = (frame) => {
    console.log("Connected to STOMP WebSocket broker");

    const dispatchEvent = (data) => {
      if (!data || isDuplicateEvent(data)) return;
      queryUpdateListeners.forEach((fn) => fn(data));
      notificationListeners.forEach((fn) => fn(data));
    };

    stompClient.subscribe("/topic/queries", (message) => {
      try {
        dispatchEvent(JSON.parse(message.body));
      } catch (e) {
        console.error("Error parsing WebSocket query message", e);
      }
    });

    stompClient.subscribe("/topic/notifications", (message) => {
      try {
        dispatchEvent(JSON.parse(message.body));
      } catch (e) {
        console.error("Error parsing WebSocket notification message", e);
      }
    });

    stompClient.subscribe("/user/queue/notifications", (message) => {
      try {
        dispatchEvent(JSON.parse(message.body));
      } catch (e) {
        console.error("Error parsing WebSocket user notification message", e);
      }
    });
  };

  stompClient.onStompError = (frame) => {
    console.error("Broker reported error: " + frame.headers["message"]);
  };

  stompClient.activate();
  return stompClient;
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
};
