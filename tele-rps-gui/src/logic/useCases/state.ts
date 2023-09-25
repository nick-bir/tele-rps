import { State, createState } from '../domain/state';

let state: State;

function useState() {
    if (!state) {
        state = createState();
    }
    return { state };
}

export { useState };
