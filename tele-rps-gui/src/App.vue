<template>
    <div :class="`app theme_${theme}`">
        <ErrorMessages />
        <InstructionsPage v-if="noGameCreated" />
        <GamePage v-if="isGameStarted" />
        <ResultsPage v-if="isGameFinished" />
        <FakeGame v-if="isDevMode()" />
    </div>
</template>

<script setup lang="ts">
    import ErrorMessages from './components/ErrorMessages.vue';
    import FakeGame from './components/_FakeGame.vue';
    import InstructionsPage from './components/pages/InstructionsPage.vue';
    import GamePage from './components/pages/GamePage.vue';
    import ResultsPage from './components/pages/ResultsPage.vue';
    import { useState } from './state';
    import { isDevMode } from './utils';
    import { isDarkMode } from './telegram';

    const { noGameCreated, isGameStarted, isGameFinished } = useState();
    const theme = isDarkMode() ? 'dark' : 'light';
</script>

<style>
    .app {
        width: fit-content;
        margin: 20px auto;
        padding: 10px;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }

    .theme_dark {
        background-color: #2c3e50;
        color: white;
    }
</style>
