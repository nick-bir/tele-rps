<template>
    <div id="app">
        <InstructionsPage v-if="isNewGame" />
        <GamePage v-if="isGameStarted" />
        <ResultsPage v-if="isGameFinished" />

        <FakeGame />
    </div>
</template>

<script setup lang="ts">
    import FakeGame from './ui/components/_FakeGame.vue';
    import InstructionsPage from './ui/pages/InstructionsPage.vue';
    import GamePage from './ui/pages/GamePage.vue';
    import ResultsPage from './ui/pages/ResultsPage.vue';
    import { useApi } from './logic/api';

    const { isNewGame, isGameStarted, isGameFinished } = useApi();
</script>

<style>
    #app {
        margin: 20px auto;
        width: fit-content;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }
</style>
