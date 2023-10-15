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
        state.loadGameState({
            status: data.gameStatus,
            myGesture: data.yourGesture,
            enemyGesture: data.opponentGesture,
            result: data.gameResult,
        })
    });

    onClosed(() => {
        console.log('---onClosed');
        state.setGameStatus('PENDING');
    });
}

export { connect };
