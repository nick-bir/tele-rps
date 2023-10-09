import {
    authenticateApp,
    onConnected,
    onMessage,
    openWebSocket,
    requestGameState,
} from './backend';
import { useState } from './state';

async function connect() {
    const state = useState();

    await authenticateApp();
    await openWebSocket();

    onConnected(() => {
        console.log('---onConnected');
        requestGameState();
    });

    onMessage((data) => {
        console.log('---onMessage', data);

        // TODO:
        // setEnemyName(data.enemyName);
    });
}

export { connect };
