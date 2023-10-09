import { getConfig } from './config/config';
import { getUserId, getWebAppToken } from './telegram';
import { getLogger } from './utils';

let webAppAuthToken: string;
let ws: WebSocket;
let onMessageHandler: (data: any) => void;
let onConnectedHandler: () => void;

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
            headers: {
                Authorization: getWebAppToken(),
            },
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

    ws.addEventListener('open', () => {
        onConnectedHandler();
    });
}

function onConnected(handler: () => void) {
    onConnectedHandler = handler;
}

function onMessage(handler: (data: any) => void) {
    onMessageHandler = handler;
}

function requestGameState() {
    ws.send(JSON.stringify({ type: 'HELLO', from: getUserId() }));
}

export {
    authenticateApp,
    openWebSocket,
    onConnected,
    onMessage,
    requestGameState,
};
