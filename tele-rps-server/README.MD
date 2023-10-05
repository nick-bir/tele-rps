<h1>Backend for Rock-Paper-Scissors TG bot webapp</h1>
<h3>How to call it from the Frontend</h3>
When the webapp is launched in valid TG client,
client lib provides hash for authenticity.

Send this hash to /auth endpoint and get 1-time auth token for websockets

Open WebSocket at /ws?{SOCKET_TOKEN}

Sample request:
```javascript
const tokenReq = await fetch('http://0.0.0.0:8080/auth', {
    method: 'POST',
    headers: {
        'Authorization': Telegram.WebApp.initDataUnsafe.hash
    }

});
const token = await tokenReq.text()
const ws = new WebSocket(`ws://localhost:8080/ws?${token}`)
```
<h3>Local Testing</h3>
1. Put `application.local.properties` next to `application.properties`
2. Create TG bot in bot_father, save the tg-bot token
3. Put following config:
```properties
tg_token=TG_BOT:TOKEN
# any text to use instead of Telegram.WebApp.initDataUnsafe.hash
tg_hash=test
socket_token_timeout_sec=60
polling_mode=true
```