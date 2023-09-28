import { computed, ref } from 'vue';

type GameStatus = 'new' | 'started' | 'finished';
type Figures = 'rock' | 'paper' | 'scissors' | 'choosing';

const gameStatus = ref<GameStatus>('new');
const myChoise = ref<Figures>('choosing');
const opponentChoise = ref<Figures>('choosing');

function useApi() {
    const isNewGame = computed(() => gameStatus.value === 'new');
    const isGameStarted = computed(() => gameStatus.value === 'started');
    const isGameFinished = computed(() => gameStatus.value === 'finished');
    const isMyChoiseMade = computed(() => myChoise.value !== 'choosing');
    const isOpponentChoiseMade = computed(
        () => opponentChoise.value !== 'choosing'
    );
    const isBothChoiseMade = computed(
        () => isMyChoiseMade.value && isOpponentChoiseMade.value
    );
    const isRoundFinished = computed(
        () => isGameStarted.value && isBothChoiseMade.value
    );

    const startNewGame = () => {
        gameStatus.value = 'started';
        myChoise.value = 'choosing';
    };

    const setGameStatus = (status: GameStatus) => (gameStatus.value = status);

    const makeMyChoise = (figure: Figures) => {
        myChoise.value = figure;
    };
    const makeOpponentChoise = (figure: Figures) => {
        opponentChoise.value = figure;
    };

    return {
        startNewGame,
        setGameStatus,

        myChoise,
        opponentChoise,

        isNewGame,
        isGameStarted,
        isMyChoiseMade,
        isOpponentChoiseMade,
        isBothChoiseMade,
        isRoundFinished,
        isGameFinished,

        makeMyChoise,
        makeOpponentChoise,
    };
}

export { useApi };
export type { Figures };
