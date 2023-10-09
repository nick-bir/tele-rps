import devConfig from './config-dev';
import prodConfig from './config-prod';

function getConfig() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined' && Telegram.WebApp.initData) {
        return prodConfig
    }
    return devConfig;
}

export { getConfig };
