<template>
    <div>
        <MyHand :choise="myChoise" />
        <OpponentHand :choise="opponentChoise" />
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../../logic/api';
    import MyHand from './MyHand.vue';
    import OpponentHand from './OpponentHand.vue';

    const { myChoise, opponentChoise } = useApi();
</script>
../../../logic/api
