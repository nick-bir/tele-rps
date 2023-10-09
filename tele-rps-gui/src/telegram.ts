const DEV_WEBAPP_TOKEN = 'test';
const DEV_USER_ID = 123456;

function getWebAppToken() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined') {
        // @ts-expect-error cannot find Telegram
        return Telegram.WebApp.initData.hash || DEV_WEBAPP_TOKEN;
    }

    return DEV_WEBAPP_TOKEN;
}

function getUserId() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined') {
        // @ts-expect-error cannot find Telegram
        return Telegram.WebAppUser?.id || DEV_USER_ID;
    }

    return DEV_USER_ID;
}

export { getWebAppToken, getUserId };
