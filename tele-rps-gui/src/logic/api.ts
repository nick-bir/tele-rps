import { computed, ref } from 'vue';

type GameStatus = 'new' | 'started' | 'finished';
type Figures = 'rock' | 'paper' | 'scissors' | 'choosing';

const gameStatus = ref<GameStatus>('new');
const enemyName = ref<string>('@ppo_nent');
const myWeapon = ref<Figures>('choosing');
const enemyWeapon = ref<Figures>('choosing');

function useApi() {
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
    };
}

export { useApi };
export type { Figures };
