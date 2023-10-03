<template>
    <div class="_fake-game">
        Fake Game Controller
        <div>
            <button @click="startNewGame">Start new game</button>
        </div>
        <div>
            choose for opponent:
            <button @click="setEnemyWeapon('rock')">Rock</button>
            <button @click="setEnemyWeapon('paper')">Paper</button>
            <button @click="setEnemyWeapon('scissors')">Scissors</button>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../logic/api';

    const { startNewGame, setEnemyWeapon } = useApi();
</script>

<style scoped>
    ._fake-game {
        margin-top: 40px;
        border: 1px solid black;
        padding: 10px;
    }
</style>
