import { computed, ref } from 'vue';

type GameStatus = 'new' | 'started' | 'finished';
type Figures = 'rock' | 'paper' | 'scissors' | 'choosing';

const gameStatus = ref<GameStatus>('new');
const enemyName = ref<string>('@ppo_nent');
const myWeapon = ref<Figures>('choosing');
const enemyWeapon = ref<Figures>('choosing');
const errorMessages = ref<string[]>([]);

function useState() {
    const noGameCreated = computed(() => gameStatus.value === 'new');
    const isGameStarted = computed(() => gameStatus.value === 'started');
    const isGameFinished = computed(() => gameStatus.value === 'finished');
    const isMyWeaponChoosen = computed(() => myWeapon.value !== 'choosing');

    const startNewGame = () => {
        gameStatus.value = 'started';
        myWeapon.value = 'choosing';
        enemyWeapon.value = 'choosing';
    };

    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);

    const setMyWeapon = (figure: Figures) => {
        myWeapon.value = figure;
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

        enemyName,
        myWeapon,
        enemyWeapon,

        noGameCreated,
        isGameStarted,
        isMyWeaponChoosen,
        isGameFinished,

        setMyWeapon,
        setEnemyWeapon,

        errorMessages,
        showError,
    };
}

export { useState };
export type { Figures };
