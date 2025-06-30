package com.example.datvexe.data.remote.service;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.datvexe.data.mapper.ChatMessageMapper;
import com.example.datvexe.data.remote.dto.ChatMessageDto;
import com.example.datvexe.domain.model.ChatMessage;
import com.example.datvexe.domain.repository.ChatRepository;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Setter;

@Singleton
public class ChatSocketService {
    private static final String TAG = "ChatSocketService";
    private static final String BASE_URL = "http://10.0.2.2:9999"; // Localhost cho emulator Android Studio
    // Nếu dùng device thật, thay bằng IP máy tính: "http://192.168.1.xxx:9999"
    private final Gson gson;
    private final ChatMessageMapper mapper;
    private final Handler mainHandler;
    private Socket socket;
    @Setter
    private SocketConnectedListener socketConnectedListener;

    @Setter
    private ChatRepository.MessageListener messageListener;
    @Setter
    private ChatRepository.ConversationListener conversationListener;

    @Inject
    public ChatSocketService(ChatMessageMapper mapper) {
        this.mapper = mapper;
        this.gson = new Gson();
        this.mainHandler = new Handler(Looper.getMainLooper());
        initSocket();
    }

    private void initSocket() {
        try {
            IO.Options options = IO.Options.builder()
                    .setForceNew(false)
                    .setReconnection(true)
                    .build();

            socket = IO.socket(BASE_URL, options);
            setupSocketListeners();
        } catch (URISyntaxException e) {
            Log.e(TAG, "Error initializing socket", e);
        }
    }

    private void setupSocketListeners() {
        socket.on(Socket.EVENT_CONNECT, args -> {
            Log.d(TAG, "Socket connected");
            if (socketConnectedListener != null) socketConnectedListener.onSocketConnected();
        });

        socket.on(Socket.EVENT_DISCONNECT, args -> {
            Log.d(TAG, "Socket disconnected");
        });

        socket.on("receive_message", args -> {
            try {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "Received message: " + data.toString());

                ChatMessageDto messageDto = gson.fromJson(data.toString(), ChatMessageDto.class);
                ChatMessage message = mapper.toDomain(messageDto);

                if (message != null && messageListener != null) {
                    mainHandler.post(() -> messageListener.onNewMessage(message));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error parsing received message", e);
                if (messageListener != null) {
                    mainHandler.post(() -> messageListener.onError("Lỗi khi nhận tin nhắn: " + e.getMessage()));
                }
            }
        });

        socket.on("conversation_history", args -> {
            try {
                if (args.length > 0 && args[0] instanceof org.json.JSONArray) {
                    org.json.JSONArray jsonArray = (org.json.JSONArray) args[0];
                    List<ChatMessage> messages = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject messageJson = jsonArray.getJSONObject(i);
                        ChatMessageDto messageDto = gson.fromJson(messageJson.toString(), ChatMessageDto.class);
                        ChatMessage message = mapper.toDomain(messageDto);
                        if (message != null) {
                            messages.add(message);
                        }
                    }

                    if (conversationListener != null) {
                        mainHandler.post(() -> conversationListener.onConversationLoaded(messages));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error parsing conversation history", e);
                if (conversationListener != null) {
                    mainHandler.post(() -> conversationListener.onError("Lỗi khi tải lịch sử chat: " + e.getMessage()));
                }
            }
        });
    }

    public Socket connect() {
        if (socket != null && !socket.connected()) {
            return socket.connect();
        }
        return null;
    }

    public void disconnect() {
        if (isConnected()) {
            socket.disconnect();
        }
    }

    public void joinChat(String userId) {
        Log.d(TAG, "Joining chat for user: " + userId);
        if (isConnected()) {
            socket.emit("join_chat", userId);
            Log.d(TAG, "Joined chat for user: " + userId);
        }
    }

    public void sendMessage(String userId, String message, ChatMessage.Sender sender) {
        if (isConnected()) {
            JSONObject messageData = createMessageData(userId, message, sender);
            if (messageData == null) return;
            socket.emit("send_message", messageData);
            Log.d(TAG, "Sent message: " + message);
        }
    }

    private JSONObject createMessageData(String userId, String message, ChatMessage.Sender sender) {
        JSONObject messageData = new JSONObject();
        try {
            messageData.put("userId", userId);
            messageData.put("message", message);
            JSONObject senderData = new JSONObject();
            senderData.put("id", sender.getId());
            senderData.put("name", sender.getName());
            senderData.put("role", sender.getRole());
            messageData.put("sender", senderData);
            return messageData;
        } catch (JSONException e) {
            Log.e(TAG, "Error creating message data", e);
            return null;
        }
    }

    public void getConversationHistory(String userId) {
        Log.d(TAG, "isConnected: " + isConnected());
        if (isConnected()) {
            try {
                JSONObject data = new JSONObject();
                data.put("userId", userId);
                socket.emit("get_conversation", data);
                Log.d(TAG, "Requested conversation history for user: " + userId);
            } catch (JSONException e) {
                Log.e(TAG, "Error requesting conversation history", e);
            }
        }
    }

    public boolean isConnected() {
        return socket != null && socket.connected();
    }

    public interface SocketConnectedListener {
        void onSocketConnected();
    }
}