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
