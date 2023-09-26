<template>
    <div>
        Game api
        <Score />
        <RpsSelector v-if="isNewRound" @choise="makeChoise" />
        <template v-if="isChoiseMade || isRoundFinished">
            <UserHand />
            <OpponentHand />
        </template>
    </div>
</template>

<script setup lang="ts">
    import Score from './Score.vue';
    import RpsSelector from './RpsSelector.vue';
    import UserHand from './UserHand.vue';
    import OpponentHand from './OpponentHand.vue';
    import { useApi } from '../logic/api';

    const { isNewRound, isRoundFinished, makeChoise, isChoiseMade } = useApi();
</script>
