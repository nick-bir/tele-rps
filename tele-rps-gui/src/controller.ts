import { authenticateApp } from './backend/backend';
import { useState } from './state/state';

async function connect() {
    const state = useState();

    await authenticateApp();
}

export { connect };
