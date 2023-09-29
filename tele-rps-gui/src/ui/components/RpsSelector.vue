<template>
    <div>
        <button @click="makeMyChoise('rock')">Rock</button>
        <button @click="makeMyChoise('paper')">Paper</button>
        <button @click="makeMyChoise('scissors')">Scissors</button>
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../logic/api';

    const { makeMyChoise } = useApi();
</script>
