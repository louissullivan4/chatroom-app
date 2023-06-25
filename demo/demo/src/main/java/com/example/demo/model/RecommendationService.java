package com.example.demo.model;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {
    public RecommendationService() {}

    public List<Room> recommendRooms(Account account, RoomRepository roomRepository) {
        List<Room> listRecommendedRooms = new ArrayList<>();
        List<Room> listPopularRooms = new ArrayList<>();
        for(Room room : roomRepository.findAllByOrderByAccountsDesc()) {
            if (!listPopularRooms.contains(room) && listPopularRooms.size() < 5) {
                listPopularRooms.add(room);
            }
        }
        Location accountLocation = account.getLocation();
        int totalNumberOfRooms = roomRepository.findAll().size();
        for(int currentDistance = 50; listRecommendedRooms.size() < 5; currentDistance += 50) {
            if (listRecommendedRooms.size() == totalNumberOfRooms) {
                break;
            }
            for (Room room : roomRepository.findAll()) {
                Location roomLocation = room.getLocation();
                if (accountLocation.distanceTo(roomLocation) <= currentDistance && !listRecommendedRooms.contains(room) && listRecommendedRooms.size() < 5 && !listPopularRooms.contains(room)) {
                    listRecommendedRooms.add(room);
                }
            }
        }
        listRecommendedRooms.addAll(listPopularRooms);
        return listRecommendedRooms;
    }
}