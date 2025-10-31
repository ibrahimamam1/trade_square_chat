package iut.cse.trade_square_chat.services;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineUserTracker {

    private final Set<Long> onlineUsers = ConcurrentHashMap.newKeySet();

    public void addUser(Long userId) {
        onlineUsers.add(userId);
        System.out.println("User " + userId + " is now online. Total online: " + onlineUsers.size());
    }

    public void removeUser(Long userId) {
        onlineUsers.remove(userId);
        System.out.println("User " + userId + " is now offline. Total online: " + onlineUsers.size());
    }

    public boolean isUserOnline(Long userId) {
        return onlineUsers.contains(userId);
    }

    public Set<Long> getOnlineUsers() {
        return Set.copyOf(onlineUsers);
    }


    private final ConcurrentHashMap<Long, LocalDateTime> lastSeen = new ConcurrentHashMap<>();

    public void updateLastSeen(Long userId) {
        lastSeen.put(userId, LocalDateTime.now());
    }

    public LocalDateTime getLastSeen(Long userId) {
        return lastSeen.get(userId);
    }

}