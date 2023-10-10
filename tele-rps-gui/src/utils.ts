import { useState } from './state';
import { getWebAppInitData } from './telegram';

function getLogger(prefix: string) {
    const log = (...args: any[]) => {
        console.log('---', prefix, ...args);
    };

    log.error = (...args: any[]) => {
        console.error('---', prefix, ...args);
        useState().showError(args.join(' '));
    };

    return log;
}

function isDevMode() {
    return getWebAppInitData() === 'test';
}

export { getLogger, isDevMode };
