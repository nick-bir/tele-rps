<template>
    <div>
        Game UI
        <Score />
        <RpsSelector v-if="!isMyChoiseMade" @choise="makeMyChoise" />
        <Hands v-if="isMyChoiseMade || isRoundFinished" />
    </div>
</template>

<script setup lang="ts">
    import Score from './Score.vue';
    import RpsSelector from './RpsSelector.vue';
    import Hands from './Hands/Hands.vue';
    import { useApi } from '../logic/api';

    const { isRoundFinished, makeMyChoise, isMyChoiseMade } = useApi();
</script>
