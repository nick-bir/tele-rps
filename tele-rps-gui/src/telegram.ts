const DEV_WEBAPP_TOKEN = 'test';
const DEV_USER_ID = 123456

function getWebAppInitData() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined') {
        // @ts-expect-error cannot find Telegram
        return Telegram.WebApp.initData
    }
    return DEV_WEBAPP_TOKEN;
}

function getUserId() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined') {
        // @ts-expect-error cannot find Telegram
        return Telegram.WebApp.initDataUnsafe?.user?.id || DEV_USER_ID;
    }
    return DEV_USER_ID;
}

export { getWebAppInitData, getUserId };
