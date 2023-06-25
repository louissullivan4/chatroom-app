package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.Location;
import com.example.demo.model.Room;

import java.time.LocalDate;
import java.util.Random;

public class GenerateRandomData {
    private static final String[] FIRST_NAMES = {"James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Nancy", "Daniel", "Lisa", "Matthew", "Betty", "Anthony", "Margaret", "Donald", "Sandra", "Mark", "Ashley", "Paul", "Kimberly", "Steven", "Emily", "Andrew", "Donna", "Kenneth", "Michelle", "George", "Dorothy", "Joshua", "Carol", "Kevin", "Amanda", "Brian", "Melissa", "Edward", "Deborah", "Ronald", "Stephanie", "Timothy", "Rebecca", "Jason", "Laura", "Jeffrey", "Helen", "Ryan", "Sharon", "Jacob", "Cynthia", "Gary", "Kathleen", "Nicholas", "Amy", "Eric", "Shirley", "Stephen", "Angela", "Jonathan", "Anna", "Larry", "Ruth", "Justin", "Brenda", "Scott", "Pamela", "Brandon", "Nicole", "Frank", "Katherine", "Benjamin", "Samantha", "Gregory", "Christine", "Raymond", "Catherine", "Samuel", "Virginia", "Patrick", "Debra", "Alexander", "Rachel", "Jack", "Janet", "Dennis", "Emma", "Jerry", "Carolyn", "Tyler", "Maria", "Aaron", "Heather", "Henry", "Diane", "Jose", "Julie", "Douglas", "Joyce", "Peter", "Evelyn", "Adam", "Joan", "Nathan", "Victoria", "Zachary", "Kelly", "Walter", "Christina", "Kyle", "Lauren", "Harold", "Frances", "Carl", "Martha", "Jeremy", "Judith", "Gerald", "Cheryl", "Keith", "Megan", "Roger", "Andrea", "Arthur", "Olivia", "Terry", "Ann", "Lawrence", "Jean", "Sean"};

    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green"};

    private static final String[] ROOM_NAMES = {"Sports", "Politics", "Music", "Movies", "TV", "Books", "Food", "Travel", "Fashion", "Art", "Science", "Technology", "Health", "Fitness", "Pets"};

    public static Account generateRandomAccount() {
        Random random = new Random();
        Location location = generateRandomLocation();

        String email = generateRandomEmail();
        String firstName = getRandomElement(FIRST_NAMES);
        String lastName = getRandomElement(LAST_NAMES);
        String username = generateRandomUsername(firstName, lastName);
        LocalDate birthDate = generateRandomBirthDate();

        return new Account(email, firstName, lastName, username, birthDate, location);
    }

    public static Room generateRandomRoom() {
        Random random = new Random();
        Location location = generateRandomLocation();

        String roomName = getRandomElement(ROOM_NAMES);
        String roomNumber = String.valueOf(random.nextInt(100) + 1);

        return new Room(roomName, roomNumber, location);
    }

    private static Location generateRandomLocation() {
        Random random = new Random();
        double latitude = random.nextDouble() * (90 - (-90)) + (-90);
        double longitude = random.nextDouble() * (180 - (-180)) + (-180);
        return new Location(latitude, longitude);
    }

    private static String generateRandomEmail() {
        String[] domains = {"gmail.com", "outlook.com"};
        Random random = new Random();
        String username = generateRandomString(6);
        String domain = getRandomElement(domains);
        return username + "@" + domain;
    }

    private static String generateRandomUsername(String firstName, String lastName) {
        String username = firstName.toLowerCase() + lastName.toLowerCase();
        return username + (new Random().nextInt(100) + 1);
    }

    private static LocalDate generateRandomBirthDate() {
        Random random = new Random();
        // Random year between 1950 and 2005
        int year = random.nextInt(2005 - 1950 + 1) + 1950;
        int month = random.nextInt(12) + 1; // Random month between 1 and 12
        int day = random.nextInt(28) + 1; // Random day between 1 and 28
        return LocalDate.of(year, month, day);
    }

    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static <T> T getRandomElement(T[] array) {
        Random random = new Random();
        int index = random.nextInt(array.length);
        return array[index];
    }
}
