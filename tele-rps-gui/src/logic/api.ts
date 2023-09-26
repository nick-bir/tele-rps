import { computed, ref } from 'vue';

type GameStatus = 'new' | 'started' | 'finished';
type RoundStatus = 'new' | 'choise_made' | 'finished';
type Figures = 'rock' | 'paper' | 'scissors' | 'choosing';

const gameStatus = ref<GameStatus>('new');
const roundStatus = ref<RoundStatus>('new');
const userChoise = ref<Figures>('rock');

function useApi() {
    const isNewGame = computed(() => gameStatus.value === 'new');
    const isGameStarted = computed(() => gameStatus.value === 'started');
    const isGameFinished = computed(() => gameStatus.value === 'finished');
    const isNewRound = computed(() => roundStatus.value === 'new');
    const isChoiseMade = computed(() => userChoise.value !== 'choosing');
    const isRoundFinished = computed(() => roundStatus.value === 'finished');

    const startNewGame = () => {
        gameStatus.value = 'new';
        roundStatus.value = 'new';
        userChoise.value = 'choosing';
    };

    const startNewRound = () => (roundStatus.value = 'new');
    const finishRound = () => (roundStatus.value = 'finished');
    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);
    const setRoundStatus = (status: RoundStatus) =>
        (roundStatus.value = status);

    const makeChoise = (figure: Figures) => {
        console.log('--- makeChoise', figure);

        userChoise.value = figure;
        roundStatus.value = 'choise_made';
    };

    return {
        startNewGame,
        setGameStatus,
        setRoundStatus,

        isNewGame,
        isGameStarted,
        isNewRound,
        isChoiseMade,
        isRoundFinished,
        isGameFinished,
        startNewRound,
        finishRound,

        makeChoise,
    };
}

export { useApi };
