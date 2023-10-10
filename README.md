# Tele-RPS (rock-paper-scissors)

## App backend server

### To run local server in docker:

```bash
make docker-server
```

## Web app

1. Install node
2. Install yarn (`npm i -g yarn`)

### To run local dev server

```bash
make dev-gui
```

### To run tests

```bash
make test-gui
```

## How bot works
1. User sends `/start`. Common for any TG bot.
2. Bot greets user with info message about how to use the bot.
3. User sends `/challenge` command to a bot
4. Bot creates PENDING game for this user.
5. Bot sends button with link to this game to a user. Button is the link in following format: `t.me/botName?start=GAME_ID`
6. User forwards message with the button to the opponent
7. Opponent clicks the button and clicks 'start' button in the bot
8. Both players get prompt to run the app from bot menu
9. Players play the game and get prompt to `/challenge` someone again
