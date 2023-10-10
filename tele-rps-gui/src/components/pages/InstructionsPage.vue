<template>
    <div>
        <Subheader>ABOUT THE GAME</Subheader>

        Challenge anyone in the world to conclude an argument, toss a coin or
        simply for fun. Remember, rock breaks scissors, paper wraps the rock and
        scissors cut paper. Good luck!

        <Subheader>HOW TO PLAY</Subheader>

        <ol class="instructions-list">
            <li>Send <b>/challenge</b> in chat</li>
            <li>
                Forward button <b>PLAY</b> to your opponent to challenge them>
            </li>
            <li>Open the app in the menu and battle!</li>
        </ol>
    </div>
</template>

<script setup lang="ts">
    import Subheader from '../Subheader.vue';
</script>

<style scoped>
    .instructions-list {
        margin: 20px auto;
    }
</style>
../../components/Subheader.vue
