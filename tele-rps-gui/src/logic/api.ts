import { computed, ref } from 'vue';

type GameStatus = 'new' | 'started' | 'finished';

const gameStatus = ref<GameStatus>('new');
const isNewRound = ref(true);

function useApi() {
    const isNewGame = computed(() => gameStatus.value === 'new');
    const isGameStarted = computed(() => gameStatus.value === 'started');
    const isGameFinished = computed(() => gameStatus.value === 'finished');
    const isRoundFinished = computed(() => !isNewRound.value);

    const startNewRound = () => (isNewRound.value = true);
    const finishRound = () => (isNewRound.value = false);
    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);

    return {
        setGameStatus,
        isNewGame,
        isGameStarted,
        isNewRound,
        isRoundFinished,
        isGameFinished,
        startNewRound,
        finishRound,
    };
}

export { useApi };
