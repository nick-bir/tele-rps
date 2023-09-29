<template>
    <div class="_fake-game">
        Fake Game Controller
        <div>
            <button @click="startNewGame">Start new game</button>
        </div>
        <div>
            choose for opponent:
            <button @click="makeOpponentChoise('rock')">Rock</button>
            <button @click="makeOpponentChoise('paper')">Paper</button>
            <button @click="makeOpponentChoise('scissors')">Scissors</button>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../logic/api';

    const { startNewGame, makeOpponentChoise } = useApi();
</script>

<style scoped>
    ._fake-game {
        margin-top: 40px;
        border: 1px solid black;
        padding: 10px;
    }
</style>
