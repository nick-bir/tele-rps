let state: State;

function useState() {
    if (!state) {
        state = createState();
    }
    return { state };
}
