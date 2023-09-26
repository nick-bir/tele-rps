<template>
    <div>
        Game api
        <Score />
        <RpsSelector v-if="isNewRound" />
        <PlayerHand v-if="isRoundFinished" />
        <OpponentHand v-if="isRoundFinished" />
    </div>
</template>

<script setup lang="ts">
    import Score from './Score.vue';
    import RpsSelector from './RpsSelector.vue';
    import PlayerHand from './PlayerHand.vue';
    import OpponentHand from './OpponentHand.vue';
    import { useApi } from '../logic/api';

    const { isNewRound, isRoundFinished } = useApi();
</script>
