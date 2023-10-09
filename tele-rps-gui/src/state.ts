import { computed, ref } from 'vue';
import {getUserId} from "./telegram.ts";
import {mapFigureToGesture} from "./backend.ts";

type GameStatus = 'new' | 'started' | 'finished';
type GameResult = 'DRAW' | 'VICTORY' | 'DEFEAT';
type Figures = 'rock' | 'paper' | 'scissors' | 'choosing';

const gameStatus = ref<GameStatus>('new');
const gameResult = ref<GameResult>();
const enemyName = ref<string>('@ppo_nent');
const myWeapon = ref<Figures>('choosing');
const enemyWeapon = ref<Figures>('choosing');
const errorMessages = ref<string[]>([]);
const ws = ref<WebSocket>()

function useState() {
    const noGameCreated = computed(() => gameStatus.value === 'new');
    const isGameStarted = computed(() => gameStatus.value === 'started');
    const isGameFinished = computed(() => gameStatus.value === 'finished');
    const isMyWeaponChosen = computed(() => myWeapon.value !== 'choosing');

    const startNewGame = () => {
        gameStatus.value = 'started';
        myWeapon.value = 'choosing';
        enemyWeapon.value = 'choosing';
    };

    const setWs = (socket: WebSocket) => (ws.value = socket)

    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);
    const setGameResult = (result: GameResult) => (gameResult.value = result);

    const setMyWeapon = (figure: Figures) => {
        myWeapon.value = figure;
        ws.value!.send(JSON.stringify({ type: 'MOVE', from: getUserId(), gesture: mapFigureToGesture(figure) }))
    };

    const setEnemyWeapon = (figure: Figures) => {
        enemyWeapon.value = figure;
    };

    const showError = (message: string) => {
        errorMessages.value.push(message);
    };

    return {
        startNewGame,
        setGameStatus,
        setGameResult,
        setWs,

        enemyName,
        myWeapon,
        enemyWeapon,

        noGameCreated,
        isGameStarted,
        isMyWeaponChosen,
        isGameFinished,
        gameResult,

        setMyWeapon,
        setEnemyWeapon,

        errorMessages,
        showError,
    };
}

export { useState };
export type { Figures };
