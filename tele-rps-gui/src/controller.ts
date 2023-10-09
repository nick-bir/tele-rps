import {
    authenticateApp, mapGestureToFigure,
    onConnected,
    onMessage,
    openWebSocket,
    requestGameState, SocketMessage,
} from './backend';
import {Figures, useState} from './state';

async function connect() {
    const state = useState();


    await authenticateApp();
    const ws = await openWebSocket();
    state.setWs(ws);

    onConnected(() => {
        console.log('---onConnected');
        requestGameState();
    });

    onMessage((data: SocketMessage) => {
        console.log('---onMessage', data);
        if (!data.gameResult) {
            state.setGameStatus('started')
        } else {
            state.setGameStatus('finished')
            state.setGameResult(data.gameResult)
        }
        state.setEnemyWeapon(mapGestureToFigure(data.opponentGesture));
    });
}

export { connect };
