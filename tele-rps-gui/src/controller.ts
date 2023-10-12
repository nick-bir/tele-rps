import {
    authenticateApp,
    onClosed,
    onConnected,
    onMessage,
    openWebSocket,
    requestGameState,
    SocketMessage,
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

    onMessage((data: SocketMessage) => {
        console.log('---onMessage', data);
        if (!data.gameResult) {
            state.setGameStatus('STARTED');
        } else {
            state.setGameStatus('FINISHED');
            state.setGameResult(data.gameResult);
        }
        state.setMyWeapon(data.yourGesture);
        state.setEnemyWeapon(data.opponentGesture);
    });

    onClosed(() => {
        console.log('---onClosed');
        state.setGameStatus('PENDING');
    });
}

export { connect };
