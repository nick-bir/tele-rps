<template>
    <div class="hands">
        <MyHand class="hand" :choise="myChoise" />
        <OpponentHand class="hand" :choise="opponentChoise" />
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../../logic/api';
    import MyHand from './MyHand.vue';
    import OpponentHand from './OpponentHand.vue';

    const { myChoise, opponentChoise } = useApi();
</script>

<style>
    .hands {
        display: flex;
        justify-content: space-around;
        width: 100%;
    }

    .hand {
        align-self: stretch;
    }
</style>
