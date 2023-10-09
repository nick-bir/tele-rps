<template>
    <div class="_fake-game">
        Fake Game Controller
        <div>
            <button @click="startNewGame">Start new game</button>
        </div>
        <div>
            <br />
            choose for opponent:
            <br />
            <button @click="setEnemyWeapon('rock')">Rock</button>
            <button @click="setEnemyWeapon('paper')">Paper</button>
            <button @click="setEnemyWeapon('scissors')">Scissors</button>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { useState } from '../state';

    const { startNewGame, setEnemyWeapon } = useState();
</script>

<style scoped>
    ._fake-game {
        margin-top: 40px;
        border: 1px solid black;
        padding: 10px;
    }
</style>
../../state/api
