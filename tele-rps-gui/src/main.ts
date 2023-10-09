import { createApp } from 'vue';
import App from './App.vue';
import { connect } from './controller';
import { useState } from './state/state';

createApp(App).mount('#app');
connect().catch((e) => {
    throw e;
});

window.addEventListener('unhandledrejection', (event) => {
    useState().showError(event.reason);
});
