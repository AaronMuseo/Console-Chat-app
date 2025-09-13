# Java Socket Chat Application

A simple terminal-based **multi-client chat application** built in Java using sockets.  
Supports multiple users, usernames, colored messages.

---

## 🚀 Features
- Multi-client chat with broadcast messaging
- Unique usernames for each client
- Colored messages in the terminal:
  - 🟦 Blue → Your own messages
  - 🟩 Green → Usernames
  - 🟨 Yellow → System messages (join/leave)
- Works on localhost or LAN (using IP address of the server machine)

---

## 📂 Project Structure
.
├── Server.java # Chat server handling clients and broadcasting messages
├── ChatClient.java # Client app to connect to server and chat
└── README.md

---

## ⚙️ Requirements
- Java 8 or higher
- Terminal/Command prompt with ANSI color support (most modern terminals support this)

---

#*Open the src folder in terminal*

1. Start the server
```java Server.java``` or ```java Server```


Output:

```Server is running...```

3. Run the client(s)

Open another terminal still in the src folder (or multiple terminals for multiple users):

```java ChatClient.java``` or ```java ChatClient```


You’ll be prompted:

```Enter username:```


Type a username (e.g., Alice) and start chatting.


Code has comments in there for learning and what the blocks do 
