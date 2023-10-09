import { authenticateApp } from './backend';
import { useState } from './state';

async function connect() {
    const state = useState();

    await authenticateApp();
}

export { connect };
