type Game = {
    status: string;
};

function createGame(): Game {
    return {
        status: 'new',
    };
}

export { createGame };
export type { Game };
