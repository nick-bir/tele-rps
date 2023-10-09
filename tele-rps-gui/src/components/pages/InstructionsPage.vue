<template>
    <div>
        <Subheader>How to play</Subheader>
        <ol class="instructions-list">
            <li>Invite a <b>@rpsbattle_bot</b> to a chat</li>
            <li>Write <b>/battle @person_you_want_to_play_with</b></li>
            <li>Open <b>menu -> Open game</b></li>
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
