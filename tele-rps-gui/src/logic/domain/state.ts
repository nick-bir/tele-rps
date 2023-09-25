import { Game, createGame } from './game';
import { Player } from './player';

type State = {
    game: Game;
    players: Player[];
};

function createState(): State {
    return {
        game: createGame(),
        players: [],
    };
}

export { createState };
export type { State };
