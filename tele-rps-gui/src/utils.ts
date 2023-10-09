import { useState } from './state';

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

export { getLogger };
