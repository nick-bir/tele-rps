# Backend for Rock-Paper-Scissors TG bot webapp

### How to call it from the Frontend

When the webapp is launched in valid TG client,
client lib provides hash for authenticity.

Send this hash to `/auth` endpoint and get 1-time auth token for websockets

Open WebSocket at `/ws?{SOCKET_TOKEN}`

Sample request:

```javascript
const tokenReq = await fetch('http://0.0.0.0:8080/auth', {
    method: 'POST',
    headers: {
        Authorization: Telegram.WebApp.initDataUnsafe.hash,
    },
});
const token = await tokenReq.text();
const ws = new WebSocket(`ws://localhost:8080/ws?${token}`);
```

### Local Testing

1. Put `application.local.properties` next to `src/main/resources/application.properties`
2. Create TG bot in bot_father, save the tg-bot token
3. Put following config:

```properties
tg_token=TG_BOT:TOKEN
# any text to use instead of Telegram.WebApp.initDataUnsafe.hash
tg_hash=test
socket_token_timeout_sec=60
polling_mode=true
```

### Token Protocol

1. client sends `{type: "HELLO", from: tg_user_id}`
2. server sends current game updates in format

```
{
    gameStatus: "PENDING" | "IN_PROGRESS" | "COMPLETED",
    yourGesture: "ROCK" | "PAPER" | "SCISSORS" | null,
    opponentGesture: "ROCK" | "PAPER" | "SCISSORS" | null,
    gameResult: "VICTORY" | "DEFEAT" | "DRAW" | null
}
```

3. client sends

```
{
    type: "MOVE",
    from: tg_user_id,
    gesture: "ROCK" | "PAPER" | "SCISSORS"
}
```

4. server sends current game updates in format same as above

#### Error codes:
In case of unexpected message from UI, server closes WS with code 4000 and one of these messages: 

1. NO_GAME - there is no active or completed game for current TG user. UI must prompt user to /challenge someone via TG bot
2. NO_TYPE - required field `type` of incoming message is missing. Should be 'HELLO' or 'MOVE'
3. NO_SENDER - required field `from` is missing. Should be TG user id
5. NO_GESTURE - if `type: "MOVE"` was sent, but no gesture was provided
