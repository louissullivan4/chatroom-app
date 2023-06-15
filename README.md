# Chatroom App - Backend

## What is it ?

- Application for Phone
- User have accounts
- User can create "Rooms" with a "Topic" (title)
- Have a homepage which users can scroll through these "Rooms" - Based on location or popularity
- User can also search for these "Topic" which returns "Rooms" based on the search term
- Handle the actual "Room":
    - Topic
    - Host
    - Users
        - VideoUsers
        - MessageUsers
    - Messages Sent
    - Room Stats (UserCount, Length)
    - Room Permissions (AllowedVideoOn, AllowedSendMessages)

## Installation 
Clone this repository.

Run:  
~~~
./gradlww bootRun
~~~

## Test Backend with Curl (Windows Terminal)
Note that if running this server it will initiate with 2 Accounts and 1 Room

### Testing Accounts
1. Get all accounts
```
curl localhost:8080/accounts
```

2. Get account with id 2
```
curl localhost:8080/accounts/2
```

3. Get account with id 3, it will return a not found exception
```
curl localhost:8080/accounts/3
```

4. Create a new account
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/accounts -d "{\"email\":\"amy@gmail.com\", \"firstName\":\"Amy\", \"lastName\":\"Murphy\", \"username\":\"amurphy1\", \"country\":\"en_IE\", \"dob\":\"1980_1_1\"}"
```

5. Update account with id 2 (email will be updated)
```
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/accounts/2 -d "{\"email\":\"dylan@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"bdylan1\", \"country\":\"en_IE\", \"dob\":\"1960_1_1\"}"
```

6. Delete account with id 3
```
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/accounts/3
```

### Testing Rooms
1. Get all rooms
```
curl localhost:8080/rooms
```

2. Get room with id 1
```
curl localhost:8080/rooms/1
```

3. Get account with id 2, it will return a not found exception
```
curl localhost:8080/rooms/2
```






