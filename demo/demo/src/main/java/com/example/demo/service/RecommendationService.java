package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Location;
import com.example.demo.model.Room;
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
        // randomise the order of the list
        for (int i = 0; i < listRecommendedRooms.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * listRecommendedRooms.size());
            Room temp = listRecommendedRooms.get(randomIndexToSwap);
            listRecommendedRooms.set(randomIndexToSwap, listRecommendedRooms.get(i));
            listRecommendedRooms.set(i, temp);
        }
        return listRecommendedRooms;
    }
}