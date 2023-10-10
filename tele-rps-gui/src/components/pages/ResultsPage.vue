<template>
    <div>
        <Subheader>game results</Subheader>
        {{ gameResult }}
    </div>
</template>
<script setup lang="ts">
    import Subheader from '../Subheader.vue';
    import { useState } from '../../state.ts';

    const state = useState();
    const gameResult = state.gameResult;
    console.log(state);
</script>
