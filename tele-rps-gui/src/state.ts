import { computed, ref } from 'vue';
import { makeMove } from './backend.ts';

type GameStatus = 'PENDING' | 'STARTED' | 'FINISHED';
type Gesture = 'PENDING' | 'ROCK' | 'PAPER' | 'SCISSORS';
type GameResult = 'PENDING' | 'VICTORY' | 'DEFEAT' | 'DRAW';

const gameStatus = ref<GameStatus>('PENDING');
const gameResult = ref<GameResult>();
const enemyName = ref<string>('@ppo_nent');
const myWeapon = ref<Gesture>('PENDING');
const enemyWeapon = ref<Gesture>('PENDING');
const errorMessages = ref<string[]>([]);

function useState() {
    const noGameCreated = computed(() => gameStatus.value === 'PENDING');
    const isGameStarted = computed(() => gameStatus.value === 'STARTED');
    const isGameFinished = computed(() => gameStatus.value === 'FINISHED');
    const isMyWeaponChosen = computed(() => myWeapon.value !== 'PENDING');
    const iWon = computed(() => gameResult.value === 'VICTORY');
    const iLost = computed(() => gameResult.value === 'DEFEAT');

    const startNewGame = () => {
        gameStatus.value = 'STARTED';
        myWeapon.value = 'PENDING';
        enemyWeapon.value = 'PENDING';
    };

    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);
    const setGameResult = (result: GameResult) => (gameResult.value = result);

    const setMyWeapon = (figure: Gesture) => {
        myWeapon.value = figure;
        makeMove(figure);
    };

    const setEnemyWeapon = (figure: Gesture) => {
        enemyWeapon.value = figure;
    };

    const showError = (message: string) => {
        errorMessages.value.push(message);
    };

    return {
        startNewGame,
        setGameStatus,
        setGameResult,

        enemyName,
        myWeapon,
        enemyWeapon,

        noGameCreated,
        isGameStarted,
        isMyWeaponChosen,
        isGameFinished,
        iWon,
        iLost,

        setMyWeapon,
        setEnemyWeapon,

        errorMessages,
        showError,
    };
}

export { useState };
export type { Gesture, GameStatus, GameResult };
