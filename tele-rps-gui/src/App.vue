<template>
    <div id="app">
        <GameInstructions v-if="!isGameStarted" />
        <Game v-else />
    </div>
</template>

<script setup lang="ts">
    import GameInstructions from './ui/Instructions.vue';
    import Game from './ui/Game.vue';
    import { useApi } from './logic/api';

    const { isGameStarted } = useApi();
</script>

<style>
    #app {
    }
</style>
