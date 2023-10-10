import { getConfig } from './config/config';
import { getUserId, getWebAppInitData } from './telegram';
import { getLogger } from './utils';
import { Figures } from './state.ts';

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

function makeMove(figure: Figures) {
    ws.send(
        JSON.stringify({
            type: 'MOVE',
            from: getUserId(),
            gesture: mapFigureToGesture(figure),
        })
    );
}

export type Gesture = 'ROCK' | 'PAPER' | 'SCISSORS' | null;
export type SocketMessage = {
    gameStatus: 'PENDING';
    yourGesture: Gesture;
    opponentGesture: Gesture;
    gameResult: 'VICTORY' | 'DEFEAT' | 'DRAW' | null;
};

function mapGestureToFigure(gesture: Gesture): Figures {
    switch (gesture) {
        case 'ROCK':
            return 'rock';
        case 'PAPER':
            return 'paper';
        case 'SCISSORS':
            return 'scissors';
        default:
            return 'choosing';
    }
}

function mapFigureToGesture(figure: Figures): Gesture | undefined {
    switch (figure) {
        case 'rock':
            return 'ROCK';
        case 'paper':
            return 'PAPER';
        case 'scissors':
            return 'SCISSORS';
        default:
            return undefined;
    }
}
export {
    authenticateApp,
    openWebSocket,
    onConnected,
    onMessage,
    onClosed,
    requestGameState,
    makeMove,
    mapGestureToFigure,
    mapFigureToGesture,
};
