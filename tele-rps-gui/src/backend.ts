import { getConfig } from './config/config';
import { getLogger } from './utils';
const log = getLogger('backend');

let webAppAuthToken: string;

async function authenticateApp() {
    const log = getLogger('backend.authenticateApp');
    if (webAppAuthToken) {
        log('already authenticated');
        return;
    }

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

        webAppAuthToken = token;
    } catch (e) {
        log.error(e);
        throw e;
    }
}

export { authenticateApp };
