<template>
    <div>
        <Header>Game results</Header>
        <Hands />
        <div class="result">
            <div v-if="iWon" class="result__win">🎉 You win! 🎉</div>
            <div v-if="iLost" class="result__lose">You lose :(</div>
            <div v-if="isDraw" class="result__draw">It's a draw 🤝</div>
        </div>

        <br />
        <div><b>/challenge</b> someone again!</div>
    </div>
</template>
<script setup lang="ts">
    import Hands from '../Hands/Hands.vue';
    import Header from '../Header.vue';
    import { useState } from '../../state.ts';

    const { iWon, iLost, isDraw } = useState();
</script>

<style>
    .result {
        font-size: 1.5rem;
        font-weight: bold;
        margin: 1rem;
        text-align: center;
    }

    .result__win {
        color: green;
    }

    .result__draw {
        color: gray;
    }

    .result__lose {
        color: orange;
    }

    .theme_dark .result__draw {
        color: silver;
    }
</style>
