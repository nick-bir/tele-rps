import { getConfig } from './config/config';
import { getUserId, getWebAppInitData } from './telegram';
import { getLogger } from './utils';
import { Gesture, GameStatus, GameResult } from './state.ts';

let webAppAuthToken: string;
let ws: WebSocket;
let onMessageHandler: (data: any) => void;
let onConnectedHandler: () => void;
let onClosedHandler: () => void;

async function authenticateApp() {
    const log = getLogger('backend.authenticateApp');

    if (webAppAuthToken) {
        log('already authenticated');
        return;
    }

    log('getting token');

    try {
        const result = await fetch(getConfig().authUrl, {
            method: 'POST',
            body: getWebAppInitData(),
        });

        if (!result.ok) {
            throw new Error('wrong response');
        }

        log('token recieved');

        webAppAuthToken = await result.text();
    } catch (e) {
        log.error(e);
        throw e;
    }
}

function openWebSocket() {
    ws = new WebSocket(`${getConfig().wsUrl}?${webAppAuthToken}`);
    ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        onMessageHandler(data);
    };
    ws.onopen = () => {
        onConnectedHandler();
    };
    ws.onclose = () => {
        onClosedHandler();
    };
}

function onConnected(handler: () => void) {
    onConnectedHandler = handler;
}

function onMessage(handler: (data: any) => void) {
    onMessageHandler = handler;
}

function onClosed(handler: () => void) {
    onClosedHandler = handler;
}

function requestGameState() {
    ws.send(JSON.stringify({ type: 'HELLO', from: getUserId() }));
}

function makeMove(figure: Gesture) {
    ws.send(
        JSON.stringify({
            type: 'MOVE',
            from: getUserId(),
            // gesture: mapFigureToGesture(figure),
            gesture: figure,
        })
    );
}

export type SocketMessage = {
    gameStatus: GameStatus;
    yourGesture: Gesture;
    opponentGesture: Gesture;
    gameResult: GameResult;
};

export {
    authenticateApp,
    openWebSocket,
    onConnected,
    onMessage,
    onClosed,
    requestGameState,
    makeMove,
};
