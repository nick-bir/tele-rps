const DEV_WEBAPP_TOKEN = 'test';
const DEV_USER_ID = 123456;

function getWebapp() {
    // @ts-expect-error cannot find Telegram
    if (typeof Telegram !== 'undefined') {
        // @ts-expect-error cannot find Telegram
        return Telegram.WebApp;
    }
    return {};
}

function getWebAppInitData() {
    return getWebapp().initData || DEV_WEBAPP_TOKEN;
}

function getUserId() {
    return getWebapp().initDataUnsafe?.user?.id || DEV_USER_ID;
}

function isDarkMode() {
    return getWebapp().colorTheme === 'dark';
}

export { getWebAppInitData, getUserId, isDarkMode };
