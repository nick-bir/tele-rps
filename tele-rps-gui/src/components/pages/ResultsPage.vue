<template>
    <div>game results</div>
    {{ gameResult }}
</template>
<script setup lang="ts">

import {useState} from "../../state.ts";

const state = useState();
const gameResult = state.gameResult;
console.log(state)
</script>