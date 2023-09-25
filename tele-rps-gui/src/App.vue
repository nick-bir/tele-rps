<template>
    <div id="app">
        <GameInstructions v-if="!isGameStarted" />
    </div>
</template>

<script setup lang="ts">
    import { computed } from 'vue';
    import { useState } from './logic/useCases/state';
    import GameInstructions from './ui/GameInstructions.vue';
    console.log('--- state', useState());

    const { state } = useState();

    let isGameStarted = computed(() => state.game.status !== 'new');
</script>

<style>
    #app {
    }
</style>
