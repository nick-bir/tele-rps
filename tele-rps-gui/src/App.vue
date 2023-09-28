<template>
    <div id="app">
        <GameInstructions v-if="isNewGame" />
        <Game v-if="isGameStarted" />
        <GameResults v-if="isGameFinished" />
        <FakeGame />
    </div>
</template>

<script setup lang="ts">
    import FakeGame from './_FakeGame.vue';
    import GameInstructions from './ui/Instructions.vue';
    import Game from './ui/Game.vue';
    import GameResults from './ui/GameResults.vue';
    import { useApi } from './logic/api';

    const { isNewGame, isGameStarted, isGameFinished } = useApi();
</script>

<style>
    #app {
    }
</style>
