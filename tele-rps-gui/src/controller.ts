import {
    authenticateApp,
    mapGestureToFigure,
    onClosed,
    onConnected,
    onMessage,
    openWebSocket,
    requestGameState,
    SocketMessage,
} from './backend';
import { Figures, useState } from './state';

async function connect() {
    const state = useState();

    await authenticateApp();

    onConnected(() => {
        console.log('---onConnected');
        requestGameState();
    });

    onMessage((data: SocketMessage) => {
        console.log('---onMessage', data);
        if (!data.gameResult) {
            state.setGameStatus('started');
        } else {
            state.setGameStatus('finished');
            state.setGameResult(data.gameResult);
        }
        state.setEnemyWeapon(mapGestureToFigure(data.opponentGesture));
    });

    onClosed(() => {
        state.setGameStatus('new');
    });
}

export { connect };
