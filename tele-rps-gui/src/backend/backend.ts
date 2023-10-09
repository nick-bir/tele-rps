import { getConfig } from '../config/config';
import { getLogger } from '../utils';
const log = getLogger('backend');

let pageAuthToken: string;

async function authenticateApp() {
    const log = getLogger('backend.authenticateApp');
    if (pageAuthToken) {
        log('already authenticated');
        return;
    }

    throw new Error('not implemented');

    log('getting token');

    getConfig().telegramAppToken;

    try {
        const result = await fetch(getConfig().authUrl, {
            method: 'POST',
            headers: {
                Authorization: getConfig().telegramAppToken,
            },
        });

        if (!result.ok) {
            throw new Error('wrong response');
        }

        log('token recieved');

        const token = await result.text();

        pageAuthToken = token;
    } catch (e) {
        log.error(e);
        throw e;
    }
}

export { authenticateApp };
